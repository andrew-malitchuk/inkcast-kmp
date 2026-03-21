package presentation.feature.splash.source.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.icon.AppIcon

@Composable
internal fun SplashSuccessContent(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = AppIcon,
            contentDescription = null,
            tint = Theme.color.inkMain,
            modifier = Modifier.size(256.dp),
        )
    }
}
