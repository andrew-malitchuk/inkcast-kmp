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
