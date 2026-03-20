package presentation.core.platform.source.image

/**
 * Encodes a grayscale pixel array into a 24-bit BMP file.
 *
 * The output is a standard Windows BMP (BITMAPINFOHEADER, bottom-up BGR)
 * suitable for display on e-ink devices.
 */
public object BmpEncoder {

    /**
     * Encodes a grayscale array into BMP bytes.
     *
     * @param gray Grayscale pixel values (0–255), length = [width] × [height].
     * @param width Image width in pixels.
     * @param height Image height in pixels.
     * @return Raw BMP file bytes.
     */
    public fun encode(gray: IntArray, width: Int, height: Int): ByteArray {
        val rowBytes = width * 3
        val padding = (4 - rowBytes % 4) % 4
        val paddedRow = rowBytes + padding
        val pixelDataSize = paddedRow * height
        val fileSize = 14 + 40 + pixelDataSize

        val bmp = ByteArray(fileSize)
        var offset = 0

        bmp[offset++] = 'B'.code.toByte()
        bmp[offset++] = 'M'.code.toByte()
        bmp.writeLeInt(offset, fileSize); offset += 4
        bmp.writeLeInt(offset, 0); offset += 4
        bmp.writeLeInt(offset, 54); offset += 4

        bmp.writeLeInt(offset, 40); offset += 4
        bmp.writeLeInt(offset, width); offset += 4
        bmp.writeLeInt(offset, height); offset += 4
        bmp.writeLeShort(offset, 1); offset += 2
        bmp.writeLeShort(offset, 24); offset += 2
        bmp.writeLeInt(offset, 0); offset += 4
        bmp.writeLeInt(offset, pixelDataSize); offset += 4
        bmp.writeLeInt(offset, 2835); offset += 4
        bmp.writeLeInt(offset, 2835); offset += 4
        bmp.writeLeInt(offset, 0); offset += 4
        bmp.writeLeInt(offset, 0); offset += 4

        for (y in height - 1 downTo 0) {
            for (x in 0 until width) {
                val g = gray[y * width + x].toByte()
                bmp[offset++] = g
                bmp[offset++] = g
                bmp[offset++] = g
            }
            offset += padding
        }

        return bmp
    }

    private fun ByteArray.writeLeInt(offset: Int, value: Int) {
        this[offset] = (value and 0xFF).toByte()
        this[offset + 1] = ((value shr 8) and 0xFF).toByte()
        this[offset + 2] = ((value shr 16) and 0xFF).toByte()
        this[offset + 3] = ((value shr 24) and 0xFF).toByte()
    }

    private fun ByteArray.writeLeShort(offset: Int, value: Int) {
        this[offset] = (value and 0xFF).toByte()
        this[offset + 1] = ((value shr 8) and 0xFF).toByte()
    }
}
