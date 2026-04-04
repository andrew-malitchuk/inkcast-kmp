package presentation.core.ui.source.kit.molecule.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme

/**
 * Displays a single row in a settings section with an icon, label/subtitle, and trailing content.
 *
 * @param label Primary text label.
 * @param modifier Modifier to be applied to the root [Row].
 * @param subtitle Optional secondary description text.
 * @param icon Optional leading icon.
 * @param onClick Optional click callback. When null, the row is not clickable.
 * @param trailing Trailing slot for toggle, chevron, value text, etc.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun SettingRow(
    label: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClick,
                    )
                } else {
                    Modifier
                },
            )
            .padding(
                horizontal = Theme.spacing.spacingL,
                vertical = Theme.spacing.spacingM,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            if (icon != null) {
                Image(
                    imageVector = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Theme.color.inkSubtle),
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(Theme.spacing.spacingM))
            }
            Column {
                Text(
                    text = label,
                    style = Theme.typography.body,
                    color = Theme.color.inkMain,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                    )
                }
            }
        }
        if (trailing != null) {
            Spacer(modifier = Modifier.width(Theme.spacing.spacingS))
            trailing()
        }
    }
}
