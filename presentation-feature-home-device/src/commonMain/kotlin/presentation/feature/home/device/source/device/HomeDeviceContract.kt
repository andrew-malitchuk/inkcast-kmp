package presentation.feature.home.device.source.device

/**
 * UI state for the device settings screen.
 *
 * Combines device system info, orientation, and UI theme into a single
 * immutable snapshot consumed by the content composable.
 *
 * @property isLoading Whether the initial data fetch is in progress.
 * @property isOnline Whether the device is currently reachable.
 * @property isRefreshing Whether a manual refresh is in progress.
 * @property uptime Human-readable device uptime (e.g., `"0m 47s"`).
 * @property freeRam Human-readable free RAM (e.g., `"123 KB"`).
 * @property freeRamFraction Fraction of free RAM in `0f..1f` range for progress indicator.
 * @property firmware Firmware version string (e.g., `"1.1.1"`).
 * @property ipAddress Device IP address (e.g., `"192.168.4.1"`).
 * @property wifiMode Wi-Fi operating mode (e.g., `"AP"`).
 * @property selectedOrientationIndex Index into [orientationOptions].
 * @property orientationOptions Available screen orientation labels.
 * @property selectedThemeIndex Index into [themeOptions].
 * @property themeOptions Available UI theme labels.
 */
public data class HomeDeviceState(
    val isLoading: Boolean = true,
    val isOnline: Boolean = false,
    val isRefreshing: Boolean = false,
    // System info
    val uptime: String = "—",
    val freeRam: String = "—",
    val freeRamFraction: Float = 0f,
    val firmware: String = "—",
    val ipAddress: String = "—",
    val wifiMode: String = "—",
    // Orientation
    val selectedOrientationIndex: Int = 0,
    val orientationOptions: List<String> = listOf("Portrait", "Landscape CW", "Inverted", "Landscape CCW"),
    // UI Theme
    val selectedThemeIndex: Int = 0,
    val themeOptions: List<String> = listOf("Classic", "Lyra", "Lyra Extended"),
    val isClearingCache: Boolean = false,
)

/**
 * One-time side effects emitted by [HomeDeviceViewModel].
 */
public sealed class HomeDeviceSideEffect {
    /** Show an error snackbar. */
    public data object ShowError : HomeDeviceSideEffect()

    /** Navigate to the device connection screen. */
    public data object NavigateToConnection : HomeDeviceSideEffect()

    /** Cache was cleared successfully. */
    public data object ShowCacheCleared : HomeDeviceSideEffect()

    /** Cache clear failed. */
    public data object ShowCacheClearFailed : HomeDeviceSideEffect()
}

/**
 * User intents dispatched from the device settings content composable.
 */
public sealed class HomeDeviceIntent {
    /** Refresh device status and settings from the device. */
    public data object Refresh : HomeDeviceIntent()

    /**
     * Select a screen orientation.
     *
     * @property index Index into the orientation options list.
     */
    public data class SelectOrientation(val index: Int) : HomeDeviceIntent()

    /**
     * Select a UI theme.
     *
     * @property index Index into the theme options list.
     */
    public data class SelectTheme(val index: Int) : HomeDeviceIntent()

    /** Navigate to the "Change Device" flow. */
    public data object ChangeDevice : HomeDeviceIntent()

    /** Clear the `.crosspoint/` rendering cache on the device. */
    public data object ClearCache : HomeDeviceIntent()
}
