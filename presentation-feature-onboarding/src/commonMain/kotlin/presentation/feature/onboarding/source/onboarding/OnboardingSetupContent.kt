package presentation.feature.onboarding.source.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_continue
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_setup_hint
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_setup_step1
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_setup_step2
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_setup_step3
import inkcast_kmp.presentation_core_localisation.generated.resources.onboarding_setup_title
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.container.IconContainer
import presentation.core.ui.source.kit.atom.icon.Wifi
import presentation.core.ui.source.kit.organism.animatedsequence.AnimatedItem
import presentation.core.ui.source.kit.organism.animatedsequence.AnimationSequenceHost

/** Duration in milliseconds for individual enter/exit animations. */
private const val ANIM_DURATION_MS = 400

/** Delay in milliseconds between sequenced animation items. */
private const val ANIM_DELAY_MS = 200L

/**
 * Step 2 of the Onboarding flow — hotspot connection instructions.
 *
 * Explains the three-step process for connecting the phone to the
 * e-reader's Wi-Fi hotspot before scanning for the device.
 *
 * @param state Current [OnboardingState]; used to toggle the button's loading indicator.
 * @param onIntent Callback to dispatch [OnboardingIntent] actions to the ViewModel.
 *
 * @see OnboardingContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun OnboardingSetupContent(
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Content block: icon + title + steps — fades in together
            AnimatedItem(
                index = 0,
                delayAfterAnimation = ANIM_DELAY_MS,
                enter = fadeIn(tween(ANIM_DURATION_MS)),
                exit = fadeOut(tween(ANIM_DURATION_MS)),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconContainer(
                        modifier = Modifier.size(Theme.spacing.spacing5XL),
                        icon = Wifi,
                        backgroundColor = Theme.color.inkMain,
                        foregroundColor = Theme.color.surface,
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacing3XL))

                    Text(
                        text = stringResource(Res.string.onboarding_setup_title),
                        style = Theme.typography.display,
                        color = Theme.color.inkMain,
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingXL))

                    Text(
                        text = stringResource(Res.string.onboarding_setup_step1),
                        style = Theme.typography.body,
                        color = Theme.color.inkMain,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                    Text(
                        text = stringResource(Res.string.onboarding_setup_step2),
                        style = Theme.typography.body,
                        color = Theme.color.inkMain,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))

                    Text(
                        text = stringResource(Res.string.onboarding_setup_step3),
                        style = Theme.typography.body,
                        color = Theme.color.inkMain,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.spacingL))

                    Text(
                        text = stringResource(Res.string.onboarding_setup_hint),
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                        modifier = Modifier.fillMaxWidth(),
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
                    text = stringResource(Res.string.onboarding_continue),
                    onClick = { onIntent(OnboardingIntent.OnContinueClick) },
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
