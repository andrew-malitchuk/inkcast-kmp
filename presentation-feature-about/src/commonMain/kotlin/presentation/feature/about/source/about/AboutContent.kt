package presentation.feature.about.source.about

import androidx.compose.runtime.Composable

@Composable
internal fun AboutContent(
    state: AboutState,
    onIntent: (AboutIntent) -> Unit,
) {
    AboutSuccessContent(
        state = state,
        onIntent = onIntent,
    )
}
