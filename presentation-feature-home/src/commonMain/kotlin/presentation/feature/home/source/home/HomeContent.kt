package presentation.feature.home.source.home

import androidx.compose.runtime.Composable

@Composable
internal fun HomeContent(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    HomeSuccessContent(
        state = state,
        onIntent = onIntent,
    )
}
