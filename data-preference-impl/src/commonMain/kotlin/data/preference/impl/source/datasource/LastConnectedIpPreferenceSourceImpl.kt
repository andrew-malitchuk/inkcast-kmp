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
 * @property settings Platform settings store injected by Koin.
 * @see LastConnectedIpPreferenceSource
 */
internal class LastConnectedIpPreferenceSourceImpl(
    private val settings: Settings,
) : LastConnectedIpPreferenceSource {

    /** In-memory state flow initialised with the persisted value on construction. */
    private val _flow = MutableStateFlow(readFromSettings())

    /**
     * Reads the last connected IP from the platform [Settings].
     *
     * @return A [LastConnectedIpPreference] with the stored IP, or `null` if none is persisted.
     */
    private fun readFromSettings(): LastConnectedIpPreference {
        return LastConnectedIpPreference(
            ip = settings.getStringOrNull(KEY_LAST_CONNECTED_IP),
        )
    }

    /**
     * Retrieves the current last-connected IP preference from persistent storage.
     *
     * @return The latest [LastConnectedIpPreference].
     */
    override suspend fun getData(): LastConnectedIpPreference = readFromSettings()

    /**
     * Persists the given [data] and notifies observers.
     *
     * When [LastConnectedIpPreference.ip] is `null`, the stored key is removed instead of
     * writing a null value, keeping the settings store clean.
     *
     * @param data The [LastConnectedIpPreference] to persist.
     */
    override suspend fun setData(data: LastConnectedIpPreference) {
        if (data.ip != null) {
            settings[KEY_LAST_CONNECTED_IP] = data.ip
        } else {
            // Remove the key entirely when there is no IP to store.
            settings.remove(KEY_LAST_CONNECTED_IP)
        }
        _flow.value = data
    }

    /**
     * Observes the last-connected IP preference as a reactive [Flow].
     *
     * @return A read-only [Flow] emitting [LastConnectedIpPreference] on every change.
     */
    override fun observeData(): Flow<LastConnectedIpPreference> = _flow.asStateFlow()

    private companion object {
        /** Settings key under which the last connected IP address is stored. */
        const val KEY_LAST_CONNECTED_IP = "pref_last_connected_ip"
    }
}
