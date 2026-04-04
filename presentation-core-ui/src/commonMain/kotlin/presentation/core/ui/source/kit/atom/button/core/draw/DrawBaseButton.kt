package presentation.core.ui.source.kit.atom.button.core.draw

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import presentation.core.ui.core.configuration.AnimationConfiguration
import presentation.core.ui.source.kit.atom.shape.SquircleShape

/**
 * Shared base container for all button variants, providing squircle background,
 * border, and an animated content-size transition between normal and loading states.
 *
 * @param modifier Modifier applied to the outer [Box].
 * @param backgroundColor Fill color of the button container.
 * @param borderColor Stroke color of the button border.
 * @param corner Corner radius applied via [SquircleShape].
 * @param borderSize Stroke width of the border.
 * @param paddings Inner content padding.
 * @param isLoading When `true`, swaps [content] for a loading placeholder.
 * @param loadingSize Diameter of the loading indicator.
 * @param horizontalArrangement Content alignment inside the container.
 * @param content Slot for the normal (non-loading) button content.
 */
@Composable
internal fun DrawBaseButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderColor: Color,
    corner: Dp,
    borderSize: Dp,
    paddings: PaddingValues,
    isLoading: Boolean,
    loadingSize: Dp,
    horizontalArrangement: Alignment = Alignment.Center,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
        modifier
            .animateContentSize(
                animationSpec =
                tween(
                    durationMillis = AnimationConfiguration.Duration.DEFAULT,
                    easing = LinearOutSlowInEasing,
                ),
            )
            .border(
                width = borderSize,
                color = borderColor,
                shape = SquircleShape(corner),
            )
            .background(
                color = backgroundColor,
                shape = SquircleShape(corner),
            )
            .padding(paddings),
        contentAlignment = horizontalArrangement,
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = AnimationConfiguration.Transition.default(),
            label = "DrawBaseButton: AnimatedContent",
        ) { loading ->
            if (loading) {
                Box(
                    modifier =
                    Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    // TODO: loading indicator (e.g. Lottie)
                }
            } else {
                content()
            }
        }
    }
}
