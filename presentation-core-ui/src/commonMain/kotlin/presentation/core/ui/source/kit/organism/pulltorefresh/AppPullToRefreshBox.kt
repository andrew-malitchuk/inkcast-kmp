package presentation.core.ui.source.kit.organism.pulltorefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.progress.WavyProgressIndicator
import kotlin.math.roundToInt

private val REFRESH_TRIGGER = 80.dp
private val MAX_DRAG = 140.dp
private const val DRAG_MULTIPLIER = 0.5f

/**
 * Custom pull-to-refresh container using the app's [WavyProgressIndicator].
 *
 * Detects overscroll via [nestedScroll] and shows a wavy progress bar
 * that fills proportionally to the pull distance. Once the user pulls
 * past [REFRESH_TRIGGER] and releases, [onRefresh] is invoked and the
 * indicator enters an indeterminate animation until [isRefreshing]
 * returns to `false`.
 *
 * @param isRefreshing Whether a refresh operation is currently in progress.
 * @param onRefresh Called when the user completes a valid pull gesture.
 * @param modifier Modifier for the root container.
 * @param content The scrollable content.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun AppPullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val refreshTriggerPx = with(density) { REFRESH_TRIGGER.toPx() }
    val maxDragPx = with(density) { MAX_DRAG.toPx() }

    var rawPullOffset by remember { mutableFloatStateOf(0f) }
    val indicatorOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            rawPullOffset = 0f
            indicatorOffset.animateTo(
                targetValue = 0f,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            )
        } else {
            indicatorOffset.snapTo(refreshTriggerPx)
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                if (rawPullOffset > 0f && available.y < 0f) {
                    val consumed = available.y.coerceAtLeast(-rawPullOffset)
                    rawPullOffset += consumed
                    scope.launch { indicatorOffset.snapTo(rawPullOffset) }
                    return Offset(0f, consumed)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                if (available.y > 0f && source == NestedScrollSource.UserInput) {
                    rawPullOffset = (rawPullOffset + available.y * DRAG_MULTIPLIER)
                        .coerceAtMost(maxDragPx)
                    scope.launch { indicatorOffset.snapTo(rawPullOffset) }
                    return Offset(0f, available.y)
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (rawPullOffset >= refreshTriggerPx) {
                    onRefresh()
                    indicatorOffset.animateTo(
                        targetValue = refreshTriggerPx,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    )
                } else {
                    rawPullOffset = 0f
                    indicatorOffset.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                    )
                }
                return Velocity.Zero
            }
        }
    }

    Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
        Box(
            modifier = Modifier.offset {
                IntOffset(0, indicatorOffset.value.roundToInt())
            },
        ) {
            content()
        }

        if (indicatorOffset.value > 1f) {
            val progress = if (isRefreshing) {
                1f
            } else {
                (indicatorOffset.value / refreshTriggerPx).coerceIn(0f, 1f)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(0, (indicatorOffset.value - with(density) { 24.dp.toPx() }).roundToInt().coerceAtLeast(0))
                    }
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                WavyProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth(),
                    activeColor = Theme.color.inkMain,
                    inactiveColor = Theme.color.outlineLow,
                )
            }
        }
    }
}
