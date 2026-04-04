package presentation.feature.home.source.home

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import inkcast_kmp.presentation_core_localisation.generated.resources.Res
import inkcast_kmp.presentation_core_localisation.generated.resources.settings_title
import inkcast_kmp.presentation_core_localisation.generated.resources.tab_create
import inkcast_kmp.presentation_core_localisation.generated.resources.tab_device
import inkcast_kmp.presentation_core_localisation.generated.resources.tab_files
import inkcast_kmp.presentation_core_localisation.generated.resources.tab_sleep
import org.jetbrains.compose.resources.stringResource
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.icon.FileText
import presentation.core.ui.source.kit.atom.icon.Lock
import presentation.core.ui.source.kit.atom.icon.Plus
import presentation.core.ui.source.kit.atom.icon.Settings
import presentation.core.ui.source.kit.atom.icon.Smartphone
import presentation.core.ui.source.kit.molecule.bar.tab.TabBar
import presentation.core.ui.source.kit.molecule.bar.tab.TabBarItem
import presentation.core.ui.source.kit.organism.animatedsequence.AnimatedItem
import presentation.core.ui.source.kit.organism.animatedsequence.AnimationSequenceHost
import presentation.feature.home.core.HomeTab
import presentation.feature.home.create.navigation.HomeCreateTab
import presentation.feature.home.device.navigation.HomeDeviceTab
import presentation.feature.home.files.navigation.HomeFilesTab
import presentation.feature.home.sleep.navigation.HomeSleepTab

/** Duration for the bottom bar slide-in animation. */
private const val BAR_SLIDE_DURATION_MS = 400

/** Delay before the bottom bar animation starts, allowing content to settle. */
private const val BAR_SLIDE_DELAY_MS = 200L

/**
 * Main content composable for the Home screen in its loaded state.
 *
 * Hosts the four feature tabs ([HomeTab]) via a [SaveableStateProvider] so
 * that each tab retains its scroll position across tab switches, and
 * renders an animated bottom navigation bar with a settings shortcut button.
 *
 * @param state Current immutable UI state snapshot from [HomeViewModel].
 * @param onIntent Callback that forwards user intents to the ViewModel.
 *
 * @see HomeContent
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun HomeSuccessContent(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    val tabBarItems = listOf(
        TabBarItem(icon = FileText, contentDescription = stringResource(Res.string.tab_files)),
        TabBarItem(icon = Plus, contentDescription = stringResource(Res.string.tab_create)),
        TabBarItem(icon = Lock, contentDescription = stringResource(Res.string.tab_sleep)),
        TabBarItem(icon = Smartphone, contentDescription = stringResource(Res.string.tab_device)),
    )

    AnimationSequenceHost(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas),
    ) {
        // Preserve child-tab state across tab switches.
        saveableStateHolder.SaveableStateProvider(state.selectedTab) {
            when (state.selectedTab) {
                HomeTab.FILES -> HomeFilesTab(resetTrigger = state.tabResetTrigger)
                HomeTab.CREATE -> HomeCreateTab(resetTrigger = state.tabResetTrigger)
                HomeTab.SLEEP -> HomeSleepTab(resetTrigger = state.tabResetTrigger)
                HomeTab.DEVICE -> HomeDeviceTab(resetTrigger = state.tabResetTrigger)
            }
        }

        // Bottom navigation bar slides in from below
        AnimatedItem(
            index = 0,
            delayAfterAnimation = BAR_SLIDE_DELAY_MS,
            enter = slideInVertically(
                animationSpec = tween(BAR_SLIDE_DURATION_MS),
                initialOffsetY = { fullHeight -> fullHeight },
            ) + fadeIn(tween(BAR_SLIDE_DURATION_MS)),
            exit = slideOutVertically(
                animationSpec = tween(BAR_SLIDE_DURATION_MS),
                targetOffsetY = { fullHeight -> fullHeight },
            ) + fadeOut(tween(BAR_SLIDE_DURATION_MS)),
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        start = Theme.spacing.spacingL,
                        end = Theme.spacing.spacingL,
                        bottom = Theme.spacing.spacingL,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TabBar(
                    items = tabBarItems,
                    selectedIndex = state.selectedTab.ordinal,
                    onItemClick = { index ->
                        onIntent(HomeIntent.OnTabSelected(HomeTab.entries[index]))
                    },
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(Theme.spacing.spacingS))
                // Circular settings button beside the tab bar.
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Theme.color.surfaceInverse)
                        .clickable { onIntent(HomeIntent.OnSettingsClick) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Settings,
                        contentDescription = stringResource(Res.string.settings_title),
                        tint = Theme.color.canvas,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}
