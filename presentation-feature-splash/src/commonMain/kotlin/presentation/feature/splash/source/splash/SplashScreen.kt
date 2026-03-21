package presentation.feature.splash.source.splash

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.core.composition.LocalSharedUrl
import presentation.core.navigation.api.source.destination.AppNavigator
import presentation.core.navigation.api.source.destination.Destination

/**
 * Entry-point composable for the splash screen.
 *
 * Observes [SplashViewModel] side effects to determine the initial navigation
 * destination. When a shared URL is present (e.g. from the Android share sheet),
 * the Connection screen is skipped and the user is sent directly to Home so
 * the EPUB can be downloaded over the current internet connection.
 *
 * @param viewModel ViewModel instance provided by Koin DI.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun SplashScreen(viewModel: SplashViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val sharedUrlState = LocalSharedUrl.current
    val state = viewModel.collectAsState()

    viewModel.collectSideEffect { effect ->
        when (effect) {
            SplashSideEffect.NavigateToOnboarding -> appNavigator?.navigate(
                Destination.Onboarding,
                AppNavigator.NavOptions.ClearTask,
            )
            SplashSideEffect.NavigateToConnection -> {
                // NOTE: When the app was launched via a share intent, the user
                // needs internet to download the article first. Sending them to
                // Connection (which requires ESP32 AP with no internet) would
                // block the flow. Skip to Home; the Create tab will prompt for
                // device setup at upload time.
                val hasSharedUrl = sharedUrlState.url != null
                if (hasSharedUrl) {
                    appNavigator?.navigate(
                        Destination.Home,
                        AppNavigator.NavOptions.ClearTask,
                    )
                } else {
                    appNavigator?.navigate(
                        Destination.Connection(isInitialSetup = true),
                        AppNavigator.NavOptions.ClearTask,
                    )
                }
            }
            SplashSideEffect.NavigateToHome -> appNavigator?.navigate(
                Destination.Home,
                AppNavigator.NavOptions.ClearTask,
            )
            is SplashSideEffect.ShowError -> {
                // TODO: show snackbar with effect.messageId
            }
        }
    }

    SplashContent(
        state = state.value,
        onIntent = viewModel::handleIntent,
    )
}
