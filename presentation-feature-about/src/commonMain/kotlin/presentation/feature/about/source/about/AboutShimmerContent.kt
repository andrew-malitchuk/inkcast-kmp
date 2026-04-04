package presentation.feature.about.source.about

import androidx.compose.runtime.Composable
import presentation.core.ui.core.modifier.ShimmerProvider

/**
 * Shimmer/skeleton loading placeholder for the About screen.
 *
 * Displayed while the screen data is being loaded.
 *
 * @see AboutContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun AboutShimmerContent() {
    ShimmerProvider(isLoading = true) {
        // TODO: implement about screen shimmer
    }
}
