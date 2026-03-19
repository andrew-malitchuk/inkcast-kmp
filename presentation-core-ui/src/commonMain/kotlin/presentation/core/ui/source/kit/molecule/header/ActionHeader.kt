package presentation.core.ui.source.kit.molecule.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.core.styling.core.Theme
import presentation.core.styling.source.theme.AppTheme
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.IconButton
import presentation.core.ui.source.kit.atom.icon.Plus

/**
 * A header row identical to [SimpleHeader] but with an additional [IconButton]
 * on the trailing side.
 *
 * The action button is rendered via the project's [IconButton] atom, which respects
 * the design-system color tokens and interaction states (pressed, disabled, selected).
 *
 * @param title The header text displayed on the leading side.
 * @param actionIcon [ImageVector] rendered inside the trailing [IconButton].
 * @param onActionClick Called when the user taps the action button.
 * @param modifier [Modifier] applied to the root [Row].
 * @param buttonSize Size variant for the [IconButton]. Defaults to [ButtonSizeType.Small].
 *
 * @see SimpleHeader
 * @see IconButton
 */
@Composable
public fun ActionHeader(
    title: String,
    actionIcon: ImageVector,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: ButtonSizeType = ButtonSizeType.Small,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Theme.spacing.spacingL,
                vertical = Theme.spacing.spacingM,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = Theme.typography.title,
            color = Theme.color.inkMain,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            icon = actionIcon,
            onClick = onActionClick,
            size = buttonSize,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionHeaderPreview() {
    AppTheme {
        ActionHeader(
            title = "File Manager",
            actionIcon = Plus,
            onActionClick = {},
        )
    }
}
