package presentation.core.platform.source.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayInputStream

/**
 * Android implementation of [ImageDecoder] using [BitmapFactory] and EXIF rotation correction.
 */
public actual object ImageDecoder {

    /** Maximum width or height before sub-sampling kicks in. */
    private const val MAX_DIMENSION = 1600

    /**
     * Decodes raw image bytes into a [DecodedImage] with ARGB pixel data.
     *
     * Uses a two-pass decode: first pass reads dimensions only, second pass
     * applies sub-sampling to stay within [MAX_DIMENSION]. EXIF orientation
     * is applied after decoding.
     *
     * @param bytes Raw image file bytes (JPEG, PNG, etc.).
     * @return Decoded image with pixel array, or `null` if decoding fails.
     */
    public actual fun decode(bytes: ByteArray): DecodedImage? {
        val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, opts)
        val imgW = opts.outWidth
        val imgH = opts.outHeight
        if (imgW <= 0 || imgH <= 0) return null

        var sampleSize = 1
        while (imgW / sampleSize > MAX_DIMENSION || imgH / sampleSize > MAX_DIMENSION) {
            sampleSize *= 2
        }

        val decodeOpts = BitmapFactory.Options().apply {
            inSampleSize = sampleSize
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }
        val raw = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, decodeOpts) ?: return null
        val bitmap = applyExifRotation(raw, bytes)
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val result = DecodedImage(pixels, bitmap.width, bitmap.height)
        if (bitmap !== raw) raw.recycle()
        bitmap.recycle()
        return result
    }

    /**
     * Converts ARGB pixel array to a Compose [ImageBitmap] via Android [Bitmap].
     *
     * @param pixels ARGB pixel data.
     * @param width Image width in pixels.
     * @param height Image height in pixels.
     * @return Compose-compatible image bitmap.
     */
    public actual fun toImageBitmap(pixels: IntArray, width: Int, height: Int): ImageBitmap {
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888).asImageBitmap()
    }

    /**
     * Applies EXIF orientation correction to the decoded bitmap.
     *
     * @param bitmap The raw decoded bitmap (may have incorrect orientation).
     * @param bytes Original file bytes used to read EXIF metadata.
     * @return Correctly rotated/flipped bitmap, or the original if no correction needed.
     */
    private fun applyExifRotation(bitmap: Bitmap, bytes: ByteArray): Bitmap {
        val exif = try {
            ExifInterface(ByteArrayInputStream(bytes))
        } catch (_: Exception) {
            return bitmap
        }
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL,
        )
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.preScale(1f, -1f)
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.postRotate(90f)
                matrix.preScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.postRotate(270f)
                matrix.preScale(-1f, 1f)
            }
            else -> return bitmap
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
