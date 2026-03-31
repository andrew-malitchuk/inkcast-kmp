package presentation.feature.settings.source.settings

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.settings_title
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.molecule.header.NavigationHeader

/**
 * Root content composable for the Settings screen.
 *
 * Renders a [NavigationHeader] with a back button, then cross-fades between
 * [SettingsShimmerContent] (loading) and [SettingsSuccessContent] (loaded)
 * based on [SettingsState.isLoading].
 *
 * @param state Current [SettingsState] driving the UI.
 * @param onIntent Callback to dispatch [SettingsIntent] actions to the ViewModel.
 *
 * @see SettingsSuccessContent
 * @see SettingsShimmerContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun SettingsContent(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .statusBarsPadding(),
    ) {
        NavigationHeader(
            title = stringResource(Res.string.settings_title),
            onNavigationClick = { onIntent(SettingsIntent.OnBackClick) },
        )
        // Cross-fade between shimmer placeholder and actual settings controls
        Crossfade(
            targetState = state.isLoading,
            modifier = Modifier.fillMaxSize(),
        ) { loading ->
            if (loading) {
                SettingsShimmerContent()
            } else {
                SettingsSuccessContent(
                    state = state,
                    onIntent = onIntent,
                )
            }
        }
    }
}
