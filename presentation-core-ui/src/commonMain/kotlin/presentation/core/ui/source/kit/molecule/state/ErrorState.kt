package presentation.core.ui.source.kit.molecule.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.icon.AlertTriangle

@Composable
public fun ErrorState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = AlertTriangle,
            contentDescription = null,
            tint = Theme.color.error,
            modifier = Modifier.size(64.dp),
        )
        Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
        Text(
            text = title,
            style = Theme.typography.title,
            color = Theme.color.inkMain,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(Theme.spacing.spacingS))
        Text(
            text = description,
            style = Theme.typography.body,
            color = Theme.color.inkSubtle,
            textAlign = TextAlign.Center,
        )
    }
}
