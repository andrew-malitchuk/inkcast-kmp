package presentation.core.ui.source.kit.atom.crop

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A gesture-driven crop box that displays the processed e-ink preview and
 * lets the user pan/zoom to adjust the crop region of the source image.
 *
 * The viewport aspect ratio defaults to 480:800 (portrait e-ink).
 * [displayBitmap] is what the user sees (typically the dithered preview);
 * [sourceWidth]/[sourceHeight] drive the gesture math so that normalised
 * offsets and zoom map correctly to the full-resolution source.
 *
 * During an active gesture the preview is visually transformed for instant
 * feedback. When all fingers lift, transforms animate back to identity and
 * [onCropFinished] fires so the host can re-process and supply a new
 * [displayBitmap].
 *
 * @param displayBitmap Bitmap shown in the viewport (preview or source fallback).
 * @param sourceWidth   Width of the original source image (for gesture math).
 * @param sourceHeight  Height of the original source image (for gesture math).
 * @param offsetX       Current normalised horizontal offset (0–1).
 * @param offsetY       Current normalised vertical offset (0–1).
 * @param zoom          Current zoom level (≥ 1).
 * @param onCropChanged Called during gestures with updated offset/zoom.
 * @param onCropFinished Called when the gesture ends (all fingers lifted).
 * @param modifier      Modifier applied to the outer container.
 * @param viewportAspect Width/height ratio of the crop viewport.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun CropBox(
    displayBitmap: ImageBitmap,
    sourceWidth: Int,
    sourceHeight: Int,
    offsetX: Float,
    offsetY: Float,
    zoom: Float,
    onCropChanged: (offsetX: Float, offsetY: Float, zoom: Float) -> Unit,
    onCropFinished: () -> Unit,
    modifier: Modifier = Modifier,
    viewportAspect: Float = 480f / 800f,
) {
    var viewportW by remember { mutableFloatStateOf(0f) }
    var viewportH by remember { mutableFloatStateOf(0f) }

    val latestZoom by rememberUpdatedState(zoom)
    val latestOffX by rememberUpdatedState(offsetX)
    val latestOffY by rememberUpdatedState(offsetY)
    val latestOnCropChanged by rememberUpdatedState(onCropChanged)
    val latestOnCropFinished by rememberUpdatedState(onCropFinished)

    val imgW = sourceWidth.toFloat()
    val imgH = sourceHeight.toFloat()

    // During gesture: mutable state for instant visual feedback (no suspend)
    var isGesturing by remember { mutableStateOf(false) }
    var liveScale by remember { mutableFloatStateOf(1f) }
    var livePanX by remember { mutableFloatStateOf(0f) }
    var livePanY by remember { mutableFloatStateOf(0f) }

    // After gesture: Animatable for smooth snap-back
    val animScale = remember { Animatable(1f) }
    val animPan = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    val scope = rememberCoroutineScope()
    var snapBackJob by remember { mutableStateOf<Job?>(null) }

    // Derive the final visual transform: live values during gesture, animated values otherwise
    val displayScale = if (isGesturing) liveScale else animScale.value
    val displayPanX = if (isGesturing) livePanX else animPan.value.x
    val displayPanY = if (isGesturing) livePanY else animPan.value.y

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(viewportAspect)
            .clipToBounds()
            .background(Color.Black)
            .onSizeChanged { size: IntSize ->
                viewportW = size.width.toFloat()
                viewportH = size.height.toFloat()
            }
            .pointerInput(sourceWidth, sourceHeight) {
                val touchSlop = viewConfiguration.touchSlop

                awaitEachGesture {
                    awaitFirstDown(requireUnconsumed = false)

                    var currentZoom = latestZoom
                    var currentOffX = latestOffX
                    var currentOffY = latestOffY
                    var pastSlop = false
                    var accumulatedPan = Offset.Zero
                    var accumulatedZoom = 1f

                    // Cancel any running snap-back and reset
                    snapBackJob?.cancel()
                    isGesturing = true
                    liveScale = 1f
                    livePanX = 0f
                    livePanY = 0f

                    do {
                        val event = awaitPointerEvent()
                        val canceled = event.changes.any { it.isConsumed }
                        if (canceled) break

                        val zoomChange = event.calculateZoom()
                        val panChange = event.calculatePan()

                        if (!pastSlop) {
                            accumulatedZoom *= zoomChange
                            accumulatedPan += panChange
                            val zoomMotion = kotlin.math.abs(1f - accumulatedZoom) * 100f
                            val panMotion = accumulatedPan.getDistance()
                            if (zoomMotion > touchSlop || panMotion > touchSlop) {
                                pastSlop = true
                            }
                        }

                        if (pastSlop && (zoomChange != 1f || panChange != Offset.Zero)) {
                            liveScale *= zoomChange
                            livePanX += panChange.x
                            livePanY += panChange.y

                            val newZoom = (currentZoom * zoomChange).coerceIn(1f, 5f)

                            val imgAspect = imgW / imgH
                            val fitW: Float
                            val fitH: Float
                            if (imgAspect > viewportAspect) {
                                val fitScale = viewportH / imgH
                                fitH = viewportH
                                fitW = imgW * fitScale
                            } else {
                                val fitScale = viewportW / imgW
                                fitW = viewportW
                                fitH = imgH * fitScale
                            }

                            val scaledW = fitW * newZoom
                            val scaledH = fitH * newZoom
                            val maxPanX = ((scaledW - viewportW) / 2f).coerceAtLeast(0f)
                            val maxPanY = ((scaledH - viewportH) / 2f).coerceAtLeast(0f)

                            val curPanX = if (maxPanX > 0f) (currentOffX - 0.5f) * 2f * maxPanX else 0f
                            val curPanY = if (maxPanY > 0f) (currentOffY - 0.5f) * 2f * maxPanY else 0f

                            val newPanX = (curPanX - panChange.x).coerceIn(-maxPanX, maxPanX)
                            val newPanY = (curPanY - panChange.y).coerceIn(-maxPanY, maxPanY)

                            val newOffX = (if (maxPanX > 0f) (newPanX / (2f * maxPanX)) + 0.5f else 0.5f)
                                .coerceIn(0f, 1f)
                            val newOffY = (if (maxPanY > 0f) (newPanY / (2f * maxPanY)) + 0.5f else 0.5f)
                                .coerceIn(0f, 1f)

                            currentZoom = newZoom
                            currentOffX = newOffX
                            currentOffY = newOffY

                            latestOnCropChanged(newOffX, newOffY, newZoom)
                            event.changes.forEach { if (it.positionChanged()) it.consume() }
                        }
                    } while (event.changes.any { it.pressed })

                    // Hand off to animated snap-back
                    val endScale = liveScale
                    val endPan = Offset(livePanX, livePanY)
                    isGesturing = false

                    val spec = tween<Float>(durationMillis = 250)
                    val specOffset = tween<Offset>(durationMillis = 250)
                    snapBackJob = scope.launch {
                        launch { animScale.snapTo(endScale); animScale.animateTo(1f, spec) }
                        launch { animPan.snapTo(endPan); animPan.animateTo(Offset.Zero, specOffset) }
                    }

                    if (pastSlop) {
                        latestOnCropFinished()
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            bitmap = displayBitmap,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = displayScale
                    scaleY = displayScale
                    translationX = displayPanX
                    translationY = displayPanY
                },
        )
    }
}
