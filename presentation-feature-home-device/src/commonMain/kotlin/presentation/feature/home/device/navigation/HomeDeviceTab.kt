package presentation.feature.home.device.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import presentation.feature.home.device.source.device.HomeDeviceScreen

/**
 * Top-level composable for the Device tab inside the Home screen.
 *
 * Hosts a Navigation 3 [NavDisplay] with its own back-stack, allowing the
 * Device tab to maintain independent navigation state. Transitions are
 * disabled (instant swap) to match the tab-switch UX.
 *
 * When [resetTrigger] is incremented (the user re-selects this tab), the
 * top back-stack entry is popped so the tab returns to its root.
 *
 * @param resetTrigger Monotonically increasing counter from the parent
 *   [presentation.feature.home.source.home.HomeViewModel]; triggers a
 *   back-stack pop when the user re-selects the already-active tab.
 *
 * @see HomeDeviceDestination
 * @see homeDeviceSavedStateConfig
 */
@Composable
public fun HomeDeviceTab(resetTrigger: Int = 0) {
    val backStack = rememberNavBackStack(
        homeDeviceSavedStateConfig,
        HomeDeviceDestination.Root,
    )

    // Pop the top entry when the user re-selects this tab.
    LaunchedEffect(resetTrigger) {
        if (resetTrigger > 0 && backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    NavDisplay(
        backStack = backStack,
        transitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
        popTransitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
        entryProvider = entryProvider {
            entry<HomeDeviceDestination.Root> { HomeDeviceScreen() }
        },
    )
}
