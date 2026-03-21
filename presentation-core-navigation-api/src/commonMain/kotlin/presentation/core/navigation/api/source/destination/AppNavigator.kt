package presentation.core.navigation.api.source.destination

public interface AppNavigator {
    public fun navigate(destination: Destination, options: NavOptions = NavOptions.Default)
    public fun popBackStack()
    public fun backAction() {
        popBackStack()
    }

    public enum class NavOptions {
        Default,
        SingleTop,
        ClearTask,
    }
}
