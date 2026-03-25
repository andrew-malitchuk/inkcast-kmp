package presentation.core.ui.source.kit.molecule.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.basicMarquee
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.core.styling.core.Theme
import presentation.core.styling.source.theme.AppTheme
import presentation.core.ui.source.kit.atom.container.IconContainer
import presentation.core.ui.source.kit.atom.icon.BookOpen
import presentation.core.ui.source.kit.atom.icon.FileText
import presentation.core.ui.source.kit.atom.icon.Folder
import presentation.core.ui.source.kit.atom.icon.Image
import presentation.core.ui.source.kit.atom.icon.MoreVertical
import presentation.core.ui.source.kit.atom.shape.SquircleShape

/**
 * A card representing a file-system item (book, file, image, or folder) with an icon,
 * title, description, and a "more" action button.
 *
 * @param modifier Modifier to be applied to the root [Row].
 * @param title Primary label displayed next to the icon.
 * @param description Secondary label shown below the title.
 * @param type Determines the leading icon variant.
 * @param onCallback Called when the user taps the card body ([ItemCardCallback.OnClick]) or
 *   the overflow button ([ItemCardCallback.OnMoreClick]).
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun ItemCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    type: ItemCardType,
    onCallback: (ItemCardCallback) -> Unit,
) {
    val icon: ImageVector = when (type) {
        ItemCardType.Book -> BookOpen
        ItemCardType.File -> FileText
        ItemCardType.Image -> Image
        ItemCardType.Folder -> Folder
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(SquircleShape(Theme.spacing.spacingM))
            .background(Theme.color.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onCallback(ItemCardCallback.OnClick) },
            )
            .padding(Theme.spacing.spacingL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
    ) {
        IconContainer(
            modifier = Modifier.size(48.dp),
            icon = icon,
            backgroundColor = Theme.color.surfaceVariant,
            foregroundColor = Theme.color.inkMain,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingXXS),
        ) {
            Text(
                text = title,
                modifier = Modifier.basicMarquee(),
                style = Theme.typography.bodyEmphasis,
                color = Theme.color.inkMain,
                maxLines = 1,
            )
            Text(
                text = description,
                style = Theme.typography.caption,
                color = Theme.color.inkSubtle,
            )
        }
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onCallback(ItemCardCallback.OnMoreClick) },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                imageVector = MoreVertical,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Theme.color.inkSubtle),
            )
        }
    }
}

/**
 * Visual type of an [ItemCard], controlling which icon is displayed.
 */
public enum class ItemCardType {
    Book,
    File,
    Image,
    Folder,
}

/**
 * Sealed set of user interactions emitted by [ItemCard].
 */
public sealed interface ItemCardCallback {
    /** The user tapped the card body. */
    public data object OnClick : ItemCardCallback

    /** The user tapped the overflow ("more") button. */
    public data object OnMoreClick : ItemCardCallback
}

@Preview(showBackground = true)
@Composable
private fun ItemCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(Theme.spacing.spacingL),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
        ) {
            ItemCard(
                title = "My Book",
                description = "A short description",
                type = ItemCardType.Book,
                onCallback = {},
            )
            ItemCard(
                title = "Vacation Photos",
                description = "12 items",
                type = ItemCardType.Image,
                onCallback = {},
            )
            ItemCard(
                title = "Documents",
                description = "3 items",
                type = ItemCardType.Folder,
                onCallback = {},
            )
        }
    }
}
