package presentation.feature.connection.source.connection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_title
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.molecule.header.NavigationHeader
import presentation.core.ui.source.kit.molecule.header.SimpleHeader

@Composable
internal fun ConnectionContent(
    state: ConnectionState,
    isInitialSetup: Boolean = false,
    onIntent: (ConnectionIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .statusBarsPadding(),
    ) {
        if (isInitialSetup) {
            SimpleHeader(title = stringResource(Res.string.connection_title))
        } else {
            NavigationHeader(
                title = stringResource(Res.string.connection_title),
                onNavigationClick = { onIntent(ConnectionIntent.OnBackClick) },
            )
        }
        ConnectionSuccessContent(
            state = state,
            isInitialSetup = isInitialSetup,
            onIntent = onIntent,
        )
    }
}
