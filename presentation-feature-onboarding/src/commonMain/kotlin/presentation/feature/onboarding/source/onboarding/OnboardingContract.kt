package presentation.feature.onboarding.source.onboarding

public data class OnboardingState(
    val isLoading: Boolean = false,
)

public sealed class OnboardingSideEffect {
    public data object NavigateToConnection : OnboardingSideEffect()
    public data class ShowError(val messageId: Int) : OnboardingSideEffect()
}

public sealed class OnboardingIntent {
    public data object OnGetStartedClick : OnboardingIntent()
}
