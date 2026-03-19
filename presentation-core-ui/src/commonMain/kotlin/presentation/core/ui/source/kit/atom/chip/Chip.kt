package presentation.core.ui.source.kit.atom.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import presentation.core.styling.core.Theme

/**
 * A selectable chip with filled or outlined appearance based on selection state.
 *
 * @param label Chip text content.
 * @param selected Whether the chip is currently selected.
 * @param onClick Callback for chip tap.
 * @param modifier Modifier to be applied to the chip [Box].
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun Chip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(Theme.spacing.spacingXL)
    val backgroundColor = if (selected) Theme.color.inkMain else Theme.color.surface
    val contentColor = if (selected) Theme.color.surface else Theme.color.inkMain
    val borderColor = if (selected) Theme.color.inkMain else Theme.color.outlineLow

    Text(
        text = label,
        style = Theme.typography.caption,
        color = contentColor,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(width = Theme.spacing.spacingXXS, color = borderColor, shape = shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(
                horizontal = Theme.spacing.spacingL,
                vertical = Theme.spacing.spacingS,
            ),
    )
}
