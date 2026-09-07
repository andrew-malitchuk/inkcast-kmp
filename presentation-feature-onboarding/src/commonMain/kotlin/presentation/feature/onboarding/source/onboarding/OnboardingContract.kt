package presentation.feature.onboarding.source.onboarding

/**
 * UI state for the Onboarding screen.
 *
 * @property isLoading Whether a blocking operation (e.g. persisting onboarding status) is in progress.
 * @property currentStep Current onboarding step: 0 = welcome, 1 = hotspot setup guide.
 */
public data class OnboardingState(
    val isLoading: Boolean = false,
    val currentStep: Int = 0,
)

/**
 * One-shot side-effects emitted by [OnboardingViewModel].
 */
public sealed class OnboardingSideEffect {

    /** Navigate to the Connection screen to begin initial device setup. */
    public data object NavigateToConnection : OnboardingSideEffect()

    /**
     * Display an error message to the user.
     *
     * @property messageId String resource identifier for the error message.
     */
    public data class ShowError(val messageId: Int) : OnboardingSideEffect()
}

/**
 * User-initiated actions on the Onboarding screen.
 */
public sealed class OnboardingIntent {

    /** User tapped the "Get Started" button on the welcome step. Advances to the hotspot setup step. */
    public data object OnGetStartedClick : OnboardingIntent()

    /** User tapped "Continue" on the hotspot setup step. Completes onboarding and navigates to Connection. */
    public data object OnContinueClick : OnboardingIntent()
}
