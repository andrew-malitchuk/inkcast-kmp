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
import androidx.compose.ui.unit.Dp
import presentation.core.ui.source.kit.atom.button.core.animate.AnimateIconButton
import presentation.core.ui.source.kit.atom.button.core.model.ButtonColor
import presentation.core.ui.source.kit.atom.button.core.model.ButtonInteractionState
import presentation.core.ui.source.kit.atom.button.core.model.ButtonSizeValues

@Composable
internal fun StateIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    colors: ButtonColor,
    sizes: ButtonSizeValues,
    corner: Dp,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isLoading: Boolean,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    var interactionState = 0
    if (isHovered) interactionState = interactionState.or(ButtonInteractionState.HOVER)
    if (isPressed) interactionState = interactionState.or(ButtonInteractionState.PRESSED)
    if (isFocused) interactionState = interactionState.or(ButtonInteractionState.FOCUSED)
    if (isSelected) interactionState = interactionState.or(ButtonInteractionState.SELECTED)

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

    AnimateIconButton(
        icon = icon,
        backgroundColor = backgroundColor,
        iconColor = foregroundColor,
        borderColor = borderColor,
        corner = corner,
        iconSize = sizes.iconSize,
        borderSize = sizes.borderSize,
        minHeight = sizes.minHeight,
        paddings = sizes.contentPadding,
        modifier = currentModifier,
        isLoading = isLoading,
        loadingSize = sizes.loadingSize,
    )
}
