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

/**
 * Success-state content for the Splash screen.
 *
 * Displays the application icon centered on the canvas background.
 * This is shown while [SplashViewModel] determines the initial navigation destination.
 *
 * @param state Current [SplashState]; reserved for future use (e.g. progress indicators).
 * @param onIntent Callback to dispatch [SplashIntent] actions to the ViewModel.
 *
 * @see SplashContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
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
