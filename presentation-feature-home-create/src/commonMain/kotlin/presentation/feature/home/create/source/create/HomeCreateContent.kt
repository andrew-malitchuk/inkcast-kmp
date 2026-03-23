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
