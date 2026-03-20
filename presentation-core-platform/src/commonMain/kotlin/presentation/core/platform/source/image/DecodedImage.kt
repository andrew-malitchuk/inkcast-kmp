package presentation.core.platform.source.image

/**
 * Platform-agnostic representation of a decoded image as a flat ARGB pixel array.
 *
 * @property pixels ARGB pixel data, row-major, length = [width] × [height].
 * @property width Image width in pixels.
 * @property height Image height in pixels.
 */
public data class DecodedImage(val pixels: IntArray, val width: Int, val height: Int)
