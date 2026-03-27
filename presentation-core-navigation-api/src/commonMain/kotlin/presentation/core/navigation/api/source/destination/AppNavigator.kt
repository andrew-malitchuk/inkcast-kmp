package presentation.core.navigation.api.source.destination

/**
 * Contract for application-level navigation between [Destination] screens.
 *
 * @see AppNavigatorImpl
 * @see Destination
 */
public interface AppNavigator {

    /**
     * Navigates to the specified [destination] with the given navigation [options].
     *
     * @param destination Target screen to navigate to.
     * @param options Navigation behavior modifier (default push, single-top, or clear task).
     */
    public fun navigate(destination: Destination, options: NavOptions = NavOptions.Default)

    /**
     * Pops the top entry from the back stack, returning to the previous screen.
     */
    public fun popBackStack()

    /**
     * Default back-navigation action, delegates to [popBackStack].
     */
    public fun backAction() {
        popBackStack()
    }

    /**
     * Navigation behavior options for [navigate].
     */
    public enum class NavOptions {
        /** Pushes the destination onto the back stack. */
        Default,
        /** Pushes only if the top of the stack is not already the same destination. */
        SingleTop,
        /** Clears the entire back stack before pushing the destination. */
        ClearTask,
    }
}
