package presentation.core.ui.source.kit.atom.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.core.state.StateButton

/**
 * Primary design-system button that delegates to [StateButton] for interaction tracking.
 *
 * @param text Label displayed inside the button.
 * @param onClick Callback invoked when the button is tapped.
 * @param style Visual style variant (primary, secondary, or text).
 * @param size Predefined size category controlling padding, icon size, and minimum height.
 * @param modifier Modifier applied to the root composable.
 * @param startIcon Optional icon rendered before the label.
 * @param endIcon Optional icon rendered after the label.
 * @param enabled Whether the button accepts input; disabled buttons use muted colors.
 * @param isLoading When `true`, replaces the content with a loading indicator.
 * @param textStyle Typography style applied to the button label.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
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
