package presentation.core.ui.source.kit.atom.button.core.animate

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import presentation.core.ui.core.configuration.AnimationConfiguration
import presentation.core.ui.source.kit.atom.button.core.draw.DrawIconButton

@Composable
internal fun AnimateIconButton(
    icon: ImageVector,
    backgroundColor: Color,
    iconColor: Color,
    borderColor: Color,
    corner: Dp,
    iconSize: Dp,
    borderSize: Dp,
    minHeight: Dp,
    paddings: PaddingValues,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    loadingSize: Dp,
) {
    val colorAnimationSpec =
        tween<Color>(
            durationMillis = AnimationConfiguration.Duration.DEFAULT,
            easing = LinearEasing,
        )

    val animationBorderColor by animateColorAsState(
        animationSpec = colorAnimationSpec,
        targetValue = borderColor,
        label = "AnimateIconButton: borderColor",
    )
    val animationBackgroundColor by animateColorAsState(
        animationSpec = colorAnimationSpec,
        targetValue = backgroundColor,
        label = "AnimateIconButton: backgroundColor",
    )
    val animationForegroundColor by animateColorAsState(
        animationSpec = colorAnimationSpec,
        targetValue = iconColor,
        label = "AnimateIconButton: foregroundColor",
    )

    val localModifier =
        modifier.animateContentSize(
            animationSpec =
            tween(
                durationMillis = AnimationConfiguration.Duration.DEFAULT,
                easing = LinearEasing,
            ),
        )

    DrawIconButton(
        icon = icon,
        backgroundColor = animationBackgroundColor,
        iconColor = animationForegroundColor,
        borderColor = animationBorderColor,
        corner = corner,
        iconSize = iconSize,
        borderSize = borderSize,
        minHeight = minHeight,
        paddings = paddings,
        isLoading = isLoading,
        loadingSize = loadingSize,
        modifier = localModifier,
    )
}
