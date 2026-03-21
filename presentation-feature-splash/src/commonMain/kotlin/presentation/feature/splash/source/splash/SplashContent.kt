package presentation.feature.splash.source.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun SplashContent(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
            SplashSuccessContent(
                state = state,
                onIntent = onIntent,
            )
    }
}
