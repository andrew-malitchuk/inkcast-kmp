package presentation.feature.connection.source.connection

/**
 * Typed error variants for the connection screen.
 *
 * Each value maps to a specific localised string in the composable layer —
 * no raw strings are stored in the ViewModel or state.
 */
public enum class ConnectionErrorType {
    /** Scan completed but no devices responded on the network. */
    NoDevicesFound,

    /** A network-level error occurred during the scan. */
    NetworkError,

    /** The device at the entered IP address did not respond to a reachability check. */
    DeviceNotReachable,

    /** The manually entered IP address has an invalid format. */
    InvalidIp,

    /** The manual IP field was blank when the user tapped Connect. */
    EmptyIp,
}

/**
 * UI state for the device connection screen.
 *
 * Combines auto-discovery and manual connection states into a single
 * immutable snapshot consumed by the content composable.
 *
 * @property isLoading Whether the initial data fetch is in progress.
 * @property isScanning Whether a network scan is currently running.
 * @property isConnecting Whether a connection attempt is in progress.
 * @property ipAddress Current value of the manual IP input field.
 * @property discoveredDevices List of discovered device IP addresses.
 */
public data class ConnectionState(
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val isConnecting: Boolean = false,
    val ipAddress: String = "",
    val discoveredDevices: List<String> = emptyList(),
)

/**
 * One-time side effects emitted by [ConnectionViewModel].
 */
public sealed class ConnectionSideEffect {
    /** Navigate back to the previous screen. */
    public data object NavigateBack : ConnectionSideEffect()

    /**
     * Show an error snackbar with a localised message resolved from [errorType].
     *
     * @property errorType Typed error variant — resolved to a string via [stringResource] in the Screen.
     */
    public data class ShowError(val errorType: ConnectionErrorType) : ConnectionSideEffect()
}

/**
 * User intents dispatched from the connection content composable.
 */
public sealed class ConnectionIntent {
    /** Navigate back to the previous screen. */
    public data object OnBackClick : ConnectionIntent()

    /** Trigger auto-discovery scan on the local network. */
    public data object ScanForDevices : ConnectionIntent()

    /**
     * Select a discovered device to connect to.
     *
     * @property ip IP address of the selected device.
     */
    public data class SelectDevice(val ip: String) : ConnectionIntent()

    /**
     * Update the manual IP address input field.
     *
     * @property ip New IP address string.
     */
    public data class UpdateIpAddress(val ip: String) : ConnectionIntent()

    /** Connect using the manually entered IP address. */
    public data object ConnectManually : ConnectionIntent()
}
