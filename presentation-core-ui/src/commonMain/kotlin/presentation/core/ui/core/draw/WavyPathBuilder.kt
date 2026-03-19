package presentation.core.ui.core.draw

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.sin

/**
 * Builds a sinusoidal wave [Path] that can be stroked to render wavy tracks in progress indicators
 * and sliders.
 *
 * The resulting path starts at [startX] and ends at [endX], oscillating around [centerY].
 * When [incremental] is `true` the wave amplitude ramps up linearly from zero at [startX]
 * to full height at [endX], producing an "emerging wave" effect suitable for spread animations.
 *
 * This function reuses a single [Path] instance ([reusablePath]) to avoid per-frame allocations.
 *
 * @param startX Horizontal pixel coordinate where the wave begins.
 * @param endX Horizontal pixel coordinate where the wave ends.
 * @param centerY Vertical pixel coordinate of the wave's equilibrium line.
 * @param waveLengthPx Length of one full wave cycle in pixels.
 * @param waveHeightPx Peak amplitude of the wave in pixels (half the crest-to-trough distance).
 * @param waveSpread Multiplier (0..1) controlling the overall wave amplitude during spread
 *   animation. At `0` the wave is flat; at `1` it reaches full [waveHeightPx].
 * @param waveShiftPx Horizontal pixel offset applied to the wave phase, used to animate
 *   continuous horizontal movement.
 * @param incremental When `true`, the amplitude grows linearly from the start of the path to the
 *   end, creating a progressive wave appearance.
 * @return A [Path] describing the wave, ready to be drawn with [DrawScope.drawPath].
 *
 * @see WavyPathDefaults
 */
public fun DrawScope.buildWavyPath(
    startX: Float,
    endX: Float,
    centerY: Float,
    waveLengthPx: Float,
    waveHeightPx: Float,
    waveSpread: Float,
    waveShiftPx: Float,
    incremental: Boolean,
): Path = reusablePath.apply {
    rewind()
    val startRadians = waveSpread * waveShiftPx / waveLengthPx * (2 * PI)
    val startHeightFactor = if (incremental) 0f else 1f
    val startY = (sin(startRadians) * startHeightFactor * waveHeightPx + size.height) / 2
    moveTo(startX, startY.toFloat())
    val range = startX.toInt()..endX.toInt()
    for (x in range) {
        val heightFactor = if (incremental) {
            (x - range.first).toFloat() / (range.last - range.first).coerceAtLeast(1)
        } else {
            1f
        }
        val radians = waveSpread * (x - range.first + waveShiftPx) / waveLengthPx * (2 * PI)
        val y = (sin(radians) * heightFactor * waveHeightPx + size.height) / 2
        lineTo(x.toFloat(), y.toFloat())
    }
}

/**
 * Default constants for wavy path components.
 *
 * Provides sensible defaults for wave geometry and animation timing shared between
 * [WavyProgressIndicator][presentation.core.ui.source.kit.atom.progress.WavyProgressIndicator] and
 * [WavySlider][presentation.core.ui.source.kit.atom.slider.WavySlider].
 */
public object WavyPathDefaults {

    /** Default wave spread animation duration in milliseconds. */
    public const val SPREAD_DURATION_MS: Int = 3_000

    /** Default wave height animation duration in milliseconds. */
    public const val HEIGHT_ANIM_DURATION_MS: Int = 300

    /** Minimum component height in dp. */
    public const val MIN_COMPONENT_HEIGHT_DP: Int = 12
}

/**
 * Shared [Path] instance reused across frames to avoid allocations.
 *
 * Because Compose draws on a single thread per canvas, reusing a path object is safe
 * as long as [Path.rewind] is called before each use.
 */
private val reusablePath = Path()
