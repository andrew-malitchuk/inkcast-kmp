package presentation.feature.splash.source.splash

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.configuration.GetOnboardingStatusUseCase
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

private const val SPLASH_DELAY_MS = 2000L

/**
 * ViewModel for the splash screen.
 *
 * Handles the initial app loading animation and determines the next destination
 * based on the onboarding completion status.
 *
 * @param getOnboardingStatusUseCase Use case that checks whether the user has completed onboarding.
 */
@OrbitExperimental
public class SplashViewModel(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
) : ContainerHost<SplashState, SplashSideEffect>, ViewModel() {

    override val container: Container<SplashState, SplashSideEffect> =
        container<SplashState, SplashSideEffect>(SplashState()) {
            onAnimationFinished()
        }

    /**
     * Dispatches the given [intent] to the appropriate handler.
     */
    public fun handleIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.OnAnimationFinished -> onAnimationFinished()
        }
    }

    private fun onAnimationFinished() = intent {
        delay(SPLASH_DELAY_MS)
        reduce { state.copy(isLoading = false) }

        val isOnboardingCompleted = getOnboardingStatusUseCase().getOrDefault(false)

        if (isOnboardingCompleted) {
            postSideEffect(SplashSideEffect.NavigateToConnection)
        } else {
            postSideEffect(SplashSideEffect.NavigateToOnboarding)
        }
    }
}
