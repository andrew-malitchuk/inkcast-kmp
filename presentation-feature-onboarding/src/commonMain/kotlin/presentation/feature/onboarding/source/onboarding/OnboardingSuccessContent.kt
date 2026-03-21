package presentation.feature.onboarding.source.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.container.IconContainer
import presentation.core.ui.source.kit.atom.icon.Wizard
import presentation.core.ui.source.kit.organism.animatedsequence.AnimatedItem
import presentation.core.ui.source.kit.organism.animatedsequence.AnimationSequenceHost
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_description
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_get_started
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_title
import org.jetbrains.compose.resources.stringResource

private const val ANIM_DURATION_MS = 400
private const val ANIM_DELAY_MS = 200L

@Composable
internal fun OnboardingSuccessContent(
    state: OnboardingState,
    onIntent: (OnboardingIntent) -> Unit,
) {
    AnimationSequenceHost(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .padding(horizontal = Theme.spacing.spacingL),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Content block: illustration + title + description — fades in together
            AnimatedItem(
                index = 0,
                delayAfterAnimation = ANIM_DELAY_MS,
                enter = fadeIn(tween(ANIM_DURATION_MS)),
                exit = fadeOut(tween(ANIM_DURATION_MS)),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        imageVector = Wizard,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(horizontal = Theme.spacing.spacing3XL),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacing3XL))

                    Text(
                        text = stringResource(Res.string.onboarding_title),
                        style = Theme.typography.display,
                        color = Theme.color.inkMain,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

                    Text(
                        text = stringResource(Res.string.onboarding_description),
                        style = Theme.typography.body,
                        color = Theme.color.inkSubtle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.spacingL),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Button slides in from the bottom
            AnimatedItem(
                index = 1,
                delayAfterAnimation = ANIM_DELAY_MS,
                enter = slideInVertically(
                    animationSpec = tween(ANIM_DURATION_MS),
                    initialOffsetY = { it },
                ) + fadeIn(tween(ANIM_DURATION_MS)),
                exit = slideOutVertically(
                    animationSpec = tween(ANIM_DURATION_MS),
                    targetOffsetY = { it },
                ) + fadeOut(tween(ANIM_DURATION_MS)),
            ) {
                Button(
                    text = stringResource(Res.string.onboarding_get_started),
                    onClick = { onIntent(OnboardingIntent.OnGetStartedClick) },
                    style = ButtonStyle.Primary,
                    size = ButtonSizeType.Large,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    isLoading = state.isLoading,
                )
            }

            Spacer(modifier = Modifier.height(Theme.spacing.spacing2XL))
        }
    }
}
