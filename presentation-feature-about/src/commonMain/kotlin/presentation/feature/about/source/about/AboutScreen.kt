package presentation.feature.about.source.about

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator

/**
 * Entry-point composable for the About feature screen.
 *
 * Injects [AboutViewModel] via Koin, collects Orbit MVI state and side effects,
 * then delegates rendering to [AboutContent].
 *
 * @param viewModel ViewModel driving the screen state via Orbit MVI.
 *
 * @see AboutContent
 * @see AboutContract
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun AboutScreen(viewModel: AboutViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()

    viewModel.collectSideEffect { effect ->
        when (effect) {
            AboutSideEffect.GoBackEffect -> appNavigator?.backAction()
            is AboutSideEffect.ShowError -> {
                // TODO: show snackbar with effect.messageId
            }
        }
    }

    AboutContent(
        state = state.value,
        onIntent = viewModel::handleIntent,
    )
}
