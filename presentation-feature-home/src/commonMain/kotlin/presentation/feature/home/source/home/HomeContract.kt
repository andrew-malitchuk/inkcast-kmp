package presentation.feature.home.source.home

import presentation.feature.home.core.HomeTab

/**
 * Immutable UI state for the Home screen tab container.
 *
 * @property isLoading Whether the home screen is in a loading state.
 * @property selectedTab The currently active [HomeTab].
 * @property tabResetTrigger Monotonically increasing counter that is bumped when
 *   the user re-selects the already-active tab, signalling child tabs to
 *   scroll-to-top or reset their state.
 *
 * @see HomeViewModel
 */
public data class HomeState(
    val isLoading: Boolean = false,
    val selectedTab: HomeTab = HomeTab.FILES,
    val tabResetTrigger: Int = 0,
)

/**
 * One-shot side effects emitted by [HomeViewModel].
 *
 * Each effect is consumed exactly once by [HomeScreen] to trigger
 * navigation or transient UI feedback.
 *
 * @see HomeScreen
 */
public sealed class HomeSideEffect {
    /** Navigate to the Settings screen. */
    public data object NavigateToSettings : HomeSideEffect()

    /**
     * Display an error snackbar.
     *
     * @property messageId String resource identifier for the error message.
     */
    public data class ShowError(val messageId: Int) : HomeSideEffect()
}

/**
 * User intents dispatched from the Home UI layer.
 *
 * @see HomeViewModel.handleIntent
 */
public sealed class HomeIntent {
    /** The user tapped the settings icon in the bottom bar. */
    public data object OnSettingsClick : HomeIntent()

    /**
     * The user selected a tab in the bottom navigation bar.
     *
     * @property tab The [HomeTab] that was tapped.
     */
    public data class OnTabSelected(val tab: HomeTab) : HomeIntent()
}
