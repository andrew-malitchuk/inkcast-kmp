package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.LastConnectedIpPreferenceSource
import data.preference.api.source.model.LastConnectedIpPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Default implementation of [LastConnectedIpPreferenceSource].
 *
 * Persists the last successfully connected device IP address via [Settings]
 * and exposes a [MutableStateFlow] for reactive observation.
 *
 * @property settings Platform settings store.
 */
internal class LastConnectedIpPreferenceSourceImpl(
    private val settings: Settings,
) : LastConnectedIpPreferenceSource {

    private val _flow = MutableStateFlow(readFromSettings())

    private fun readFromSettings(): LastConnectedIpPreference {
        return LastConnectedIpPreference(
            ip = settings.getStringOrNull(KEY_LAST_CONNECTED_IP),
        )
    }

    override suspend fun getData(): LastConnectedIpPreference = readFromSettings()

    override suspend fun setData(data: LastConnectedIpPreference) {
        if (data.ip != null) {
            settings[KEY_LAST_CONNECTED_IP] = data.ip
        } else {
            settings.remove(KEY_LAST_CONNECTED_IP)
        }
        _flow.value = data
    }

    override fun observeData(): Flow<LastConnectedIpPreference> = _flow.asStateFlow()

    private companion object {
        const val KEY_LAST_CONNECTED_IP = "pref_last_connected_ip"
    }
}
