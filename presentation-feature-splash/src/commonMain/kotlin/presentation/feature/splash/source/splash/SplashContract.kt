package presentation.feature.splash.source.splash

public data class SplashState(
    val isLoading: Boolean = true,
)

/**
 * One-shot side effects emitted by [SplashViewModel].
 */
public sealed class SplashSideEffect {

    /**
     * Navigate to the onboarding flow (first launch).
     */
    public data object NavigateToOnboarding : SplashSideEffect()

    /**
     * Navigate to the connection screen (onboarding already completed, skip to device setup).
     */
    public data object NavigateToConnection : SplashSideEffect()

    /**
     * Navigate directly to the home screen, deferring device connection until upload time.
     *
     * Used when the app is launched via a share intent so the user can download
     * content over regular internet before being asked to connect to the e-reader AP.
     */
    public data object NavigateToHome : SplashSideEffect()

    /**
     * Display an error message identified by a string resource [messageId].
     */
    public data class ShowError(val messageId: Int) : SplashSideEffect()
}

public sealed class SplashIntent {
    public data object OnAnimationFinished : SplashIntent()
}
