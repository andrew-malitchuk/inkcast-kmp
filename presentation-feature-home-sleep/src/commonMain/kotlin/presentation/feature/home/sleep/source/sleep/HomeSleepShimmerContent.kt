package presentation.feature.home.sleep.source.sleep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.core.modifier.ShimmerProvider
import presentation.core.ui.core.modifier.shimmerable

/**
 * Shimmer loading placeholder for the sleep screen gallery section.
 *
 * Displays a column of six shimmer rectangles that approximate the layout
 * of gallery items while the actual data is being fetched.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeSleepShimmerContent() {
    ShimmerProvider(isLoading = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Theme.spacing.spacingL,
                    end = Theme.spacing.spacingL,
                    top = Theme.spacing.spacingS,
                ),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .shimmerable(shape = RoundedCornerShape(12.dp)),
                )
            }
        }
    }
}
