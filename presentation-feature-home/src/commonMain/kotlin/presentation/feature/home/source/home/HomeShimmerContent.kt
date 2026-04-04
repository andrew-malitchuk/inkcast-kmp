package presentation.feature.home.source.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.core.ui.core.modifier.ShimmerProvider

/**
 * Shimmer loading placeholder for the Home screen.
 *
 * Displays an animated shimmer effect over a full-size box while
 * the Home screen data is being fetched. Replaced by
 * [HomeSuccessContent] once loading completes.
 *
 * @see HomeContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeShimmerContent() {
    ShimmerProvider(isLoading = true) {
        Box(modifier = Modifier.fillMaxSize())
    }
}
