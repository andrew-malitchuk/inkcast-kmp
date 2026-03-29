package data.network.impl.source.epub

import java.io.ByteArrayOutputStream
import java.util.UUID
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Desktop (JVM) implementation of [EpubBuilder] using `java.util.zip.ZipOutputStream`.
 *
 * Identical to the Android implementation — both share the JVM ZIP library.
 */
internal actual object EpubBuilder {

    private const val MIMETYPE = "application/epub+zip"

    /**
     * Builds a valid EPUB 3 archive from the given article content and images.
     *
     * The archive contains:
     * 1. `mimetype` — STORED (uncompressed) as required by the EPUB spec.
     * 2. `META-INF/container.xml` — points to the OPF package document.
     * 3. `OEBPS/content.opf` — package metadata, manifest, and spine.
     * 4. `OEBPS/nav.xhtml` — EPUB 3 navigation document.
     * 5. `OEBPS/article.xhtml` — the article body.
     * 6. `OEBPS/images/*` — downloaded image assets.
     *
     * @param title The article title (will be XML-escaped).
     * @param cleanHtml The sanitised article HTML body.
     * @param images The list of [EpubImage] assets to embed.
     * @return The complete EPUB file as a byte array.
     */
    actual fun build(title: String, cleanHtml: String, images: List<EpubImage>): ByteArray {
        val safeTitle = title.escapeXml()
        val outputStream = ByteArrayOutputStream()
        ZipOutputStream(outputStream).use { zip ->
            // NOTE: The mimetype entry MUST be the first entry and MUST use STORED (no compression)
            // per the EPUB Open Container Format specification.
            val mimeBytes = MIMETYPE.toByteArray(Charsets.UTF_8)
            val crc32 = CRC32().apply { update(mimeBytes) }.value
            val mimeEntry = ZipEntry("mimetype").apply {
                method = ZipEntry.STORED
                size = mimeBytes.size.toLong()
                crc = crc32
            }
            zip.putNextEntry(mimeEntry)
            zip.write(mimeBytes)
            zip.closeEntry()

            zip.putNextEntry(ZipEntry("META-INF/container.xml"))
            zip.write(
                """<?xml version="1.0" encoding="UTF-8"?>
<container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
    <rootfiles>
        <rootfile full-path="OEBPS/content.opf" media-type="application/oebps-package+xml"/>
    </rootfiles>
</container>""".toByteArray(),
            )
            zip.closeEntry()

            val imageManifest = images.mapIndexed { i, img ->
                """        <item id="img$i" href="images/${img.fileName}" media-type="${img.mediaType}"/>"""
            }.joinToString("\n")

            zip.putNextEntry(ZipEntry("OEBPS/content.opf"))
            zip.write(
                """<?xml version="1.0" encoding="UTF-8"?>
<package xmlns="http://www.idpf.org/2007/opf" version="3.0" unique-identifier="uid">
    <metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
        <dc:identifier id="uid">urn:uuid:${UUID.randomUUID()}</dc:identifier>
        <dc:title>$safeTitle</dc:title>
        <dc:language>en</dc:language>
    </metadata>
    <manifest>
        <item id="nav" href="nav.xhtml" media-type="application/xhtml+xml" properties="nav"/>
        <item id="c1" href="article.xhtml" media-type="application/xhtml+xml"/>
$imageManifest
    </manifest>
    <spine><itemref idref="c1"/></spine>
</package>""".toByteArray(),
            )
            zip.closeEntry()

            zip.putNextEntry(ZipEntry("OEBPS/nav.xhtml"))
            zip.write(
                """<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
<head><title>Navigation</title></head>
<body>
<nav epub:type="toc"><ol><li><a href="article.xhtml">$safeTitle</a></li></ol></nav>
</body>
</html>""".toByteArray(),
            )
            zip.closeEntry()

            zip.putNextEntry(ZipEntry("OEBPS/article.xhtml"))
            zip.write(
                """<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>$safeTitle</title></head>
<body><h1>$safeTitle</h1>$cleanHtml</body>
</html>""".toByteArray(),
            )
            zip.closeEntry()

            for (img in images) {
                zip.putNextEntry(ZipEntry("OEBPS/images/${img.fileName}"))
                zip.write(img.data)
                zip.closeEntry()
            }
        }
        return outputStream.toByteArray()
    }

    /**
     * Escapes XML special characters in this [String] to produce valid XML content.
     *
     * @return The escaped string safe for embedding in XML/XHTML documents.
     */
    private fun String.escapeXml(): String = this
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}
