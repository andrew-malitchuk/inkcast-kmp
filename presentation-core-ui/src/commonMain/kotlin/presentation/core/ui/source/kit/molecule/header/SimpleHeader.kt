package presentation.core.ui.source.kit.molecule.header

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.core.styling.core.Theme

@Composable
public fun SimpleHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = Theme.typography.title,
        color = Theme.color.inkMain,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Theme.spacing.spacingL,
                vertical = Theme.spacing.spacingM,
            ),
    )
}
