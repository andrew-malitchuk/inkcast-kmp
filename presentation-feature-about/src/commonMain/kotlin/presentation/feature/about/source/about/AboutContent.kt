package presentation.feature.about.source.about

import androidx.compose.runtime.Composable

/**
 * Content router for the About screen that delegates to the appropriate sub-content.
 *
 * @param state Current UI state driving the content.
 * @param onIntent Callback to dispatch user intents to the ViewModel.
 *
 * @see AboutSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
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
