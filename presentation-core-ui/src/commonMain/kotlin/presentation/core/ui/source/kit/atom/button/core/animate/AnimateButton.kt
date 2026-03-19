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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import presentation.core.ui.core.configuration.AnimationConfiguration
import presentation.core.ui.source.kit.atom.button.core.draw.DrawButton

@Composable
internal fun AnimateButton(
    text: String,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    backgroundColor: Color,
    foregroundColor: Color,
    borderColor: Color,
    corner: Dp,
    iconSize: Dp,
    borderSize: Dp,
    spacing: Dp,
    minHeight: Dp,
    paddings: PaddingValues,
    textStyle: TextStyle,
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
        label = "AnimateButton: borderColor",
    )
    val animationBackgroundColor by animateColorAsState(
        animationSpec = colorAnimationSpec,
        targetValue = backgroundColor,
        label = "AnimateButton: backgroundColor",
    )
    val animationForegroundColor by animateColorAsState(
        animationSpec = colorAnimationSpec,
        targetValue = foregroundColor,
        label = "AnimateButton: foregroundColor",
    )

    val localModifier =
        modifier.animateContentSize(
            animationSpec =
            tween(
                durationMillis = AnimationConfiguration.Duration.DEFAULT,
                easing = LinearEasing,
            ),
        )

    DrawButton(
        text = text,
        startIcon = startIcon,
        endIcon = endIcon,
        backgroundColor = animationBackgroundColor,
        foregroundColor = animationForegroundColor,
        borderColor = animationBorderColor,
        corner = corner,
        iconSize = iconSize,
        borderSize = borderSize,
        spacing = spacing,
        minHeight = minHeight,
        paddings = paddings,
        textStyle = textStyle,
        modifier = localModifier,
        isLoading = isLoading,
        loadingSize = loadingSize,
    )
}
