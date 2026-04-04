package presentation.feature.home.source.home

import androidx.compose.runtime.Composable

/**
 * Root content composable for the Home screen.
 *
 * Acts as a state-driven content switcher. Currently delegates directly to
 * [HomeSuccessContent]; a shimmer or error branch can be added here when
 * the Home screen acquires its own loading state.
 *
 * @param state Current immutable UI state snapshot from [HomeViewModel].
 * @param onIntent Callback that forwards user intents to the ViewModel.
 *
 * @see HomeSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeContent(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    // Directly show the success layout; shimmer/error states can be
    // branched here in the future based on state.isLoading.
    HomeSuccessContent(
        state = state,
        onIntent = onIntent,
    )
}
