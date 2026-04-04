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

/**
 * Layout shell for the Connection screen providing header and content structure.
 *
 * Shows a [SimpleHeader] during onboarding flow or a [NavigationHeader] with
 * back button when accessed from settings.
 *
 * @param state Current UI state driving the content.
 * @param isInitialSetup When `true`, renders a non-navigable header for onboarding.
 * @param onIntent Callback to dispatch user intents to the ViewModel.
 *
 * @see ConnectionSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
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
