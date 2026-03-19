package presentation.core.ui.source.kit.atom.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.core.state.StateButton

@Composable
public fun Button(
    text: String,
    onClick: () -> Unit,
    style: ButtonStyle,
    size: ButtonSizeType,
    modifier: Modifier = Modifier,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    textStyle: TextStyle = Theme.typography.action,
) {
    val sizeValues = size.resolve(hasBorder = style is ButtonStyle.Primary)
    val colors = style.colors()
    val corner = style.corner(sizeValues.minHeight)

    StateButton(
        text = text,
        onClick = onClick,
        startIcon = startIcon,
        endIcon = endIcon,
        colors = colors,
        sizes = sizeValues,
        corner = corner,
        textStyle = textStyle,
        modifier = modifier,
        enabled = enabled,
        isLoading = isLoading,
    )
}
