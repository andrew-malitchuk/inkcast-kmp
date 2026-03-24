package presentation.feature.home.sleep.source.sleep

import androidx.compose.runtime.Composable

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
