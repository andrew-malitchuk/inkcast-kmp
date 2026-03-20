package presentation.core.platform.source.image

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Platform-specific image decoding and bitmap conversion.
 *
 * Each platform provides its own implementation for decoding raw bytes
 * into a [DecodedImage] (Android uses BitmapFactory, iOS uses UIKit/CoreGraphics).
 */
public expect object ImageDecoder {
    /**
     * Decodes raw image bytes (JPEG, PNG, BMP, etc.) into a [DecodedImage].
     *
     * Applies EXIF rotation automatically on platforms that support it.
     * Images are sub-sampled to fit within a reasonable dimension to avoid OOM.
     *
     * @param bytes Raw image file bytes.
     * @return Decoded pixel data, or `null` if decoding fails.
     */
    public fun decode(bytes: ByteArray): DecodedImage?

    /**
     * Converts an ARGB pixel array into a Compose [ImageBitmap] for display.
     *
     * @param pixels ARGB pixel data.
     * @param width Image width.
     * @param height Image height.
     * @return Compose-compatible bitmap.
     */
    public fun toImageBitmap(pixels: IntArray, width: Int, height: Int): ImageBitmap
}
