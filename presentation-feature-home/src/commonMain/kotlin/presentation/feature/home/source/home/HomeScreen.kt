package presentation.feature.home.source.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.core.composition.LocalSharedUrl
import presentation.core.navigation.api.source.destination.Destination
import presentation.feature.home.core.HomeTab

@Composable
public fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val sharedUrlState = LocalSharedUrl.current

    LaunchedEffect(sharedUrlState.url) {
        val url = sharedUrlState.consume()
        if (url != null) {
            viewModel.handleIntent(HomeIntent.OnTabSelected(HomeTab.CREATE))
        }
    }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            HomeSideEffect.NavigateToSettings -> appNavigator?.navigate(Destination.Settings)
            is HomeSideEffect.ShowError -> {
                // TODO: show snackbar with effect.messageId
            }
        }
    }

    HomeContent(
        state = state.value,
        onIntent = viewModel::handleIntent,
    )
}
