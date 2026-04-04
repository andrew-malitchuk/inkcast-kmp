package data.network.impl.source.epub

/**
 * Platform-specific EPUB 3 builder.
 *
 * Produces a valid EPUB ZIP archive from an article title, cleaned HTML body,
 * and optional downloaded images.
 *
 * - **Android / Desktop:** Uses `java.util.zip.ZipOutputStream`.
 * - **iOS:** Uses `Foundation` + `minizip` via `NSData` compression.
 */
internal expect object EpubBuilder {

    /**
     * Assembles an EPUB 3 archive from the provided content.
     *
     * @param title Article title (used in OPF metadata and `<h1>` heading).
     * @param cleanHtml Cleaned HTML body content (paragraphs, headings, images).
     * @param images Downloaded image files to embed in the EPUB.
     * @return Raw bytes of the EPUB ZIP archive.
     */
    fun build(title: String, cleanHtml: String, images: List<EpubImage> = emptyList()): ByteArray
}
