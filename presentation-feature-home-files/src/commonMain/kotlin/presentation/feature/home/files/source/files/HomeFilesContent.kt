package presentation.feature.home.files.source.files

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.files_title
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.icon.Plus
import presentation.core.ui.source.kit.molecule.header.ActionHeader

/**
 * Main content composable for the file manager screen.
 *
 * Renders the screen header with an add action and crossfades between
 * [HomeFilesShimmerContent] (loading) and [HomeFilesSuccessContent] (loaded)
 * based on the current state.
 *
 * @param state Current UI state of the file manager screen.
 * @param onIntent Callback to dispatch user intents to the ViewModel.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeFilesContent(
    state: HomeFilesState,
    onIntent: (HomeFilesIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .statusBarsPadding(),
    ) {
        ActionHeader(
            title = stringResource(Res.string.files_title),
            actionIcon = Plus,
            onActionClick = { onIntent(HomeFilesIntent.OnAddClick) },
        )
        Crossfade(
            targetState = state.isLoading,
            modifier = Modifier.fillMaxSize(),
        ) { loading ->
            if (loading) {
                HomeFilesShimmerContent()
            } else {
                HomeFilesSuccessContent(
                    state = state,
                    onIntent = onIntent,
                )
            }
        }
    }
}
