package data.network.impl.source.datasource

import data.network.api.core.NetworkType
import data.network.api.source.datasource.LinkProcessingNetworkSource
import data.network.api.source.model.PreparedEpubNetwork
import data.network.impl.core.NetworkProvider
import data.network.impl.source.epub.EpubBuilder
import data.network.impl.source.epub.EpubImage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlin.coroutines.cancellation.CancellationException

/**
 * Ktor-based implementation of [LinkProcessingNetworkSource].
 *
 * Downloads articles from the internet, extracts text content and referenced images,
 * and produces a [PreparedEpubNetwork] with valid EPUB 3 archive bytes.
 *
 * Uses [NetworkType.NONE] so traffic is routed over the best available internet
 * connection rather than through the ESP32 hotspot (which has no internet access).
 *
 * @see LinkProcessingNetworkSource
 * @see PreparedEpubNetwork
 * @see NetworkProvider
 */
internal class LinkProcessingNetworkSourceImpl : LinkProcessingNetworkSource {

    /**
     * Downloads an article from [url], extracts its content and images, and builds an EPUB archive.
     *
     * Progress updates are delivered via [onStatus] at each major stage (download, image fetch, EPUB build).
     *
     * @param url The article URL to download, or a `data:text/html,` URI containing inline HTML.
     * @param onStatus Callback invoked with human-readable status messages during processing.
     * @return A [PreparedEpubNetwork] containing the title and EPUB bytes, or `null` if an error occurs.
     */
    override suspend fun downloadAndBuild(
        url: String,
        onStatus: (String) -> Unit,
    ): PreparedEpubNetwork? {
        val client = NetworkProvider.createClient(network = NetworkType.NONE)
        return try {
            onStatus("Downloading article...")
            // NOTE: Support for data: URIs allows callers to pass pre-fetched HTML directly.
            val htmlContent = if (url.startsWith("data:text/html,")) {
                url.removePrefix("data:text/html,")
            } else {
                client.get(url).bodyAsText()
            }

            val title = extractTitle(htmlContent)
            val cleanHtml = extractArticleContent(htmlContent)

            onStatus("Downloading images...")
            val images = downloadImages(client, cleanHtml, url)
            val htmlWithLocalImages = rewriteImageSources(cleanHtml, images)

            onStatus("Building EPUB...")
            val epubBytes = EpubBuilder.build(title, htmlWithLocalImages, images)

            onStatus("EPUB ready! (${epubBytes.size / 1024} KB)")
            PreparedEpubNetwork(title = title, bytes = epubBytes)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            onStatus("Error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    /**
     * Extracts meaningful article content from raw HTML.
     *
     * Selects `<p>`, `<h1>`..`<h6>`, `<img>`, `<figure>`, `<blockquote>`,
     * `<ul>`, `<ol>`, `<li>` elements via regex. Falls back to the full
     * `<body>` content if no content elements are found.
     */
    private fun extractArticleContent(html: String): String {
        val tagPattern = Regex(
            """<(p|h[1-6]|img|figure|blockquote|ul|ol|li)(\s[^>]*)?>.*?</\1>|<img\s[^>]*?/?>""",
            setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL),
        )
        val matches = tagPattern.findAll(html).map { it.value }.toList()
        if (matches.isNotEmpty()) return matches.joinToString("\n")

        // NOTE: Body fallback — when no semantic content tags are found, use the raw <body> content.
        val bodyRegex = Regex("""<body[^>]*>(.*?)</body>""", setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
        return bodyRegex.find(html)?.groupValues?.get(1)?.trim() ?: html
    }

    /**
     * Downloads images referenced by `<img>` tags in the HTML.
     *
     * Only absolute HTTP(S) URLs are fetched; relative and non-HTTP sources are skipped.
     * Individual image download failures are silently ignored so that a single broken
     * image does not prevent the EPUB from being created.
     *
     * @param client The [HttpClient] used to fetch image bytes.
     * @param html The HTML string to scan for `<img src="...">` references.
     * @param baseUrl The original article URL (reserved for future relative-URL resolution).
     * @return A list of [EpubImage] instances containing downloaded image data.
     */
    private suspend fun downloadImages(
        client: HttpClient,
        html: String,
        baseUrl: String,
    ): List<EpubImage> {
        val images = mutableListOf<EpubImage>()
        val srcRegex = Regex("""<img[^>]+src=["']([^"']+)["']""")
        val seen = mutableSetOf<String>()
        var index = 0

        for (match in srcRegex.findAll(html)) {
            val src = match.groupValues[1]
            if (src in seen || !src.startsWith("http")) continue
            seen.add(src)

            try {
                val imgResponse = client.get(src)
                if (!imgResponse.status.isSuccess()) continue
                val bytes: ByteArray = imgResponse.body()
                if (bytes.isEmpty()) continue

                val mediaType = imgResponse.contentType()?.toString()
                    ?.takeIf { it.startsWith("image/") }
                    ?: guessMediaType(src)
                    ?: continue
                val ext = guessExtension(mediaType, src)
                images.add(EpubImage("img_$index.$ext", mediaType, bytes))
                index++
            } catch (_: Exception) {
                // Individual image failures are non-fatal
            }
        }

        return images
    }

    /**
     * Rewrites `<img src="...">` URLs in the HTML to point to local EPUB image paths.
     *
     * Also strips `srcset` attributes so that EPUB readers do not attempt to resolve
     * responsive image sets.
     *
     * @param html The HTML string containing remote image URLs.
     * @param images The downloaded [EpubImage] list whose filenames replace the remote URLs.
     * @return The HTML with rewritten image sources and `srcset` attributes removed.
     */
    private fun rewriteImageSources(html: String, images: List<EpubImage>): String {
        val srcRegex = Regex("""(<img[^>]+src=["'])([^"']+)(["'])""")
        val seen = mutableSetOf<String>()
        var index = 0

        var result = srcRegex.replace(html) { match ->
            val src = match.groupValues[2]
            if (src in seen || !src.startsWith("http") || index >= images.size) {
                match.value
            } else {
                seen.add(src)
                val localPath = "images/${images[index].fileName}"
                index++
                "${match.groupValues[1]}$localPath${match.groupValues[3]}"
            }
        }

        // NOTE: srcset removal — EPUB readers do not support responsive image sets,
        // so these attributes are stripped to avoid broken image references.
        result = result.replace(Regex("""\s+srcset=["'][^"']*["']"""), "")

        return result
    }

    /**
     * Extracts the document title from an HTML `<title>` tag.
     *
     * @param html The raw HTML string to search.
     * @return The trimmed title text, or `"Article"` if no `<title>` tag is found.
     */
    private fun extractTitle(html: String): String {
        val titleRegex = Regex("""<title>([^<]+)</title>""", RegexOption.IGNORE_CASE)
        return titleRegex.find(html)?.groupValues?.get(1)?.trim() ?: "Article"
    }

    /**
     * Guesses the MIME media type of an image from its URL file extension.
     *
     * @param url The image URL to inspect.
     * @return The guessed media type (e.g. `"image/png"`), or `"image/jpeg"` as the default fallback.
     */
    private fun guessMediaType(url: String): String? {
        val lower = url.lowercase()
        return when {
            lower.contains(".jpg") || lower.contains(".jpeg") -> "image/jpeg"
            lower.contains(".png") -> "image/png"
            lower.contains(".gif") -> "image/gif"
            lower.contains(".webp") -> "image/webp"
            lower.contains(".svg") -> "image/svg+xml"
            else -> "image/jpeg"
        }
    }

    /**
     * Guesses the file extension for an image based on its content type and URL.
     *
     * @param contentType The MIME content type from the HTTP response, or `null` if unavailable.
     * @param url The image URL, used as a fallback for extension detection.
     * @return The file extension without a leading dot (e.g. `"png"`), defaulting to `"jpg"`.
     */
    private fun guessExtension(contentType: String?, url: String): String {
        val type = contentType ?: ""
        val lower = url.lowercase()
        return when {
            type.contains("png") || lower.contains(".png") -> "png"
            type.contains("gif") || lower.contains(".gif") -> "gif"
            type.contains("webp") || lower.contains(".webp") -> "webp"
            type.contains("svg") || lower.contains(".svg") -> "svg"
            else -> "jpg"
        }
    }
}
