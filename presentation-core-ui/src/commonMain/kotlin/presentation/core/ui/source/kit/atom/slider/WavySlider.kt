package presentation.core.ui.source.kit.atom.slider

import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import presentation.core.ui.core.draw.WavyPathDefaults
import presentation.core.ui.core.draw.buildWavyPath

/**
 * A wavy slider built on top of `compose.foundation` and `compose.ui`,
 * without any Material dependency.
 *
 * The active (filled) portion of the track is rendered as an animated sine wave, the inactive
 * (unfilled) portion is a straight line, and a circular thumb indicates the current value.
 * The slider responds to both taps and horizontal drags.
 *
 * Usage:
 * ```kotlin
 * var volume by remember { mutableFloatStateOf(0.5f) }
 * WavySlider(
 *     value = volume,
 *     onValueChange = { volume = it },
 *     activeColor = MaterialTheme.colorScheme.primary,
 * )
 * ```
 *
 * @param value Current slider value, clamped internally to the `0f..1f` range.
 * @param onValueChange Callback invoked with the new value when the user drags or taps the track.
 * @param modifier [Modifier] applied to the root [Box] container.
 * @param enabled When `false`, the slider ignores touch input and renders the thumb at reduced
 *   opacity.
 * @param activeColor Color of the wavy (filled) portion of the track.
 * @param inactiveColor Color of the straight (unfilled) portion of the track.
 * @param thumbColor Fill color of the circular thumb indicator.
 * @param thumbRadius Radius of the circular thumb.
 * @param waveLength Horizontal distance over which one full wave cycle repeats.
 * @param waveHeight Peak-to-trough height of the wave.
 * @param waveThickness Stroke width of the wavy active line.
 * @param trackThickness Stroke width of the inactive track line.
 * @param waveSpeed Horizontal movement speed of the wave animation (dp per second).
 *   Set to `0.dp` to disable horizontal wave movement.
 * @param onValueChangeFinished Optional callback invoked when a drag gesture ends or a tap
 *   completes, useful for committing the final value.
 *
 * @see buildWavyPath
 */
@Composable
public fun WavySlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    activeColor: Color = Color(0xFF6750A4),
    inactiveColor: Color = Color(0xFFE8DEF8),
    thumbColor: Color = Color(0xFF6750A4),
    thumbRadius: Dp = 10.dp,
    waveLength: Dp = 20.dp,
    waveHeight: Dp = 6.dp,
    waveThickness: Dp = 4.dp,
    trackThickness: Dp = 4.dp,
    waveSpeed: Dp = 10.dp,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    val clampedValue = value.coerceIn(0f, 1f)

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
    var spreadTarget by remember { mutableFloatStateOf(0f) }
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
        .coerceAtLeast(thumbRadius * 2)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(componentHeight)
            .semantics {
                setProgress { targetValue ->
                    val clamped = targetValue.coerceIn(0f, 1f)
                    onValueChange(clamped)
                    true
                }
            }
            .then(
                if (enabled) {
                    Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val fraction = (offset.x / size.width).coerceIn(0f, 1f)
                                onValueChange(fraction)
                                onValueChangeFinished?.invoke()
                            }
                        }
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onDragEnd = { onValueChangeFinished?.invoke() },
                                onHorizontalDrag = { change, _ ->
                                    change.consume()
                                    val fraction =
                                        (change.position.x / size.width).coerceIn(0f, 1f)
                                    onValueChange(fraction)
                                },
                            )
                        }
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val thumbRadiusPx = thumbRadius.toPx()
            val trackStart = Offset(thumbRadiusPx, center.y)
            val trackEnd = Offset(size.width - thumbRadiusPx, center.y)
            val trackWidth = trackEnd.x - trackStart.x
            val valueX = trackStart.x + trackWidth * clampedValue
            val valueOffset = Offset(valueX, center.y)

            // Inactive track: straight line from current value to the end.
            if (clampedValue < 1f) {
                drawLine(
                    color = inactiveColor,
                    start = Offset(valueX + thumbRadiusPx, center.y),
                    end = trackEnd,
                    strokeWidth = trackThickness.toPx(),
                    cap = StrokeCap.Round,
                )
            }

            // Active track: wavy line from the start to current value.
            if (clampedValue > 0f) {
                val activeEnd = valueX - thumbRadiusPx
                if (activeEnd > trackStart.x) {
                    if (waveLength <= 0.dp || animatedWaveHeight == 0.dp) {
                        drawLine(
                            color = activeColor,
                            start = trackStart,
                            end = Offset(activeEnd, center.y),
                            strokeWidth = waveThickness.toPx(),
                            cap = StrokeCap.Round,
                        )
                    } else {
                        val path = buildWavyPath(
                            startX = trackStart.x + waveThickness.toPx() / 2,
                            endX = activeEnd - waveThickness.toPx() / 2,
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

            // Thumb: circular indicator at the current value position.
            drawCircle(
                color = thumbColor,
                radius = thumbRadiusPx,
                center = valueOffset,
                alpha = if (enabled) 1f else 0.38f,
            )
        }
    }
}
