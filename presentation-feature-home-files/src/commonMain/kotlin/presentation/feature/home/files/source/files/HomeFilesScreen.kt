package presentation.feature.home.files.source.files

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarAnimation
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarDuration
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarHost
import presentation.core.ui.source.kit.atom.snackbar.rememberStackedSnackbarHostState

/**
 * Screen-level composable for the file manager feature.
 *
 * Collects MVI state and side effects from [HomeFilesViewModel], wires up the
 * file picker launcher for EPUB uploads, and displays snackbar notifications.
 * Delegates rendering to [HomeFilesContent].
 *
 * @param viewModel ViewModel instance provided by Koin.
 *
 * @see HomeFilesContract
 * @see HomeFilesContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun HomeFilesScreen(viewModel: HomeFilesViewModel = koinViewModel()) {
    val state = viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    val hostState = rememberStackedSnackbarHostState(animation = StackedSnackbarAnimation.Slide)

    val launcher = rememberFilePickerLauncher(
        type = FileKitType.File(extensions = listOf("epub")),
    ) { file ->
        file?.let {
            scope.launch {
                val fileBytes = file.readBytes()
                val fileName = file.name
                viewModel.handleIntent(HomeFilesIntent.FileSelected(fileName, fileBytes))
            }
        }
    }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is HomeFilesSideEffect.ShowMessage -> {
                hostState.showSnackbar(
                    title = effect.message,
                    duration = StackedSnackbarDuration.Short,
                )
            }
            is HomeFilesSideEffect.ShowError -> {
                hostState.showSnackbar(
                    title = effect.message,
                    duration = StackedSnackbarDuration.Short,
                )
            }
            is HomeFilesSideEffect.LaunchFilePicker -> {
                launcher.launch()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeFilesContent(
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
