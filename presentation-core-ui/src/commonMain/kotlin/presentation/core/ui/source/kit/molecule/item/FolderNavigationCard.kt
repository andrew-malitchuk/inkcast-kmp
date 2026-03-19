package presentation.core.ui.source.kit.molecule.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.container.IconContainer
import presentation.core.ui.source.kit.atom.icon.ChevronLeft
import presentation.core.ui.source.kit.atom.icon.Folder
import presentation.core.ui.source.kit.atom.shape.SquircleShape

@Composable
public fun FolderNavigationCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(SquircleShape(Theme.spacing.spacingM))
            .background(Theme.color.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(Theme.spacing.spacingL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
    ) {
        IconContainer(
            modifier = Modifier.size(48.dp),
            icon = Folder,
            backgroundColor = Theme.color.surfaceVariant,
            foregroundColor = Theme.color.inkMain,
        )
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = Theme.typography.bodyEmphasis,
            color = Theme.color.inkMain,
            maxLines = 1,
        )
        Image(
            imageVector = ChevronLeft,
            contentDescription = null,
            colorFilter = ColorFilter.tint(Theme.color.inkSubtle),
        )
    }
}
