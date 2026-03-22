package presentation.feature.home.source.home

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container
import presentation.feature.home.core.HomeTab

@OrbitExperimental
public class HomeViewModel(
    // inject use cases here
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container: Container<HomeState, HomeSideEffect> =
        container<HomeState, HomeSideEffect>(HomeState())

    public fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnSettingsClick -> onSettingsClick()
            is HomeIntent.OnTabSelected -> onTabSelected(intent.tab)
        }
    }

    private fun onSettingsClick() = intent {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }

    private fun onTabSelected(tab: HomeTab) = intent {
        if (state.selectedTab == tab) {
            reduce { state.copy(tabResetTrigger = state.tabResetTrigger + 1) }
        } else {
            reduce { state.copy(selectedTab = tab) }
        }
    }
}
