package data.preference.api.source.model

import data.core.source.resource.Resource

/**
 * Persisted preference holding the selected application locale.
 *
 * @property languageCode ISO 639-1 language code (e.g., `"en"`, `"uk"`, `"es"`, `"de"`).
 *   Defaults to [DEFAULT_LANGUAGE].
 * @see data.preference.api.source.datasource.LanguagePreferenceSource
 */
public data class LanguagePreference(
    val languageCode: String = DEFAULT_LANGUAGE,
) : Resource {
    public companion object {
        /** Default language applied when no preference has been stored yet. */
        public const val DEFAULT_LANGUAGE: String = "en"
    }
}
