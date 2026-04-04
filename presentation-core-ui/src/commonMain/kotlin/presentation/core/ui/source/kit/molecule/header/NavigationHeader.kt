package presentation.core.ui.source.kit.molecule.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.core.styling.core.Theme
import presentation.core.styling.source.theme.AppTheme
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.IconButton
import presentation.core.ui.source.kit.atom.icon.ChevronLeft

/**
 * A header row with a leading back-navigation [IconButton] and a title.
 *
 * Tapping the back button invokes [onNavigationClick], typically used to
 * pop the current destination from the navigation back stack.
 *
 * @param title The header text displayed next to the back button.
 * @param onNavigationClick Called when the user taps the back button.
 * @param modifier [Modifier] applied to the root [Row].
 * @param buttonSize Size variant for the back [IconButton]. Defaults to [ButtonSizeType.Medium].
 *
 * @see ActionHeader
 * @see IconButton
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun NavigationHeader(
    title: String,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: ButtonSizeType = ButtonSizeType.Medium,
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
        IconButton(
            icon = ChevronLeft,
            onClick = onNavigationClick,
            size = buttonSize,
        )
        Spacer(modifier = Modifier.width(Theme.spacing.spacingM))
        Text(
            text = title,
            style = Theme.typography.title,
            color = Theme.color.inkMain,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationHeaderPreview() {
    AppTheme {
        NavigationHeader(
            title = "Connection",
            onNavigationClick = {},
        )
    }
}
