package data.preference.api.source.model

import data.core.source.resource.Resource

/**
 * Persisted preference holding the selected application theme.
 *
 * @property theme Theme identifier string (e.g., `"light"`, `"dark"`, `"system"`).
 *   Defaults to [DEFAULT_THEME].
 * @see data.preference.api.source.datasource.ThemePreferenceSource
 */
public data class ThemePreference(
    val theme: String = DEFAULT_THEME,
) : Resource {
    public companion object {
        /** Default theme applied when no preference has been stored yet. */
        public const val DEFAULT_THEME: String = "system"
    }
}
