package presentation.feature.about.source.about

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator

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
