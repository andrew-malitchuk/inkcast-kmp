package presentation.core.platform.source.image

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.ImageInfo
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage

/**
 * iOS implementation of [ImageDecoder] using UIKit and CoreGraphics.
 *
 * Normalizes EXIF orientation by re-drawing through a UIGraphics context,
 * then extracts raw BGRA pixels via CoreGraphics bitmap context for
 * conversion to ARGB format.
 */
@OptIn(ExperimentalForeignApi::class)
public actual object ImageDecoder {

    /**
     * Decodes raw image bytes into a [DecodedImage] with ARGB pixel data.
     *
     * Re-draws the image through UIGraphics to normalize EXIF orientation,
     * then reads pixels via a CoreGraphics bitmap context in BGRA byte order
     * and converts to ARGB.
     *
     * @param bytes Raw image file bytes.
     * @return Decoded image with pixel array, or `null` if decoding fails.
     */
    public actual fun decode(bytes: ByteArray): DecodedImage? {
        val nsData = bytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
        }
        val uiImage = UIImage(data = nsData) ?: return null
        val width = uiImage.size.useContents { width.toInt() }
        val height = uiImage.size.useContents { height.toInt() }
        if (width == 0 || height == 0) return null

        UIGraphicsBeginImageContextWithOptions(
            CGSizeMake(width.toDouble(), height.toDouble()), true, 1.0,
        )
        uiImage.drawInRect(CGRectMake(0.0, 0.0, width.toDouble(), height.toDouble()))
        val normalizedImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()

        val cgImage = normalizedImage?.CGImage ?: return null
        val colorSpace = CGColorSpaceCreateDeviceRGB()
        val bytesPerRow = width * 4
        val rawData = ByteArray(bytesPerRow * height)
        rawData.usePinned { pinned ->
            val context = CGBitmapContextCreate(
                data = pinned.addressOf(0),
                width = width.toULong(),
                height = height.toULong(),
                bitsPerComponent = 8u,
                bytesPerRow = bytesPerRow.toULong(),
                space = colorSpace,
                bitmapInfo = CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst.value or (2u shl 12),
            )
            if (context != null) {
                platform.CoreGraphics.CGContextDrawImage(
                    context,
                    CGRectMake(0.0, 0.0, width.toDouble(), height.toDouble()),
                    cgImage,
                )
            }
        }

        val pixels = IntArray(width * height)
        for (i in pixels.indices) {
            val offset = i * 4
            val b = rawData[offset].toInt() and 0xFF
            val g = rawData[offset + 1].toInt() and 0xFF
            val r = rawData[offset + 2].toInt() and 0xFF
            val a = rawData[offset + 3].toInt() and 0xFF
            pixels[i] = (a shl 24) or (r shl 16) or (g shl 8) or b
        }
        return DecodedImage(pixels, width, height)
    }

    /**
     * Converts ARGB pixel array to a Compose [ImageBitmap] via Skia BGRA bitmap.
     *
     * Reorders ARGB channels to BGRA byte layout expected by Skia's [ColorType.BGRA_8888].
     *
     * @param pixels ARGB pixel data.
     * @param width Image width in pixels.
     * @param height Image height in pixels.
     * @return Compose-compatible image bitmap.
     */
    public actual fun toImageBitmap(pixels: IntArray, width: Int, height: Int): ImageBitmap {
        val bitmap = Bitmap()
        bitmap.allocPixels(ImageInfo(width, height, ColorType.BGRA_8888, ColorAlphaType.PREMUL))
        val bytes = ByteArray(width * height * 4)
        for (i in pixels.indices) {
            val px = pixels[i]
            val offset = i * 4
            bytes[offset] = (px and 0xFF).toByte()
            bytes[offset + 1] = ((px shr 8) and 0xFF).toByte()
            bytes[offset + 2] = ((px shr 16) and 0xFF).toByte()
            bytes[offset + 3] = ((px shr 24) and 0xFF).toByte()
        }
        bitmap.installPixels(bytes)
        return org.jetbrains.skia.Image.makeFromBitmap(bitmap).toComposeImageBitmap()
    }
}
