package presentation.feature.home.device.source.device

import androidx.lifecycle.ViewModel
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceSettingsUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceStatusUseCase
import domain.usecase.api.source.usecase.reader.UpdateDeviceSettingsUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container

/**
 * ViewModel for the device settings screen.
 *
 * Fetches device status and settings on init, then handles user intents
 * to update orientation and UI theme.
 *
 * @property getDeviceStatusUseCase Retrieves device system info (uptime, RAM, firmware, IP, Wi-Fi).
 * @property getDeviceSettingsUseCase Retrieves device display settings.
 * @property updateDeviceSettingsUseCase Pushes updated settings to the device.
 * @property getDeviceIpUseCase Retrieves the persisted device IP address.
 */
@OrbitExperimental
public class HomeDeviceViewModel(
    private val getDeviceStatusUseCase: GetDeviceStatusUseCase,
    private val getDeviceSettingsUseCase: GetDeviceSettingsUseCase,
    private val updateDeviceSettingsUseCase: UpdateDeviceSettingsUseCase,
    private val getDeviceIpUseCase: GetDeviceIpUseCase,
) : ContainerHost<HomeDeviceState, HomeDeviceSideEffect>, ViewModel() {

    override val container: Container<HomeDeviceState, HomeDeviceSideEffect> =
        container<HomeDeviceState, HomeDeviceSideEffect>(HomeDeviceState()) {
            loadDeviceData()
        }

    public fun handleIntent(intent: HomeDeviceIntent) {
        when (intent) {
            is HomeDeviceIntent.Refresh -> refreshDevice()
            is HomeDeviceIntent.SelectOrientation -> selectOrientation(intent.index)
            is HomeDeviceIntent.SelectTheme -> selectTheme(intent.index)
            is HomeDeviceIntent.ChangeDevice -> handleChangeDevice()
        }
    }

    private fun loadDeviceData() = intent {
        reduce { state.copy(isLoading = true) }

        // NOTE: Fetch device IP first to display in the info section.
        val ipResult = getDeviceIpUseCase()
        val ip = ipResult.getOrNull()

        val statusResult = getDeviceStatusUseCase()
        val settingsResult = getDeviceSettingsUseCase()

        val status = statusResult.getOrNull()
        val settings = settingsResult.getOrNull()

        if (status != null) {
            // NOTE: Parse settings list into named values.
            // Settings come as key-value pairs from the device firmware.
            val settingsMap = settings
                ?.associate { (it.key ?: "") to it.intValue }
                ?: emptyMap()

            reduce {
                state.copy(
                    isLoading = false,
                    isOnline = true,
                    uptime = formatUptime(status.uptime),
                    freeRam = formatBytes(status.freeHeap),
                    freeRamFraction = computeRamFraction(status.freeHeap),
                    firmware = status.version ?: "—",
                    ipAddress = ip ?: status.ip ?: "—",
                    wifiMode = status.mode ?: "—",
                    selectedOrientationIndex = settingsMap["orientation"] ?: 0,
                    selectedThemeIndex = settingsMap["uiTheme"] ?: 0,
                )
            }
        } else {
            reduce {
                state.copy(
                    isLoading = false,
                    isOnline = false,
                    ipAddress = ip ?: "—",
                )
            }
        }
    }

    private fun refreshDevice() = intent {
        reduce { state.copy(isRefreshing = true) }

        val statusResult = getDeviceStatusUseCase()
        val status = statusResult.getOrNull()

        if (status != null) {
            val settingsResult = getDeviceSettingsUseCase()
            val settings = settingsResult.getOrNull()
            val settingsMap = settings
                ?.associate { (it.key ?: "") to it.intValue }
                ?: emptyMap()

            reduce {
                state.copy(
                    isRefreshing = false,
                    isOnline = true,
                    uptime = formatUptime(status.uptime),
                    freeRam = formatBytes(status.freeHeap),
                    freeRamFraction = computeRamFraction(status.freeHeap),
                    firmware = status.version ?: "—",
                    wifiMode = status.mode ?: "—",
                    selectedOrientationIndex = settingsMap["orientation"] ?: state.selectedOrientationIndex,
                    selectedThemeIndex = settingsMap["uiTheme"] ?: state.selectedThemeIndex,
                )
            }
        } else {
            reduce { state.copy(isRefreshing = false, isOnline = false) }
        }
    }

    private fun selectOrientation(index: Int) = intent {
        val previous = state.selectedOrientationIndex
        reduce { state.copy(selectedOrientationIndex = index) }
        val result = updateDeviceSettingsUseCase(mapOf("orientation" to index))
        if (result.isFailure) {
            reduce { state.copy(selectedOrientationIndex = previous) }
            postSideEffect(HomeDeviceSideEffect.ShowError)
        }
    }

    private fun selectTheme(index: Int) = intent {
        val previous = state.selectedThemeIndex
        reduce { state.copy(selectedThemeIndex = index) }
        val result = updateDeviceSettingsUseCase(mapOf("uiTheme" to index))
        if (result.isFailure) {
            reduce { state.copy(selectedThemeIndex = previous) }
            postSideEffect(HomeDeviceSideEffect.ShowError)
        }
    }

    private fun handleChangeDevice() = intent {
        postSideEffect(HomeDeviceSideEffect.NavigateToConnection)
    }

    // region Formatting helpers

    private fun computeRamFraction(freeHeap: Long?): Float {
        if (freeHeap == null) return 0f
        // NOTE: ESP32 typical usable heap ~320 KB.
        val estimatedTotalHeap = 327_680L
        return (freeHeap.toFloat() / estimatedTotalHeap).coerceIn(0f, 1f)
    }

    private fun formatUptime(seconds: Long?): String {
        if (seconds == null) return "—"
        val m = seconds / 60
        val s = seconds % 60
        return "${m}m ${s}s"
    }

    private fun formatBytes(bytes: Long?): String {
        if (bytes == null) return "—"
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${bytes / 1024} KB"
            else -> {
                val mb = bytes / (1024.0 * 1024.0)
                val rounded = (mb * 10).toLong() / 10.0
                "$rounded MB"
            }
        }
    }

    // endregion
}
