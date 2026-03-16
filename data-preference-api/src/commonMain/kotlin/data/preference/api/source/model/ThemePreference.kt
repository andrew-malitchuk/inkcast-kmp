package data.preference.api.source.model

import data.core.source.resource.Resource

public data class ThemePreference(
    val theme: String = DEFAULT_THEME,
) : Resource {
    public companion object {
        public const val DEFAULT_THEME: String = "system"
    }
}
