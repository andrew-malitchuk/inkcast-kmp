package presentation.feature.connection.source.connection

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.reader.DiscoverDevicesUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.GetLastConnectedIpUseCase
import domain.usecase.api.source.usecase.reader.SetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.SetLastConnectedIpUseCase
import domain.usecase.api.source.usecase.reader.VerifyDeviceUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * ViewModel for the device connection screen.
 *
 * Loads the currently stored IP on init, then handles auto-discovery scans
 * and manual connection attempts. Persists the chosen IP via [SetDeviceIpUseCase]
 * before navigating back.
 *
 * @property getDeviceIpUseCase Retrieves the currently stored device IP address.
 * @property setDeviceIpUseCase Persists a new device IP address.
 * @property getLastConnectedIpUseCase Retrieves the last successfully connected device IP.
 * @property setLastConnectedIpUseCase Persists the last successfully connected device IP.
 * @property discoverDevicesUseCase Broadcasts UDP discovery and returns responding device IPs.
 * @property verifyDeviceUseCase Verifies a device at a given IP via direct HTTP check.
 */
@OrbitExperimental
public class ConnectionViewModel(
    private val getDeviceIpUseCase: GetDeviceIpUseCase,
    private val setDeviceIpUseCase: SetDeviceIpUseCase,
    private val getLastConnectedIpUseCase: GetLastConnectedIpUseCase,
    private val setLastConnectedIpUseCase: SetLastConnectedIpUseCase,
    private val discoverDevicesUseCase: DiscoverDevicesUseCase,
    private val verifyDeviceUseCase: VerifyDeviceUseCase,
) : ContainerHost<ConnectionState, ConnectionSideEffect>, ViewModel() {

    override val container: Container<ConnectionState, ConnectionSideEffect> =
        container<ConnectionState, ConnectionSideEffect>(ConnectionState()) {
            loadCurrentIp()
        }

    public fun handleIntent(intent: ConnectionIntent) {
        when (intent) {
            is ConnectionIntent.OnBackClick -> onBackClick()
            is ConnectionIntent.ScanForDevices -> scanForDevices()
            is ConnectionIntent.SelectDevice -> selectDevice(intent.ip)
            is ConnectionIntent.UpdateIpAddress -> updateIpAddress(intent.ip)
            is ConnectionIntent.ConnectManually -> connectManually()
        }
    }

    private fun loadCurrentIp() = intent {
        reduce { state.copy(isLoading = true) }
        val ip = getDeviceIpUseCase().getOrNull()
            ?: getLastConnectedIpUseCase().getOrNull()
        reduce {
            state.copy(
                isLoading = false,
                ipAddress = ip ?: "",
            )
        }
    }

    private fun onBackClick() = intent {
        postSideEffect(ConnectionSideEffect.NavigateBack)
    }

    /**
     * Performs a real UDP broadcast scan on port 8134 to discover
     * CrossPoint devices on the local network.
     */
    private fun scanForDevices() = intent {
        reduce {
            state.copy(
                isScanning = true,
                scanStatus = "SCANNING…",
                errorMessage = null,
                discoveredDevices = emptyList(),
            )
        }

        val result = discoverDevicesUseCase()
        val devices = result.getOrNull() ?: emptyList()

        if (devices.isNotEmpty()) {
            reduce {
                state.copy(
                    isScanning = false,
                    scanStatus = "",
                    discoveredDevices = devices,
                )
            }
        } else {
            reduce {
                state.copy(
                    isScanning = false,
                    scanStatus = "",
                    errorMessage = "",
                )
            }
            postSideEffect(ConnectionSideEffect.ShowError)
        }
    }

    /**
     * Handles tap on a discovered device. Since UDP discovery already
     * confirmed the device is reachable, we save the IP and navigate
     * back without an extra HTTP verification round-trip.
     */
    private fun selectDevice(ip: String) = intent {
        reduce { state.copy(isConnecting = true, errorMessage = null) }
        setDeviceIpUseCase(ip)
        setLastConnectedIpUseCase(ip)
        reduce { state.copy(isConnecting = false) }
        postSideEffect(ConnectionSideEffect.NavigateBack)
    }

    private fun updateIpAddress(ip: String) = intent {
        reduce { state.copy(ipAddress = ip, errorMessage = null) }
    }

    private fun connectManually() = intent {
        val ip = state.ipAddress.trim()
        if (ip.isBlank()) {
            reduce { state.copy(errorMessage = "Please enter an IP address.") }
            return@intent
        }
        connectToDevice(ip)
    }

    /**
     * Verifies the device at [ip] is reachable via a direct HTTP check
     * (bypasses preferences), then persists the IP and navigates back.
     */
    private fun connectToDevice(ip: String) = intent {
        reduce { state.copy(isConnecting = true, errorMessage = null) }

        val reachable = verifyDeviceUseCase(ip).getOrNull() ?: false

        if (reachable) {
            setDeviceIpUseCase(ip)
            setLastConnectedIpUseCase(ip)
            reduce { state.copy(isConnecting = false) }
            postSideEffect(ConnectionSideEffect.NavigateBack)
        } else {
            reduce {
                state.copy(
                    isConnecting = false,
                    errorMessage = "",
                )
            }
            postSideEffect(ConnectionSideEffect.ShowError)
        }
    }
}
