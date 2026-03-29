package data.network.impl.source.epub

import platform.Foundation.NSUUID

/**
 * iOS implementation of [EpubBuilder] using a manual STORED-only ZIP assembler.
 *
 * Since `java.util.zip` is unavailable on iOS/Native, this builds the ZIP
 * archive byte-by-byte using only the STORED (no compression) method.
 * EPUB readers are required to accept uncompressed entries.
 */
internal actual object EpubBuilder {

    private const val MIMETYPE = "application/epub+zip"

    /**
     * Builds a valid EPUB 3 archive from the given article content and images.
     *
     * Uses a custom STORED-only ZIP assembler since `java.util.zip` is not available
     * on iOS/Native. The resulting archive is uncompressed, which is valid per the
     * EPUB specification.
     *
     * @param title The article title (will be XML-escaped).
     * @param cleanHtml The sanitised article HTML body.
     * @param images The list of [EpubImage] assets to embed.
     * @return The complete EPUB file as a byte array.
     * @see buildStoreOnlyZip
     */
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

/**
 * Represents a single file entry to be written into the ZIP archive.
 *
 * @param name The relative path of the entry within the ZIP (e.g. `"OEBPS/article.xhtml"`).
 * @param data The raw byte content of the entry.
 */
private class ZipFileEntry(val name: String, val data: ByteArray)

/**
 * Growable byte buffer that avoids per-byte boxing overhead of `MutableList<Byte>`.
 */
private class ByteBuffer(initialCapacity: Int = 4096) {
    private var array = ByteArray(initialCapacity)
    var size: Int = 0
        private set

    private fun ensureCapacity(needed: Int) {
        val required = size + needed
        if (required > array.size) {
            val newSize = maxOf(array.size * 2, required)
            array = array.copyOf(newSize)
        }
    }

    /**
     * Appends a single byte to the buffer.
     *
     * @param b The byte to write.
     */
    fun writeByte(b: Byte) {
        ensureCapacity(1)
        array[size++] = b
    }

    /**
     * Appends all bytes from the given array to the buffer.
     *
     * @param bytes The byte array to write.
     */
    fun writeBytes(bytes: ByteArray) {
        ensureCapacity(bytes.size)
        bytes.copyInto(array, size)
        size += bytes.size
    }

    /**
     * Writes a 16-bit integer in little-endian byte order.
     *
     * @param value The integer value; only the lower 16 bits are written.
     */
    fun writeLE16(value: Int) {
        ensureCapacity(2)
        array[size++] = (value and 0xFF).toByte()
        array[size++] = ((value shr 8) and 0xFF).toByte()
    }

    /**
     * Writes a 32-bit integer in little-endian byte order.
     *
     * @param value The integer value to write.
     */
    fun writeLE32(value: Int) {
        ensureCapacity(4)
        array[size++] = (value and 0xFF).toByte()
        array[size++] = ((value shr 8) and 0xFF).toByte()
        array[size++] = ((value shr 16) and 0xFF).toByte()
        array[size++] = ((value shr 24) and 0xFF).toByte()
    }

    /**
     * Returns a trimmed copy of the buffer contents.
     *
     * @return A new [ByteArray] containing exactly [size] bytes.
     */
    fun toByteArray(): ByteArray = array.copyOf(size)
}

/**
 * Builds a ZIP archive using only STORED (no compression) method.
 *
 * This is a minimal ZIP implementation sufficient for EPUB files.
 * EPUB readers are required to support uncompressed entries.
 */
private fun buildStoreOnlyZip(entries: List<ZipFileEntry>): ByteArray {
    val totalDataSize = entries.sumOf { it.data.size + it.name.encodeToByteArray().size + 76 }
    val buffer = ByteBuffer(totalDataSize)
    val centralDir = ByteBuffer(entries.size * 92)
    val offsets = IntArray(entries.size)
    val crcs = IntArray(entries.size) { crc32(entries[it].data) }

    for ((i, entry) in entries.withIndex()) {
        offsets[i] = buffer.size
        val nameBytes = entry.name.encodeToByteArray()

        // Local file header
        buffer.writeLE32(0x04034b50) // signature
        buffer.writeLE16(20)         // version needed
        buffer.writeLE16(0)          // flags
        buffer.writeLE16(0)          // compression: STORED
        buffer.writeLE16(0)          // mod time
        buffer.writeLE16(0)          // mod date
        buffer.writeLE32(crcs[i])    // crc-32
        buffer.writeLE32(entry.data.size) // compressed size
        buffer.writeLE32(entry.data.size) // uncompressed size
        buffer.writeLE16(nameBytes.size)  // name length
        buffer.writeLE16(0)               // extra length
        buffer.writeBytes(nameBytes)
        buffer.writeBytes(entry.data)
    }

    val centralDirOffset = buffer.size

    for ((i, entry) in entries.withIndex()) {
        val nameBytes = entry.name.encodeToByteArray()

        // Central directory file header
        centralDir.writeLE32(0x02014b50) // signature
        centralDir.writeLE16(20)          // version made by
        centralDir.writeLE16(20)          // version needed
        centralDir.writeLE16(0)           // flags
        centralDir.writeLE16(0)           // compression
        centralDir.writeLE16(0)           // mod time
        centralDir.writeLE16(0)           // mod date
        centralDir.writeLE32(crcs[i])
        centralDir.writeLE32(entry.data.size)
        centralDir.writeLE32(entry.data.size)
        centralDir.writeLE16(nameBytes.size)
        centralDir.writeLE16(0)           // extra length
        centralDir.writeLE16(0)           // comment length
        centralDir.writeLE16(0)           // disk number start
        centralDir.writeLE16(0)           // internal attrs
        centralDir.writeLE32(0)           // external attrs
        centralDir.writeLE32(offsets[i])  // local header offset
        centralDir.writeBytes(nameBytes)
    }

    buffer.writeBytes(centralDir.toByteArray())

    // End of central directory
    buffer.writeLE32(0x06054b50) // signature
    buffer.writeLE16(0)           // disk number
    buffer.writeLE16(0)           // disk with central dir
    buffer.writeLE16(entries.size) // entries on this disk
    buffer.writeLE16(entries.size) // total entries
    buffer.writeLE32(centralDir.size) // central dir size
    buffer.writeLE32(centralDirOffset) // central dir offset
    buffer.writeLE16(0)                // comment length

    return buffer.toByteArray()
}

/**
 * Computes the CRC-32 checksum of the given byte array using the standard polynomial (0xEDB88320).
 *
 * This is a pure-Kotlin implementation used on iOS/Native where `java.util.zip.CRC32` is unavailable.
 *
 * @param data The input bytes to checksum.
 * @return The CRC-32 value as a signed [Int].
 */
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
