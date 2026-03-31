package presentation.feature.home.sleep.source.sleep

import androidx.compose.runtime.Composable

/**
 * Main content composable for the sleep screen editor.
 *
 * Delegates rendering to [HomeSleepSuccessContent]. Acts as the content root
 * that can be extended with loading/error state transitions in the future.
 *
 * @param state Current UI state of the sleep screen editor.
 * @param onIntent Callback to dispatch user intents to the ViewModel.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeSleepContent(
    state: HomeSleepState,
    onIntent: (HomeSleepIntent) -> Unit,
) {
    HomeSleepSuccessContent(
        state = state,
        onIntent = onIntent,
    )
}
