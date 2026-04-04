package presentation.core.navigation.impl.source.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import presentation.core.navigation.api.core.composition.LocalAppNavigator
import presentation.core.navigation.api.core.composition.LocalBackAction
import presentation.core.navigation.api.source.destination.Destination
import presentation.core.navigation.impl.source.navigator.AppNavigatorImpl
import presentation.core.navigation.impl.source.serialization.navSavedStateConfiguration
import presentation.feature.about.source.about.AboutScreen
import presentation.feature.connection.source.connection.ConnectionScreen
import presentation.feature.home.source.home.HomeScreen
import presentation.feature.onboarding.source.onboarding.OnboardingScreen
import presentation.feature.settings.source.settings.SettingsScreen
import presentation.feature.splash.source.splash.SplashScreen

/**
 * Root navigation host that maps [Destination] entries to feature screen composables.
 *
 * Sets up the Navigation 3 back stack, provides [AppNavigator] and back-action callback
 * via composition locals, and disables default transition animations.
 *
 * @param startDestination Initial destination, defaults to [Destination.Splash].
 *
 * @see Destination
 * @see AppNavigatorImpl
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun NavigationHost(startDestination: Destination? = null) {
    val backStack = rememberNavBackStack(
        navSavedStateConfiguration,
        startDestination ?: Destination.Splash
    )
    val appNavigator = remember(backStack) { AppNavigatorImpl(backStack) }

    CompositionLocalProvider(
        LocalAppNavigator provides appNavigator,
        LocalBackAction provides { appNavigator.popBackStack() },
    ) {
        NavDisplay(
            backStack = backStack,
            transitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
            popTransitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
            entryProvider = entryProvider {
                entry<Destination.Splash> { SplashScreen() }
                entry<Destination.Onboarding> { OnboardingScreen() }
                entry<Destination.Home> { HomeScreen() }
                entry<Destination.Connection> { dest -> ConnectionScreen(isInitialSetup = dest.isInitialSetup) }
                entry<Destination.Settings> { SettingsScreen() }
                entry<Destination.About> { AboutScreen() }
            },
        )
    }
}
