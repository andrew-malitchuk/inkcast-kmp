package data.network.impl.source.epub

import platform.Foundation.NSUUID

internal actual object EpubBuilder {

    private const val MIMETYPE = "application/epub+zip"

    actual fun build(title: String, cleanHtml: String, images: List<EpubImage>): ByteArray {
        val safeTitle = title.escapeXml()
        val uuid = NSUUID().UUIDString

        // Build EPUB as an uncompressed ZIP using manual byte assembly.
        // EPUB readers accept STORED (uncompressed) entries.
        val entries = mutableListOf<ZipFileEntry>()

        // 1. mimetype (MUST be first)
        entries.add(ZipFileEntry("mimetype", MIMETYPE.encodeToByteArray()))

        // 2. META-INF/container.xml
        entries.add(
            ZipFileEntry(
                "META-INF/container.xml",
                """<?xml version="1.0" encoding="UTF-8"?>
<container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
    <rootfiles>
        <rootfile full-path="OEBPS/content.opf" media-type="application/oebps-package+xml"/>
    </rootfiles>
</container>""".encodeToByteArray(),
            ),
        )

        // 3. content.opf
        val imageManifest = images.mapIndexed { i, img ->
            """        <item id="img$i" href="images/${img.fileName}" media-type="${img.mediaType}"/>"""
        }.joinToString("\n")

        entries.add(
            ZipFileEntry(
                "OEBPS/content.opf",
                """<?xml version="1.0" encoding="UTF-8"?>
<package xmlns="http://www.idpf.org/2007/opf" version="3.0" unique-identifier="uid">
    <metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
        <dc:identifier id="uid">urn:uuid:$uuid</dc:identifier>
        <dc:title>$safeTitle</dc:title>
        <dc:language>en</dc:language>
    </metadata>
    <manifest>
        <item id="nav" href="nav.xhtml" media-type="application/xhtml+xml" properties="nav"/>
        <item id="c1" href="article.xhtml" media-type="application/xhtml+xml"/>
$imageManifest
    </manifest>
    <spine><itemref idref="c1"/></spine>
</package>""".encodeToByteArray(),
            ),
        )

        // 4. nav.xhtml
        entries.add(
            ZipFileEntry(
                "OEBPS/nav.xhtml",
                """<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
<head><title>Navigation</title></head>
<body>
<nav epub:type="toc"><ol><li><a href="article.xhtml">$safeTitle</a></li></ol></nav>
</body>
</html>""".encodeToByteArray(),
            ),
        )

        // 5. article.xhtml
        entries.add(
            ZipFileEntry(
                "OEBPS/article.xhtml",
                """<?xml version="1.0" encoding="utf-8"?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>$safeTitle</title></head>
<body><h1>$safeTitle</h1>$cleanHtml</body>
</html>""".encodeToByteArray(),
            ),
        )

        // 6. Images
        for (img in images) {
            entries.add(ZipFileEntry("OEBPS/images/${img.fileName}", img.data))
        }

        return buildStoreOnlyZip(entries)
    }

    private fun String.escapeXml(): String = this
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}

private class ZipFileEntry(val name: String, val data: ByteArray)

/**
 * Builds a ZIP archive using only STORED (no compression) method.
 *
 * This is a minimal ZIP implementation sufficient for EPUB files.
 * EPUB readers are required to support uncompressed entries.
 */
private fun buildStoreOnlyZip(entries: List<ZipFileEntry>): ByteArray {
    val buffer = mutableListOf<Byte>()
    val centralDirectory = mutableListOf<Byte>()
    val offsets = mutableListOf<Int>()

    for (entry in entries) {
        offsets.add(buffer.size)
        val nameBytes = entry.name.encodeToByteArray()
        val crc = crc32(entry.data)

        // Local file header
        buffer.addAll(littleEndian32(0x04034b50)) // signature
        buffer.addAll(littleEndian16(20))          // version needed
        buffer.addAll(littleEndian16(0))           // flags
        buffer.addAll(littleEndian16(0))           // compression: STORED
        buffer.addAll(littleEndian16(0))           // mod time
        buffer.addAll(littleEndian16(0))           // mod date
        buffer.addAll(littleEndian32(crc))         // crc-32
        buffer.addAll(littleEndian32(entry.data.size)) // compressed size
        buffer.addAll(littleEndian32(entry.data.size)) // uncompressed size
        buffer.addAll(littleEndian16(nameBytes.size))  // name length
        buffer.addAll(littleEndian16(0))               // extra length
        buffer.addAll(nameBytes.toList())
        buffer.addAll(entry.data.toList())
    }

    val centralDirOffset = buffer.size

    for ((i, entry) in entries.withIndex()) {
        val nameBytes = entry.name.encodeToByteArray()
        val crc = crc32(entry.data)

        // Central directory file header
        centralDirectory.addAll(littleEndian32(0x02014b50)) // signature
        centralDirectory.addAll(littleEndian16(20))          // version made by
        centralDirectory.addAll(littleEndian16(20))          // version needed
        centralDirectory.addAll(littleEndian16(0))           // flags
        centralDirectory.addAll(littleEndian16(0))           // compression
        centralDirectory.addAll(littleEndian16(0))           // mod time
        centralDirectory.addAll(littleEndian16(0))           // mod date
        centralDirectory.addAll(littleEndian32(crc))
        centralDirectory.addAll(littleEndian32(entry.data.size))
        centralDirectory.addAll(littleEndian32(entry.data.size))
        centralDirectory.addAll(littleEndian16(nameBytes.size))
        centralDirectory.addAll(littleEndian16(0))           // extra length
        centralDirectory.addAll(littleEndian16(0))           // comment length
        centralDirectory.addAll(littleEndian16(0))           // disk number start
        centralDirectory.addAll(littleEndian16(0))           // internal attrs
        centralDirectory.addAll(littleEndian32(0))           // external attrs
        centralDirectory.addAll(littleEndian32(offsets[i]))   // local header offset
        centralDirectory.addAll(nameBytes.toList())
    }

    buffer.addAll(centralDirectory)

    // End of central directory
    buffer.addAll(littleEndian32(0x06054b50)) // signature
    buffer.addAll(littleEndian16(0))           // disk number
    buffer.addAll(littleEndian16(0))           // disk with central dir
    buffer.addAll(littleEndian16(entries.size)) // entries on this disk
    buffer.addAll(littleEndian16(entries.size)) // total entries
    buffer.addAll(littleEndian32(centralDirectory.size)) // central dir size
    buffer.addAll(littleEndian32(centralDirOffset))      // central dir offset
    buffer.addAll(littleEndian16(0))                     // comment length

    return buffer.toByteArray()
}

private fun littleEndian16(value: Int): List<Byte> = listOf(
    (value and 0xFF).toByte(),
    ((value shr 8) and 0xFF).toByte(),
)

private fun littleEndian32(value: Int): List<Byte> = listOf(
    (value and 0xFF).toByte(),
    ((value shr 8) and 0xFF).toByte(),
    ((value shr 16) and 0xFF).toByte(),
    ((value shr 24) and 0xFF).toByte(),
)

private fun crc32(data: ByteArray): Int {
    var crc = 0xFFFFFFFF.toInt()
    for (byte in data) {
        crc = crc xor (byte.toInt() and 0xFF)
        repeat(8) {
            crc = if (crc and 1 != 0) {
                (crc ushr 1) xor 0xEDB88320.toInt()
            } else {
                crc ushr 1
            }
        }
    }
    return crc xor 0xFFFFFFFF.toInt()
}
