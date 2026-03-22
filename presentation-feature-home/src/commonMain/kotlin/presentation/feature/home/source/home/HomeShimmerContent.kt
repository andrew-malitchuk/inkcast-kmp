package presentation.feature.home.source.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.core.ui.core.modifier.ShimmerProvider

@Composable
internal fun HomeShimmerContent() {
    ShimmerProvider(isLoading = true) {
        Box(modifier = Modifier.fillMaxSize())
    }
}
