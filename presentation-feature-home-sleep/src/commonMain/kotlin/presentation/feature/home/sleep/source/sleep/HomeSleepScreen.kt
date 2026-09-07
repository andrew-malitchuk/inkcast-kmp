package presentation.feature.home.sleep.source.sleep

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.readBytes
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_status_delete_failed
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_status_deleted
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_status_upload_complete
import inkcast_kmp.presentation_core_localisation.generated.resources.sleep_status_upload_failed
import kotlinx.coroutines.launch
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
 * Screen-level composable for the sleep screen editor feature.
 *
 * Collects MVI state and side effects from [HomeSleepViewModel], wires up the
 * image picker launcher, and delegates rendering to [HomeSleepContent].
 *
 * @param viewModel ViewModel instance provided by Koin.
 *
 * @see HomeSleepContract
 * @see HomeSleepContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun HomeSleepScreen(viewModel: HomeSleepViewModel = koinViewModel()) {
    val appNavigator = LocalAppNavigator.current
    val state = viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    val hostState = rememberStackedSnackbarHostState(animation = StackedSnackbarAnimation.Slide)

    val uploadSuccessMsg = stringResource(Res.string.sleep_status_upload_complete)
    val uploadFailedMsg = stringResource(Res.string.sleep_status_upload_failed)
    val deleteFailedMsg = stringResource(Res.string.sleep_status_delete_failed)

    val launcher = rememberFilePickerLauncher(
        type = FileKitType.Image,
    ) { file ->
        file?.let {
            scope.launch {
                val bytes = file.readBytes()
                viewModel.handleIntent(HomeSleepIntent.ImageSelected(bytes))
            }
        }
    }

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is HomeSleepSideEffect.ShowUploadSuccess -> {
                hostState.showSnackbar(title = uploadSuccessMsg, duration = StackedSnackbarDuration.Short)
            }
            is HomeSleepSideEffect.ShowUploadError -> {
                hostState.showSnackbar(title = uploadFailedMsg, duration = StackedSnackbarDuration.Short)
            }
            is HomeSleepSideEffect.ShowDeleteSuccess -> {
                val msg = getString(Res.string.sleep_status_deleted, effect.name)
                hostState.showSnackbar(title = msg, duration = StackedSnackbarDuration.Short)
            }
            is HomeSleepSideEffect.ShowDeleteError -> {
                hostState.showSnackbar(title = deleteFailedMsg, duration = StackedSnackbarDuration.Short)
            }
            is HomeSleepSideEffect.LaunchImagePicker -> {
                launcher.launch()
            }
            is HomeSleepSideEffect.NavigateToConnection -> {
                appNavigator?.navigate(Destination.Connection(isInitialSetup = false))
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomeSleepContent(
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
