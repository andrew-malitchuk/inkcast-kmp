package presentation.core.platform.source.image

/**
 * Pure-Kotlin image processing utilities for e-ink display preparation.
 *
 * Provides center-crop, bilinear resize, grayscale conversion, and
 * Floyd–Steinberg dithering — all without platform dependencies.
 */
public object ImageProcessor {

    /**
     * Crops the image to [targetAspect] using bias offsets.
     *
     * @param src Source image.
     * @param targetAspect Width/height ratio (default 480/800 for e-ink).
     * @param verticalBias Vertical crop position: 0 = top, 1 = bottom.
     * @param horizontalBias Horizontal crop position: 0 = left, 1 = right.
     * @return Cropped image.
     */
    public fun centerCrop(
        src: DecodedImage,
        targetAspect: Float = 480f / 800f,
        verticalBias: Float = 0.5f,
        horizontalBias: Float = 0.5f,
    ): DecodedImage {
        val srcAspect = src.width.toFloat() / src.height.toFloat()
        val cropW: Int
        val cropH: Int
        if (srcAspect > targetAspect) {
            cropH = src.height
            cropW = (cropH * targetAspect).toInt()
        } else {
            cropW = src.width
            cropH = (cropW / targetAspect).toInt()
        }
        val offsetX = ((src.width - cropW) * horizontalBias).toInt().coerceIn(0, src.width - cropW)
        val offsetY = ((src.height - cropH) * verticalBias).toInt().coerceIn(0, src.height - cropH)
        val out = IntArray(cropW * cropH)
        for (y in 0 until cropH) {
            val srcRow = (y + offsetY) * src.width + offsetX
            val dstRow = y * cropW
            src.pixels.copyInto(out, dstRow, srcRow, srcRow + cropW)
        }
        return DecodedImage(out, cropW, cropH)
    }

    /**
     * Crops the image using pan offset and zoom level from a gesture-based crop UI.
     *
     * The viewport is a fixed-aspect rectangle (480:800 by default). The user pans
     * and zooms the source image underneath the viewport. [offsetX]/[offsetY] are
     * normalised (0–1) offsets within the pannable range, and [zoom] scales the
     * source image (1 = fit, higher = zoomed in).
     *
     * @param src Source image.
     * @param offsetX Normalised horizontal pan (0 = left edge, 1 = right edge).
     * @param offsetY Normalised vertical pan (0 = top edge, 1 = bottom edge).
     * @param zoom Zoom level (≥ 1).
     * @param targetW Target width in pixels.
     * @param targetH Target height in pixels.
     * @return Cropped image at [targetW]×[targetH].
     */
    public fun cropWithTransform(
        src: DecodedImage,
        offsetX: Float = 0.5f,
        offsetY: Float = 0.5f,
        zoom: Float = 1f,
        targetW: Int = 480,
        targetH: Int = 800,
    ): DecodedImage {
        val targetAspect = targetW.toFloat() / targetH.toFloat()
        val srcAspect = src.width.toFloat() / src.height.toFloat()

        // Base crop (fit) at zoom=1
        val baseCropW: Int
        val baseCropH: Int
        if (srcAspect > targetAspect) {
            baseCropH = src.height
            baseCropW = (baseCropH * targetAspect).toInt()
        } else {
            baseCropW = src.width
            baseCropH = (baseCropW / targetAspect).toInt()
        }

        // Apply zoom: smaller crop = more zoomed in
        val clampedZoom = zoom.coerceAtLeast(1f)
        val cropW = (baseCropW / clampedZoom).toInt().coerceIn(1, src.width)
        val cropH = (baseCropH / clampedZoom).toInt().coerceIn(1, src.height)

        val maxOffX = (src.width - cropW).coerceAtLeast(0)
        val maxOffY = (src.height - cropH).coerceAtLeast(0)
        val ox = (maxOffX * offsetX.coerceIn(0f, 1f)).toInt()
        val oy = (maxOffY * offsetY.coerceIn(0f, 1f)).toInt()

        val out = IntArray(cropW * cropH)
        for (y in 0 until cropH) {
            val srcRow = (y + oy) * src.width + ox
            val dstRow = y * cropW
            src.pixels.copyInto(out, dstRow, srcRow, srcRow + cropW)
        }
        return DecodedImage(out, cropW, cropH)
    }

