package presentation.feature.home.device.source.device

import androidx.compose.foundation.layout.Arrangement
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
internal fun HomeDeviceShimmerContent() {
    ShimmerProvider(isLoading = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Theme.spacing.spacingL),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            // Section header placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .shimmerable(shape = RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingXS))

            // 5 SettingRow placeholders
            repeat(5) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .shimmerable(shape = RoundedCornerShape(8.dp)),
                )
            }

            // Progress bar placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .shimmerable(shape = RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

            // Section header placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(16.dp)
                    .shimmerable(shape = RoundedCornerShape(4.dp)),
            )

            // SegmentedButtonGroup placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .shimmerable(shape = RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

            // Section header placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(16.dp)
                    .shimmerable(shape = RoundedCornerShape(4.dp)),
            )

            // SegmentedButtonGroup placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .shimmerable(shape = RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

            // Button placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shimmerable(shape = RoundedCornerShape(12.dp)),
            )
        }
    }
}
