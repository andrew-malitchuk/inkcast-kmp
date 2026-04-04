package presentation.feature.home.create.source.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.create_title
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.molecule.header.SimpleHeader

/**
 * Root content composable for the Create EPUB screen.
 *
 * Renders the screen header and delegates the body to
 * [HomeCreateSuccessContent]. A shimmer or error branch can be added
 * here in the future based on [HomeCreateState.isLoading].
 *
 * @param state Current immutable UI state snapshot from [HomeCreateViewModel].
 * @param onIntent Callback that forwards user intents to the ViewModel.
 *
 * @see HomeCreateSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeCreateContent(
    state: HomeCreateState,
    onIntent: (HomeCreateIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .statusBarsPadding(),
    ) {
        SimpleHeader(title = stringResource(Res.string.create_title))
        HomeCreateSuccessContent(
            state = state,
            onIntent = onIntent,
        )
    }
}
