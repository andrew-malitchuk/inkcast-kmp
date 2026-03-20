package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.LanguagePreferenceSource
import data.preference.api.source.model.LanguagePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Default implementation of [LanguagePreferenceSource].
 *
 * Persists the language code via [Settings] and exposes
 * a [MutableStateFlow] for reactive observation.
 *
 * @property settings Platform settings store injected by Koin.
 * @see LanguagePreferenceSource
 */
internal class LanguagePreferenceSourceImpl(
    private val settings: Settings,
) : LanguagePreferenceSource {

    /** In-memory state flow initialised with the persisted value on construction. */
    private val _flow = MutableStateFlow(readFromSettings())

    /**
     * Reads the current language preference from the platform [Settings].
     *
     * Falls back to [LanguagePreference.DEFAULT_LANGUAGE] when no value has been persisted.
     *
     * @return A [LanguagePreference] with the stored or default language code.
     */
    private fun readFromSettings(): LanguagePreference {
        return LanguagePreference(
            languageCode = settings.getStringOrNull(KEY_LANGUAGE) ?: LanguagePreference.DEFAULT_LANGUAGE,
        )
    }

    /**
     * Retrieves the current language preference from persistent storage.
     *
     * @return The latest [LanguagePreference].
     */
    override suspend fun getData(): LanguagePreference = readFromSettings()

    /**
     * Persists the given [data] and notifies observers.
     *
     * @param data The [LanguagePreference] to persist.
     */
    override suspend fun setData(data: LanguagePreference) {
        settings[KEY_LANGUAGE] = data.languageCode
        _flow.value = data
    }

    /**
     * Observes the language preference as a reactive [Flow].
     *
     * @return A read-only [Flow] emitting [LanguagePreference] on every change.
     */
    override fun observeData(): Flow<LanguagePreference> = _flow.asStateFlow()

    private companion object {
        /** Settings key under which the language code is stored. */
        const val KEY_LANGUAGE = "pref_language"
    }
}
