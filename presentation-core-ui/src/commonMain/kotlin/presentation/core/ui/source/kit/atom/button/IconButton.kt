package presentation.core.ui.source.kit.atom.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import presentation.core.styling.core.Theme
import presentation.core.ui.core.ext.has
import presentation.core.ui.source.kit.atom.button.core.model.ButtonColor
import presentation.core.ui.source.kit.atom.button.core.model.ButtonInteractionState
import presentation.core.ui.source.kit.atom.button.core.state.StateIconButton

@Composable
public fun IconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    size: ButtonSizeType,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    isLoading: Boolean = false,
) {
    val sizeValues = size.resolveIcon()
    val colors = iconButtonColors()
    val corner = Theme.spacing.spacingM

    StateIconButton(
        onClick = onClick,
        icon = icon,
        colors = colors,
        sizes = sizeValues,
        corner = corner,
        modifier = modifier,
        enabled = enabled,
        isSelected = isSelected,
        isLoading = isLoading,
    )
}

@Composable
private fun iconButtonColors(): ButtonColor {
    val containerColor = Theme.color.surfaceVariant
    val contentColor = Theme.color.inkMain
    val selectedContainerColor = Theme.color.brand
    val selectedContentColor = Theme.color.surface
    val disabledContentColor = Theme.color.inkSubtle
    val disabledContainerColor = Theme.color.surfaceVariant

    return object : ButtonColor {
        @Composable
        override fun borderColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color> =
            rememberUpdatedState(Color.Transparent)

        @Composable
        override fun foregroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color> =
            rememberUpdatedState(
                when {
                    !enabled -> disabledContentColor
                    interactionState has ButtonInteractionState.SELECTED -> selectedContentColor
                    else -> contentColor
                },
            )

        @Composable
        override fun backgroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color> =
            rememberUpdatedState(
                when {
                    !enabled -> disabledContainerColor
                    interactionState has ButtonInteractionState.PRESSED -> Theme.color.brandVariant
                    interactionState has ButtonInteractionState.SELECTED -> selectedContainerColor
                    else -> containerColor
                },
            )
    }
}
