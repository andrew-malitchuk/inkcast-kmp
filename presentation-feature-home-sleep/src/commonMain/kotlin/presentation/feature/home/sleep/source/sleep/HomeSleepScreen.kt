package presentation.feature.home.sleep.source.sleep

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
public fun HomeSleepScreen(viewModel: HomeSleepViewModel = koinViewModel()) {
    val state = viewModel.collectAsState()
    val scope = rememberCoroutineScope()

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
            is HomeSleepSideEffect.ShowMessage -> {
                // TODO: show snackbar with effect.message
            }
            is HomeSleepSideEffect.ShowError -> {
                // TODO: show error snackbar with effect.message
            }
            is HomeSleepSideEffect.LaunchImagePicker -> {
                launcher.launch()
            }
        }
    }

    HomeSleepContent(
        state = state.value,
        onIntent = viewModel::handleIntent,
    )
}
