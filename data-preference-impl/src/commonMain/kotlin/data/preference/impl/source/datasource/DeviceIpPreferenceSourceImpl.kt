package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.DeviceIpPreferenceSource
import data.preference.api.source.model.DeviceIpPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Default implementation of [DeviceIpPreferenceSource].
 *
 * Persists the device IP address via [Settings] and exposes
 * a [MutableStateFlow] for reactive observation.
 *
 * @property settings Platform settings store injected by Koin.
 * @see DeviceIpPreferenceSource
 */
internal class DeviceIpPreferenceSourceImpl(
    private val settings: Settings,
) : DeviceIpPreferenceSource {

    /** In-memory state flow initialised with the persisted value on construction. */
    private val _flow = MutableStateFlow(readFromSettings())

    /**
     * Reads the current device IP from the platform [Settings].
     *
     * @return A [DeviceIpPreference] with the stored IP, or `null` if none is persisted.
     */
    private fun readFromSettings(): DeviceIpPreference {
        return DeviceIpPreference(
            ip = settings.getStringOrNull(KEY_DEVICE_IP),
        )
    }

    /**
     * Retrieves the current device IP preference from persistent storage.
     *
     * @return The latest [DeviceIpPreference].
     */
    override suspend fun getData(): DeviceIpPreference = readFromSettings()

    /**
     * Persists the given [data] and notifies observers.
     *
     * When [DeviceIpPreference.ip] is `null`, the stored key is removed instead of
     * writing a null value, keeping the settings store clean.
     *
     * @param data The [DeviceIpPreference] to persist.
     */
    override suspend fun setData(data: DeviceIpPreference) {
        if (data.ip != null) {
            settings[KEY_DEVICE_IP] = data.ip
        } else {
            // Remove the key entirely when there is no IP to store.
            settings.remove(KEY_DEVICE_IP)
        }
        _flow.value = data
    }

    /**
     * Observes the device IP preference as a reactive [Flow].
     *
     * @return A read-only [Flow] emitting [DeviceIpPreference] on every change.
     */
    override fun observeData(): Flow<DeviceIpPreference> = _flow.asStateFlow()

    private companion object {
        /** Settings key under which the device IP address is stored. */
        const val KEY_DEVICE_IP = "pref_device_ip"
    }
}
