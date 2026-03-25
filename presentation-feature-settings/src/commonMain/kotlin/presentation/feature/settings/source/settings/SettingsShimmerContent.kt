package presentation.feature.settings.source.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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

@Composable
internal fun SettingsShimmerContent() {
    ShimmerProvider(isLoading = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Theme.spacing.spacingL),
        ) {
            // Section header placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(16.dp)
                    .shimmerable(shape = RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

            // SegmentedButtonGroup placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .shimmerable(shape = RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

            // Section header placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(16.dp)
                    .shimmerable(shape = RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

            // SegmentedButtonGroup placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .shimmerable(shape = RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacing5XL))
        }
    }
}
