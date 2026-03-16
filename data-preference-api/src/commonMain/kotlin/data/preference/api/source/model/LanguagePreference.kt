package data.preference.api.source.model

import data.core.source.resource.Resource

public data class LanguagePreference(
    val languageCode: String = DEFAULT_LANGUAGE,
) : Resource{
    public companion object {
        public const val DEFAULT_LANGUAGE: String = "en"
    }
}
