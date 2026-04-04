package presentation.feature.onboarding.source.onboarding

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.source.destination.AppNavigator
import presentation.core.navigation.api.source.destination.Destination

/**
 * Entry-point composable for the Onboarding feature.
 *
 * Collects Orbit MVI state and side-effects from [OnboardingViewModel],
 * delegates rendering to [OnboardingContent], and handles navigation
 * side-effects via [LocalAppNavigator].
 *
 * @param viewModel Koin-provided [OnboardingViewModel] instance.
 *
 * @see OnboardingContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun OnboardingScreen(viewModel: OnboardingViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()

    // Handle one-shot side-effects: navigation and error display
    viewModel.collectSideEffect { effect ->
        when (effect) {
            OnboardingSideEffect.NavigateToConnection -> appNavigator?.navigate(
                Destination.Connection(isInitialSetup = true),
                AppNavigator.NavOptions.ClearTask,
            )
            is OnboardingSideEffect.ShowError -> {
                // TODO: show snackbar with effect.messageId
            }
        }
    }

    OnboardingContent(
        state = state.value,
        onIntent = viewModel::handleIntent,
    )
}
