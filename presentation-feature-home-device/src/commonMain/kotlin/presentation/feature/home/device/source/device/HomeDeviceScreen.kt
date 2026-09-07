package presentation.feature.home.device.source.device

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.device_cache_cleared
import inkcast_kmp.presentation_core_localisation.generated.resources.device_cache_clear_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.error_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.source.destination.Destination
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarAnimation
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarDuration
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarHost
import presentation.core.ui.source.kit.atom.snackbar.rememberStackedSnackbarHostState

/**
 * Entry-point composable for the device settings screen.
 *
 * Injects [HomeDeviceViewModel] via Koin, wires state observation and side-effect
 * handling, and delegates rendering to [HomeDeviceContent].
 *
 * @param viewModel ViewModel instance provided by Koin DI.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun HomeDeviceScreen(viewModel: HomeDeviceViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val hostState = rememberStackedSnackbarHostState(animation = StackedSnackbarAnimation.Slide)
    val errorTitle = stringResource(Res.string.error_title)
    val cacheClearedMsg = stringResource(Res.string.device_cache_cleared)
    val cacheClearFailedMsg = stringResource(Res.string.device_cache_clear_failed)

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is HomeDeviceSideEffect.ShowError -> {
                hostState.showSnackbar(
                    title = errorTitle,
                    duration = StackedSnackbarDuration.Short,
                )
            }
            is HomeDeviceSideEffect.NavigateToConnection -> {
                appNavigator?.navigate(Destination.Connection())
            }
            is HomeDeviceSideEffect.ShowCacheCleared -> {
                hostState.showSnackbar(title = cacheClearedMsg, duration = StackedSnackbarDuration.Short)
            }
            is HomeDeviceSideEffect.ShowCacheClearFailed -> {
                hostState.showSnackbar(title = cacheClearFailedMsg, duration = StackedSnackbarDuration.Short)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeDeviceContent(
            state = state.value,
            onIntent = viewModel::handleIntent,
        )
        StackedSnackbarHost(
            hostState = hostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding(),
        )
    }
}
