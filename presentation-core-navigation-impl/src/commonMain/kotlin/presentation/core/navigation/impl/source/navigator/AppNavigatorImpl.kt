package presentation.core.navigation.impl.source.navigator

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import presentation.core.navigation.api.source.destination.AppNavigator
import presentation.core.navigation.api.source.destination.AppNavigator.NavOptions.ClearTask
import presentation.core.navigation.api.source.destination.AppNavigator.NavOptions.Default
import presentation.core.navigation.api.source.destination.AppNavigator.NavOptions.SingleTop
import presentation.core.navigation.api.source.destination.Destination

public class AppNavigatorImpl(
    private val backStack: NavBackStack<NavKey>,
) : AppNavigator {

    override fun popBackStack() {
        backStack.removeLastOrNull()
    }

    override fun navigate(destination: Destination, options: AppNavigator.NavOptions) {
        when (options) {
            Default -> backStack.add(destination)
            SingleTop -> {
                if (backStack.lastOrNull() != destination) backStack.add(destination)
            }
            ClearTask -> {
                while (backStack.isNotEmpty()) backStack.removeLastOrNull()
                backStack.add(destination)
            }
        }
    }
}
