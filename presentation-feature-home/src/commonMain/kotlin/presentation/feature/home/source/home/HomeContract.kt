package presentation.feature.home.source.home

import presentation.feature.home.core.HomeTab

public data class HomeState(
    val isLoading: Boolean = false,
    val selectedTab: HomeTab = HomeTab.FILES,
    val tabResetTrigger: Int = 0,
)

public sealed class HomeSideEffect {
    public data object NavigateToSettings : HomeSideEffect()
    public data class ShowError(val messageId: Int) : HomeSideEffect()
}

public sealed class HomeIntent {
    public data object OnSettingsClick : HomeIntent()
    public data class OnTabSelected(val tab: HomeTab) : HomeIntent()
}
