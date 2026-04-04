package presentation.feature.connection.source.connection

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
 * @property scanStatus Human-readable scan status label.
 * @property discoveredDevices List of discovered device IP addresses.
 * @property errorMessage Optional error message to display.
 */
public data class ConnectionState(
    val isLoading: Boolean = false,
    val isScanning: Boolean = false,
    val isConnecting: Boolean = false,
    val ipAddress: String = "",
    val scanStatus: String = "",
    val discoveredDevices: List<String> = emptyList(),
    val errorMessage: String? = null,
)

/**
 * One-time side effects emitted by [ConnectionViewModel].
 */
public sealed class ConnectionSideEffect {
    /** Navigate back to the previous screen. */
    public data object NavigateBack : ConnectionSideEffect()

    /** Show a generic error snackbar. */
    public data object ShowError : ConnectionSideEffect()
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
