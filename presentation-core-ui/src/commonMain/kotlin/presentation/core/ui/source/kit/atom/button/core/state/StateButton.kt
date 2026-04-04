package presentation.core.ui.source.kit.atom.button.core.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import presentation.core.ui.source.kit.atom.button.core.animate.AnimateButton
import presentation.core.ui.source.kit.atom.button.core.model.ButtonColor
import presentation.core.ui.source.kit.atom.button.core.model.ButtonInteractionState
import presentation.core.ui.source.kit.atom.button.core.model.ButtonSizeValues

/**
 * Collects hover, press, and focus interaction states, builds a bitmask, resolves
 * colors from [colors], and forwards everything to [AnimateButton].
 *
 * This is an internal orchestration layer between the public [Button] API and the
 * animation / draw layers.
 */
@Composable
internal fun StateButton(
    text: String,
    onClick: () -> Unit,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    colors: ButtonColor,
    sizes: ButtonSizeValues,
    corner: Dp,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isLoading: Boolean,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Build the bitmask from individual interaction flags
    var interactionState = 0
    if (isHovered) interactionState = interactionState.or(ButtonInteractionState.HOVER)
    if (isPressed) interactionState = interactionState.or(ButtonInteractionState.PRESSED)
    if (isFocused) interactionState = interactionState.or(ButtonInteractionState.FOCUSED)

    val currentModifier =
        modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = {
                if (!isLoading) {
                    onClick()
                }
            },
            role = Button,
        )

    val backgroundColor = colors.backgroundColor(interactionState, enabled, isLoading).value
    val foregroundColor = colors.foregroundColor(interactionState, enabled, isLoading).value
    val borderColor = colors.borderColor(interactionState, enabled, isLoading).value

    AnimateButton(
        text = text,
        startIcon = startIcon,
        endIcon = endIcon,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        borderColor = borderColor,
        corner = corner,
        iconSize = sizes.iconSize,
        borderSize = sizes.borderSize,
        spacing = sizes.spacing,
        minHeight = sizes.minHeight,
        paddings = sizes.contentPadding,
        textStyle = textStyle,
        modifier = currentModifier,
        isLoading = isLoading,
        loadingSize = sizes.loadingSize,
    )
}
