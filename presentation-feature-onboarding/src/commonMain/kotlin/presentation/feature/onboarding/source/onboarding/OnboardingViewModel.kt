package presentation.feature.onboarding.source.onboarding

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.configuration.SetOnboardingStatusUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * ViewModel for the Onboarding screen.
 *
 * Follows the MVI pattern via Orbit: UI dispatches [OnboardingIntent] actions
 * through [handleIntent], the ViewModel reduces [OnboardingState], and emits
 * [OnboardingSideEffect] for one-shot events such as navigation.
 *
 * Marks onboarding as completed when the user proceeds, ensuring
 * subsequent app launches skip directly to the home screen.
 *
 * @param setOnboardingStatusUseCase Use case that persists the onboarding completion flag.
 *
 * @see OnboardingContract
 * @see OnboardingScreen
 */
@OrbitExperimental
public class OnboardingViewModel(
    private val setOnboardingStatusUseCase: SetOnboardingStatusUseCase,
) : ContainerHost<OnboardingState, OnboardingSideEffect>, ViewModel() {

    override val container: Container<OnboardingState, OnboardingSideEffect> =
        container<OnboardingState, OnboardingSideEffect>(OnboardingState())

    /**
     * Dispatches the given [intent] to the appropriate handler.
     */
    public fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            OnboardingIntent.OnGetStartedClick -> onGetStartedClick()
        }
    }

    /**
     * Persists the onboarding-completed flag and navigates to the Connection screen.
     */
    private fun onGetStartedClick() = intent {
        setOnboardingStatusUseCase(true)
        postSideEffect(OnboardingSideEffect.NavigateToConnection)
    }
}
