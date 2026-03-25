package presentation.feature.about.source.about

public data class AboutState(
    val isLoading: Boolean = false,
)

public sealed class AboutSideEffect {
    public data object GoBackEffect : AboutSideEffect()
    public data class ShowError(val messageId: Int) : AboutSideEffect()
}

public sealed class AboutIntent {
    public data object OnBackClick : AboutIntent()
}
