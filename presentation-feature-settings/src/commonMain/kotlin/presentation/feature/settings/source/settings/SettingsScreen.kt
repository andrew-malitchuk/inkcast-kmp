package presentation.feature.settings.source.settings

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator

/**
 * Entry-point composable for the Settings feature.
 *
 * Collects Orbit MVI state and side-effects from [SettingsViewModel],
 * delegates rendering to [SettingsContent], and handles navigation
 * side-effects via [LocalAppNavigator].
 *
 * @param viewModel Koin-provided [SettingsViewModel] instance.
 *
 * @see SettingsContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()

    viewModel.collectSideEffect { effect ->
        when (effect) {
            SettingsSideEffect.GoBackEffect -> appNavigator?.backAction()
            is SettingsSideEffect.ShowError -> {
                // TODO: show snackbar with effect.messageId
            }
        }
    }

    SettingsContent(
        state = state.value,
        onIntent = viewModel::handleIntent,
    )
}
