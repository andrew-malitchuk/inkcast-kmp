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

@Composable
public fun HomeDeviceTab(resetTrigger: Int = 0) {
    val backStack = rememberNavBackStack(
        homeDeviceSavedStateConfig,
        HomeDeviceDestination.Root,
    )

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
