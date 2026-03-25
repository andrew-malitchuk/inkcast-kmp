package presentation.core.ui.source.kit.atom.progress

import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import presentation.core.ui.core.draw.WavyPathDefaults
import presentation.core.ui.core.draw.buildWavyPath

/**
 * A wavy linear progress indicator built on top of `compose.foundation` and `compose.ui`,
 * without any Material dependency.
 *
 * The active (filled) portion of the track is rendered as an animated sine wave, while the
 * inactive (unfilled) portion is a straight line. The wave continuously shifts horizontally
 * and can optionally "spread" into view when first composed.
 *
 * Usage:
 * ```kotlin
 * WavyProgressIndicator(
 *     progress = downloadProgress,
 *     activeColor = MaterialTheme.colorScheme.primary,
 *     inactiveColor = MaterialTheme.colorScheme.surfaceVariant,
 * )
 * ```
 *
 * @param progress Current progress value, clamped internally to the `0f..1f` range.
 * @param modifier [Modifier] applied to the underlying [Canvas].
 * @param activeColor Color of the wavy (filled) portion of the track.
 * @param inactiveColor Color of the straight (unfilled) portion of the track.
 * @param waveLength Horizontal distance over which one full wave cycle repeats.
 * @param waveHeight Peak-to-trough height of the wave.
 * @param waveThickness Stroke width of the wavy active line.
 * @param trackThickness Stroke width of the inactive track line.
 * @param waveSpeed Horizontal movement speed of the wave animation (dp per second).
 *   Set to `0.dp` to disable horizontal wave movement.
 * @param animateWaveAppearance When `true`, the wave amplitude spreads from zero to full height
 *   on first composition, producing a smooth reveal effect.
 *
 * @see buildWavyPath
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun WavyProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF6750A4),
    inactiveColor: Color = Color(0xFFE8DEF8),
    waveLength: Dp = 20.dp,
    waveHeight: Dp = 3.dp,
    waveThickness: Dp = 3.dp,
    trackThickness: Dp = 3.dp,
    waveSpeed: Dp = 10.dp,
    animateWaveAppearance: Boolean = true,
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    // Continuous horizontal wave shift driven by frame time.
    val waveShift = remember { mutableStateOf(0.dp) }
    LaunchedEffect(waveSpeed) {
        if (waveSpeed <= 0.dp) return@LaunchedEffect
        val startShift = waveShift.value
        val startTime = withFrameNanos { it }
        while (true) {
            val elapsed = (withFrameNanos { it } - startTime) / 1_000_000_000f
            waveShift.value = startShift + waveSpeed * elapsed
        }
    }

    // Wave amplitude spread: animates from flat to full height on first composition.
    var spreadTarget by remember {
        mutableFloatStateOf(if (animateWaveAppearance) 0f else 1f)
    }
    val waveSpread by animateFloatAsState(
        targetValue = spreadTarget,
        animationSpec = tween(
            durationMillis = WavyPathDefaults.SPREAD_DURATION_MS,
            easing = EaseOutQuad,
        ),
    )
    LaunchedEffect(Unit) { spreadTarget = 1f }

    // Smooth transition when waveHeight changes at runtime.
    val animatedWaveHeight by animateDpAsState(
        targetValue = waveHeight,
        animationSpec = tween(
            durationMillis = WavyPathDefaults.HEIGHT_ANIM_DURATION_MS,
            easing = FastOutSlowInEasing,
        ),
    )

    val componentHeight = (waveThickness + waveHeight.value.absoluteValue.dp)
        .coerceAtLeast(WavyPathDefaults.MIN_COMPONENT_HEIGHT_DP.dp)

    Canvas(
        modifier = modifier
            .progressSemantics(clampedProgress)
            .fillMaxWidth()
            .height(componentHeight),
    ) {
        val start = Offset(0f, center.y)
        val end = Offset(size.width, center.y)
        val valueX = start.x + (end.x - start.x) * clampedProgress
        val valueOffset = Offset(valueX, center.y)

        // Inactive track: straight line from current progress to the end.
        if (clampedProgress < 1f) {
            drawLine(
                color = inactiveColor,
                start = valueOffset,
                end = end,
                strokeWidth = trackThickness.toPx(),
                cap = StrokeCap.Round,
            )
        }

        // Active track: wavy line from the start to current progress.
        if (clampedProgress > 0f) {
            if (waveLength <= 0.dp || animatedWaveHeight == 0.dp) {
                drawLine(
                    color = activeColor,
                    start = start,
                    end = valueOffset,
                    strokeWidth = waveThickness.toPx(),
                    cap = StrokeCap.Round,
                )
            } else {
                val path = buildWavyPath(
                    startX = start.x + waveThickness.toPx() / 2,
                    endX = valueOffset.x - waveThickness.toPx() / 2,
                    centerY = size.height / 2f,
                    waveLengthPx = waveLength.toPx(),
                    waveHeightPx = animatedWaveHeight.toPx().absoluteValue,
                    waveSpread = waveSpread,
                    waveShiftPx = waveShift.value.toPx(),
                    incremental = false,
                )
                drawPath(
                    path = path,
                    color = activeColor,
                    style = Stroke(
                        width = waveThickness.toPx(),
                        join = StrokeJoin.Round,
                        cap = StrokeCap.Round,
                    ),
                )
            }
        }
    }
}
