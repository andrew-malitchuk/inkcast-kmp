package presentation.feature.home.source.home

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container
import presentation.feature.home.core.HomeTab

/**
 * ViewModel for the Home screen tab container.
 *
 * Follows the MVI pattern via Orbit: UI dispatches [HomeIntent] actions
 * through [handleIntent], the ViewModel reduces them into [HomeState]
 * updates, and emits [HomeSideEffect] for one-shot navigation events.
 *
 * Re-selecting the currently active tab increments
 * [HomeState.tabResetTrigger] so child tabs can scroll to top or reset.
 *
 * @see HomeScreen
 * @see HomeState
 * @see HomeSideEffect
 */
@OrbitExperimental
public class HomeViewModel(
    // inject use cases here
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container: Container<HomeState, HomeSideEffect> =
        container<HomeState, HomeSideEffect>(HomeState())

    /**
     * Dispatches the given [intent] to the appropriate handler.
     *
     * @param intent User action from the UI layer.
     */
    public fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnSettingsClick -> onSettingsClick()
            is HomeIntent.OnTabSelected -> onTabSelected(intent.tab)
        }
    }

    /** Emits a side effect to navigate to the Settings screen. */
    private fun onSettingsClick() = intent {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }

    /**
     * Selects the given [tab]. If the tab is already selected, bumps
     * [HomeState.tabResetTrigger] to signal the child tab to reset.
     */
    private fun onTabSelected(tab: HomeTab) = intent {
        if (state.selectedTab == tab) {
            reduce { state.copy(tabResetTrigger = state.tabResetTrigger + 1) }
        } else {
            reduce { state.copy(selectedTab = tab) }
        }
    }
}