    /**
     * Resizes an image using bilinear interpolation.
     *
     * @param src Source image.
     * @param targetW Target width (default 480).
     * @param targetH Target height (default 800).
     * @return Resized image.
     */
    public fun resize(src: DecodedImage, targetW: Int = 480, targetH: Int = 800): DecodedImage {
        val out = IntArray(targetW * targetH)
        val xRatio = src.width.toFloat() / targetW
        val yRatio = src.height.toFloat() / targetH
        for (y in 0 until targetH) {
            val srcY = y * yRatio
            val y0 = srcY.toInt().coerceAtMost(src.height - 2)
            val y1 = y0 + 1
            val yFrac = srcY - y0
            for (x in 0 until targetW) {
                val srcX = x * xRatio
                val x0 = srcX.toInt().coerceAtMost(src.width - 2)
                val x1 = x0 + 1
                val xFrac = srcX - x0
                val c00 = src.pixels[y0 * src.width + x0]
                val c10 = src.pixels[y0 * src.width + x1]
                val c01 = src.pixels[y1 * src.width + x0]
                val c11 = src.pixels[y1 * src.width + x1]
                out[y * targetW + x] = bilinearInterpolate(c00, c10, c01, c11, xFrac, yFrac)
            }
        }
        return DecodedImage(out, targetW, targetH)
    }

    private fun bilinearInterpolate(c00: Int, c10: Int, c01: Int, c11: Int, xFrac: Float, yFrac: Float): Int {
        fun channel(shift: Int): Int {
            val v00 = (c00 shr shift) and 0xFF
            val v10 = (c10 shr shift) and 0xFF
            val v01 = (c01 shr shift) and 0xFF
            val v11 = (c11 shr shift) and 0xFF
            val top = v00 + (v10 - v00) * xFrac
            val bot = v01 + (v11 - v01) * xFrac
            return (top + (bot - top) * yFrac).toInt().coerceIn(0, 255)
        }
        return (0xFF shl 24) or (channel(16) shl 16) or (channel(8) shl 8) or channel(0)
    }

    /**
     * Converts ARGB pixel data to grayscale (luminance).
     *
     * @param src Source image.
     * @return Grayscale values (0–255), one per pixel.
     */
    public fun toGrayscale(src: DecodedImage): IntArray {
        val gray = IntArray(src.pixels.size)
        for (i in src.pixels.indices) {
            val px = src.pixels[i]
            val r = (px shr 16) and 0xFF
            val g = (px shr 8) and 0xFF
            val b = px and 0xFF
            gray[i] = (77 * r + 150 * g + 29 * b) shr 8
        }
        return gray
    }

    /**
     * Applies Floyd–Steinberg dithering to a grayscale array (in-place).
     *
     * Uses 4-level quantization (0, 85, 170, 255) suitable for e-ink displays.
     * Memory-efficient: uses two row buffers instead of a full-image buffer.
     *
     * @param gray Grayscale values, modified in-place.
     * @param width Image width.
     * @param height Image height.
     */
    public fun floydSteinbergDither(gray: IntArray, width: Int, height: Int) {
        var curRow = FloatArray(width)
        var nextRow = FloatArray(width)
        for (x in 0 until width) curRow[x] = gray[x].toFloat()
        for (y in 0 until height) {
            if (y + 1 < height) {
                val rowStart = (y + 1) * width
                for (x in 0 until width) nextRow[x] = gray[rowStart + x].toFloat()
            }
            for (x in 0 until width) {
                val old = curRow[x]
                val new = nearestLevel(old)
                gray[y * width + x] = new
                val error = old - new
                if (x + 1 < width) curRow[x + 1] += error * 7f / 16f
                if (y + 1 < height) {
                    if (x - 1 >= 0) nextRow[x - 1] += error * 3f / 16f
                    nextRow[x] += error * 5f / 16f
                    if (x + 1 < width) nextRow[x + 1] += error * 1f / 16f
                }
            }
            val tmp = curRow
            curRow = nextRow
            nextRow = tmp
            nextRow.fill(0f)
        }
    }

    private fun nearestLevel(value: Float): Int = when {
        value <= 42f -> 0
        value <= 127f -> 85
        value <= 212f -> 170
        else -> 255
    }
}
