package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.ThemePreferenceSource
import data.preference.api.source.model.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Default implementation of [ThemePreferenceSource].
 *
 * Persists the theme preference via [Settings] and exposes
 * a [MutableStateFlow] for reactive observation.
 *
 * @property settings Platform settings store injected by Koin.
 * @see ThemePreferenceSource
 */
internal class ThemePreferenceSourceImpl(
    private val settings: Settings,
) : ThemePreferenceSource {

    /** In-memory state flow initialised with the persisted value on construction. */
    private val _flow = MutableStateFlow(readFromSettings())

    /**
     * Reads the current theme from the platform [Settings].
     *
     * Falls back to [ThemePreference.DEFAULT_THEME] when no value has been persisted.
     *
     * @return A [ThemePreference] with the stored or default theme identifier.
     */
    private fun readFromSettings(): ThemePreference {
        return ThemePreference(
            theme = settings.getStringOrNull(KEY_THEME) ?: ThemePreference.DEFAULT_THEME,
        )
    }

    /**
     * Retrieves the current theme preference from persistent storage.
     *
     * @return The latest [ThemePreference].
     */
    override suspend fun getData(): ThemePreference = readFromSettings()

    /**
     * Persists the given [data] and notifies observers.
     *
     * @param data The [ThemePreference] to persist.
     */
    override suspend fun setData(data: ThemePreference) {
        settings[KEY_THEME] = data.theme
        _flow.value = data
    }

    /**
     * Observes the theme preference as a reactive [Flow].
     *
     * @return A read-only [Flow] emitting [ThemePreference] on every change.
     */
    override fun observeData(): Flow<ThemePreference> = _flow.asStateFlow()

    private companion object {
        /** Settings key under which the theme identifier is stored. */
        const val KEY_THEME = "pref_theme"
    }
}
