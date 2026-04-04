package presentation.feature.splash.source.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Root content composable for the Splash screen.
 *
 * Acts as a container that hosts [SplashSuccessContent]. Future iterations
 * may add shimmer/loading states via animated visibility transitions.
 *
 * @param state Current [SplashState] driving the UI.
 * @param onIntent Callback to dispatch [SplashIntent] actions to the ViewModel.
 *
 * @see SplashSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
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
