package presentation.core.ui.core.modifier

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme

public data class ShimmerState(val isLoading: Boolean)

public val LocalShimmerState: androidx.compose.runtime.ProvidableCompositionLocal<ShimmerState> = compositionLocalOf { ShimmerState(isLoading = false) }

@Composable
public fun ShimmerProvider(
    isLoading: Boolean = true,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalShimmerState provides ShimmerState(isLoading = isLoading),
        content = content,
    )
}

@Composable
public fun Modifier.shimmerable(
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = Theme.color.inkSubtle.copy(alpha = 0.15f),
    highlightColor: Color = Theme.color.inkSubtle.copy(alpha = 0.4f),
    durationMillis: Int = 1200,
): Modifier {
    if (!LocalShimmerState.current.isLoading) return this

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(color, highlightColor, color),
        start = Offset(translateAnim - 200f, translateAnim - 200f),
        end = Offset(translateAnim, translateAnim),
    )

    return this
        .background(color = color, shape = shape)
        .background(brush = shimmerBrush, shape = shape)
        .drawWithContent { }
}
