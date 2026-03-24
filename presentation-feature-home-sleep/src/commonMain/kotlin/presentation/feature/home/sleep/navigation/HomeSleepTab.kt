package presentation.feature.home.sleep.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import presentation.feature.home.sleep.source.sleep.HomeSleepScreen

@Composable
public fun HomeSleepTab(resetTrigger: Int = 0) {
    val backStack = rememberNavBackStack(
        homeSleepSavedStateConfig,
        HomeSleepDestination.Root,
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
            entry<HomeSleepDestination.Root> { HomeSleepScreen() }
        },
    )
}
