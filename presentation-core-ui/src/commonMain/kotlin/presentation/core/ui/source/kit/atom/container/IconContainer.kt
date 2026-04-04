package presentation.core.ui.source.kit.atom.container

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.styling.source.theme.AppTheme
import presentation.core.ui.source.kit.atom.shape.SquircleShape

/**
 * A squircle-shaped container that displays a tinted icon on a colored background.
 *
 * @param modifier Modifier applied to the outer [Box].
 * @param icon The vector icon to render.
 * @param backgroundColor Fill color of the squircle container.
 * @param foregroundColor Tint applied to the icon.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun IconContainer(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    backgroundColor: Color,
    foregroundColor: Color,
) {
    Box(
        modifier =
        modifier
            .clip(SquircleShape(Theme.spacing.spacingM))
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(foregroundColor),
        )
    }
}
