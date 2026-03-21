package presentation.feature.onboarding.source.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
