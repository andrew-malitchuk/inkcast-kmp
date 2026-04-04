package data.network.impl.source.epub

import java.io.ByteArrayOutputStream
import java.util.UUID
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Android implementation of [EpubBuilder] using `java.util.zip.ZipOutputStream`.
 *
 * Produces a valid EPUB 3 ZIP archive with the `mimetype` entry stored
 * uncompressed as required by the EPUB specification.
 */
internal actual object EpubBuilder {

    private const val MIMETYPE = "application/epub+zip"

    actual fun build(title: String, cleanHtml: String, images: List<EpubImage>): ByteArray {
        val safeTitle = title.escapeXml()
        val outputStream = ByteArrayOutputStream()
        ZipOutputStream(outputStream).use { zip ->
            // 1. mimetype (MUST be first and uncompressed per EPUB spec)
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

            // 2. META-INF/container.xml
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

            // 3. OEBPS/content.opf
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

            // 4. OEBPS/nav.xhtml (EPUB 3 navigation document)
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

            // 5. OEBPS/article.xhtml
            zip.putNextEntry(ZipEntry("OEBPS/article.xhtml"))
            zip.write(
                """<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>$safeTitle</title></head>
<body><h1>$safeTitle</h1>$cleanHtml</body>
</html>""".toByteArray(),
            )
            zip.closeEntry()

            // 6. Image files
            for (img in images) {
                zip.putNextEntry(ZipEntry("OEBPS/images/${img.fileName}"))
                zip.write(img.data)
                zip.closeEntry()
            }
        }
        return outputStream.toByteArray()
    }

    private fun String.escapeXml(): String = this
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}
