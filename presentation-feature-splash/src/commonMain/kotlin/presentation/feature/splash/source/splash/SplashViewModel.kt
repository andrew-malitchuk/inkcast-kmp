package presentation.feature.splash.source.splash

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.configuration.GetOnboardingStatusUseCase
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/** Minimum time the splash screen is displayed before navigating. */
private const val SPLASH_DELAY_MS = 2000L

/**
 * ViewModel for the Splash screen.
 *
 * Follows the MVI pattern via Orbit: UI dispatches [SplashIntent] actions
 * through [handleIntent], the ViewModel reduces [SplashState], and emits
 * [SplashSideEffect] for one-shot navigation events.
 *
 * Handles the initial app loading animation and determines the next destination
 * based on the onboarding completion status.
 *
 * @param getOnboardingStatusUseCase Use case that checks whether the user has completed onboarding.
 *
 * @see SplashContract
 * @see SplashScreen
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

    /**
     * Waits for the splash delay, then checks onboarding status and posts
     * the appropriate navigation side-effect.
     */
    private fun onAnimationFinished() = intent {
        delay(SPLASH_DELAY_MS)
        reduce { state.copy(isLoading = false) }

        // Determine navigation destination based on onboarding completion
        val isOnboardingCompleted = getOnboardingStatusUseCase().getOrDefault(false)

        if (isOnboardingCompleted) {
            postSideEffect(SplashSideEffect.NavigateToConnection)
        } else {
            postSideEffect(SplashSideEffect.NavigateToOnboarding)
        }
    }
}
