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
 * @property settings Platform settings store.
 */
internal class DeviceIpPreferenceSourceImpl(
    private val settings: Settings,
) : DeviceIpPreferenceSource {

    private val _flow = MutableStateFlow(readFromSettings())

    private fun readFromSettings(): DeviceIpPreference {
        return DeviceIpPreference(
            ip = settings.getStringOrNull(KEY_DEVICE_IP),
        )
    }

    override suspend fun getData(): DeviceIpPreference = readFromSettings()

    override suspend fun setData(data: DeviceIpPreference) {
        if (data.ip != null) {
            settings[KEY_DEVICE_IP] = data.ip
        } else {
            settings.remove(KEY_DEVICE_IP)
        }
        _flow.value = data
    }

    override fun observeData(): Flow<DeviceIpPreference> = _flow.asStateFlow()

    private companion object {
        const val KEY_DEVICE_IP = "pref_device_ip"
    }
}
