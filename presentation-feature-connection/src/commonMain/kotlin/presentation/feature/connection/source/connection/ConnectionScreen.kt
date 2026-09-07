package presentation.feature.connection.source.connection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_enter_ip
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_invalid_ip
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_network_error
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_no_devices_found
import inkcast_kmp.presentation_core_localisation.generated.resources.connection_hotspot_hint
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.source.destination.AppNavigator
import presentation.core.navigation.api.source.destination.Destination
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarAnimation
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarDuration
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarHost
import presentation.core.ui.source.kit.atom.snackbar.rememberStackedSnackbarHostState

/**
 * Entry-point composable for the device connection screen.
 *
 * Injects [ConnectionViewModel] via Koin, wires state observation and side-effect
 * handling, and delegates rendering to [ConnectionContent].
 *
 * @param isInitialSetup When `true`, navigates to Home on successful connection
 *   instead of popping back. Used during the onboarding flow.
 * @param viewModel ViewModel instance provided by Koin DI.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun ConnectionScreen(
    isInitialSetup: Boolean = false,
    viewModel: ConnectionViewModel = koinViewModel(),
) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val hostState = rememberStackedSnackbarHostState(animation = StackedSnackbarAnimation.Slide)

    val noDevicesFoundMsg = stringResource(Res.string.connection_no_devices_found)
    val networkErrorMsg = stringResource(Res.string.connection_network_error)
    val hotspotHintMsg = stringResource(Res.string.connection_hotspot_hint)
    val invalidIpMsg = stringResource(Res.string.connection_invalid_ip)
    val enterIpMsg = stringResource(Res.string.connection_enter_ip)

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is ConnectionSideEffect.NavigateBack -> {
                if (isInitialSetup) {
                    appNavigator?.navigate(
                        Destination.Home,
                        AppNavigator.NavOptions.ClearTask,
                    )
                } else {
                    appNavigator?.popBackStack()
                }
            }
            is ConnectionSideEffect.ShowError -> {
                val message = when (effect.errorType) {
                    ConnectionErrorType.NoDevicesFound -> noDevicesFoundMsg
                    ConnectionErrorType.NetworkError -> networkErrorMsg
                    ConnectionErrorType.DeviceNotReachable -> hotspotHintMsg
                    ConnectionErrorType.InvalidIp -> invalidIpMsg
                    ConnectionErrorType.EmptyIp -> enterIpMsg
                }
                hostState.showSnackbar(
                    title = message,
                    duration = StackedSnackbarDuration.Short,
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ConnectionContent(
            state = state.value,
            isInitialSetup = isInitialSetup,
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
