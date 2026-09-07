package presentation.feature.onboarding.source.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Root content composable for the Onboarding screen.
 *
 * Switches between onboarding steps using [Crossfade]:
 * - Step 0: welcome screen ([OnboardingSuccessContent])
 * - Step 1: hotspot setup guide ([OnboardingSetupContent])
 *
 * @param state Current [OnboardingState] driving the UI.
 * @param onIntent Callback to dispatch [OnboardingIntent] actions to the ViewModel.
 *
 * @see OnboardingSuccessContent
 * @see OnboardingSetupContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun OnboardingContent(
    state: OnboardingState,
    onIntent: (OnboardingIntent) -> Unit,
) {
    Crossfade(
        targetState = state.currentStep,
        modifier = Modifier.fillMaxSize(),
    ) { step ->
        when (step) {
            0 -> OnboardingSuccessContent(state = state, onIntent = onIntent)
            1 -> OnboardingSetupContent(state = state, onIntent = onIntent)
        }
    }
}
