package presentation.feature.onboarding.source.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Root content composable for the Onboarding screen.
 *
 * Acts as a container that hosts [OnboardingSuccessContent]. Future iterations
 * may add shimmer/loading states via animated visibility transitions.
 *
 * @param state Current [OnboardingState] driving the UI.
 * @param onIntent Callback to dispatch [OnboardingIntent] actions to the ViewModel.
 *
 * @see OnboardingSuccessContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun OnboardingContent(
    state: OnboardingState,
    onIntent: (OnboardingIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
            OnboardingSuccessContent(
                state = state,
                onIntent = onIntent,
            )
    }
}
