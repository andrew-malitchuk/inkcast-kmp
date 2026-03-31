package presentation.feature.home.files.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import org.orbitmvi.orbit.annotation.OrbitExperimental
import presentation.feature.home.files.source.files.HomeFilesScreen

/**
 * Top-level composable for the files tab inside the home screen.
 *
 * Manages a local navigation back stack scoped to the files tab and renders
 * the appropriate destination screen. Supports resetting the back stack to root
 * when the tab is re-selected.
 *
 * @param resetTrigger Incremented value that triggers popping the back stack to root.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@OptIn(OrbitExperimental::class)
@Composable
public fun HomeFilesTab(resetTrigger: Int = 0) {
    val backStack = rememberNavBackStack(
        homeFilesSavedStateConfig,
        HomeFilesDestination.Root,
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
            entry<HomeFilesDestination.Root> { HomeFilesScreen() }
        },
    )
}
