package presentation.feature.onboarding.source.onboarding

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.source.destination.AppNavigator
import presentation.core.navigation.api.source.destination.Destination

@Composable
public fun OnboardingScreen(viewModel: OnboardingViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()

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
