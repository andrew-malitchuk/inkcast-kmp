package presentation.feature.home.create.source.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.create_upload_success
import inkcast_kmp.presentation_core_localisation.generated.resources.error_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.core.composition.LocalSharedUrl
import presentation.core.navigation.api.source.destination.Destination
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarAnimation
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarDuration
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarHost
import presentation.core.ui.source.kit.atom.snackbar.rememberStackedSnackbarHostState

/**
 * Entry-point composable for the Create EPUB screen.
 *
 * Injects [HomeCreateViewModel] via Koin, wires state observation and side-effect
 * handling, and delegates rendering to [HomeCreateContent].
 *
 * When a shared URL is present (e.g. from Android share sheet), it is consumed
 * once and the URL field is pre-filled, then EPUB creation is triggered automatically.
 *
 * @param viewModel ViewModel instance provided by Koin DI.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun HomeCreateScreen(viewModel: HomeCreateViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val sharedUrlState = LocalSharedUrl.current
    val hostState = rememberStackedSnackbarHostState(animation = StackedSnackbarAnimation.Slide)
    val errorTitle = stringResource(Res.string.error_title)
    val successTitle = stringResource(Res.string.create_upload_success)

    LaunchedEffect(Unit) {
        sharedUrlState.consume()?.let { url ->
            viewModel.handleIntent(HomeCreateIntent.SelectMode(0))
            viewModel.handleIntent(HomeCreateIntent.UpdateUrl(url))
            viewModel.handleIntent(HomeCreateIntent.CreateEpub)
        }
    }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is HomeCreateSideEffect.ShowError -> {
                hostState.showSnackbar(
                    title = errorTitle,
                    duration = StackedSnackbarDuration.Short,
                )
            }
            is HomeCreateSideEffect.ShowUploadSuccess -> {
                hostState.showSnackbar(
                    title = successTitle,
                    duration = StackedSnackbarDuration.Short,
                )
            }
            is HomeCreateSideEffect.NavigateToConnection -> {
                appNavigator?.navigate(Destination.Connection())
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeCreateContent(
            state = state.value,
            onIntent = viewModel::handleIntent,
        )
        StackedSnackbarHost(
            hostState = hostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding(),
        )
    }
}
