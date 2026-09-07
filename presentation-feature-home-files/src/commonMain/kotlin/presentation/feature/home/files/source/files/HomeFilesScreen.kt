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
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_create_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_created
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_delete_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_deleted
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_folders_no_rename
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_load_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_move_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_moved
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_rename_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_renamed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_upload_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.files_status_uploaded
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.source.destination.Destination
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
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    val hostState = rememberStackedSnackbarHostState(animation = StackedSnackbarAnimation.Slide)

    val loadFailedMsg = stringResource(Res.string.files_status_load_failed)
    val foldersNoRenameMsg = stringResource(Res.string.files_status_folders_no_rename)
    val createFailedMsg = stringResource(Res.string.files_status_create_failed)
    val deleteFailedMsg = stringResource(Res.string.files_status_delete_failed)
    val uploadFailedMsg = stringResource(Res.string.files_status_upload_failed)
    val renameFailedMsg = stringResource(Res.string.files_status_rename_failed)
    val moveFailedMsg = stringResource(Res.string.files_status_move_failed)

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
                val text = when (val msg = effect.message) {
                    is HomeFilesStatusMessage.Created -> getString(Res.string.files_status_created, msg.name)
                    is HomeFilesStatusMessage.Deleted -> getString(Res.string.files_status_deleted, msg.name)
                    is HomeFilesStatusMessage.Uploaded -> getString(Res.string.files_status_uploaded, msg.name)
                    is HomeFilesStatusMessage.Renamed -> getString(Res.string.files_status_renamed, msg.name)
                    is HomeFilesStatusMessage.Moved -> getString(Res.string.files_status_moved, msg.name)
                    else -> ""
                }
                if (text.isNotEmpty()) {
                    hostState.showSnackbar(title = text, duration = StackedSnackbarDuration.Short)
                }
            }
            is HomeFilesSideEffect.ShowError -> {
                val text = when (effect.message) {
                    HomeFilesStatusMessage.LoadFailed -> loadFailedMsg
                    HomeFilesStatusMessage.FoldersNoRename -> foldersNoRenameMsg
                    HomeFilesStatusMessage.CreateFailed -> createFailedMsg
                    HomeFilesStatusMessage.DeleteFailed -> deleteFailedMsg
                    HomeFilesStatusMessage.UploadFailed -> uploadFailedMsg
                    HomeFilesStatusMessage.RenameFailed -> renameFailedMsg
                    HomeFilesStatusMessage.MoveFailed -> moveFailedMsg
                    else -> ""
                }
                if (text.isNotEmpty()) {
                    hostState.showSnackbar(title = text, duration = StackedSnackbarDuration.Short)
                }
            }
            is HomeFilesSideEffect.LaunchFilePicker -> {
                launcher.launch()
            }
            is HomeFilesSideEffect.NavigateToConnection -> {
                appNavigator?.navigate(Destination.Connection(isInitialSetup = false))
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
