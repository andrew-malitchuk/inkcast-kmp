package presentation.feature.home.device.source.device

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.device_title
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.icon.RefreshCcw
import presentation.core.ui.source.kit.molecule.header.ActionHeader

/**
 * Root content composable for the device settings screen.
 *
 * Renders the screen header with a refresh action and cross-fades between
 * [HomeDeviceShimmerContent] (while data is loading) and
 * [HomeDeviceSuccessContent] (once the device status is available).
 *
 * @param state Current immutable UI state snapshot from [HomeDeviceViewModel].
 * @param onIntent Callback that forwards user intents to the ViewModel.
 *
 * @see HomeDeviceShimmerContent
 * @see HomeDeviceSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeDeviceContent(
    state: HomeDeviceState,
    onIntent: (HomeDeviceIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .statusBarsPadding(),
    ) {
        ActionHeader(
            title = stringResource(Res.string.device_title),
            actionIcon = RefreshCcw,
            onActionClick = { onIntent(HomeDeviceIntent.Refresh) },
        )
        // Cross-fade between shimmer and loaded content.
        Crossfade(
            targetState = state.isLoading,
            modifier = Modifier.fillMaxSize(),
        ) { loading ->
            if (loading) {
                HomeDeviceShimmerContent()
            } else {
                HomeDeviceSuccessContent(
                    state = state,
                    onIntent = onIntent,
                )
            }
        }
    }
}
