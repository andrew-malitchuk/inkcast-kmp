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

/**
 * Entry-point composable for the Home screen.
 *
 * Injects [HomeViewModel] via Koin, observes MVI state and side effects,
 * and delegates rendering to [HomeContent]. When a shared URL is detected
 * (e.g. from the Android share sheet), the Create tab is auto-selected so
 * the user lands directly on EPUB creation.
 *
 * @param viewModel ViewModel instance provided by Koin DI.
 *
 * @see HomeContent
 * @see HomeViewModel
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val sharedUrlState = LocalSharedUrl.current

    // Auto-switch to the Create tab when a shared URL arrives.
    LaunchedEffect(sharedUrlState.url) {
        val url = sharedUrlState.consume()
        if (url != null) {
            viewModel.handleIntent(HomeIntent.OnTabSelected(HomeTab.CREATE))
        }
    }

    // Handle one-shot navigation side effects.
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
