package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.preference.api.source.model.LanguagePreference

/**
 * Mapper for converting between [LanguagePreference] and a nullable locale-code [String].
 *
 * Unlike theme and onboarding, the domain representation is a plain [String]
 * rather than a dedicated [Model], so [ModelResourceMapper] is not used here.
 */
internal object LanguagePreferenceMapper {

    /**
     * Extracts the language code from a [LanguagePreference].
     */
    val toModel: Mapper<LanguagePreference, String?> =
        Mapper {
            it.languageCode
        }

    /**
     * Wraps a nullable locale-code [String] into a [LanguagePreference],
     * falling back to [LanguagePreference.DEFAULT_LANGUAGE] when `null`.
     */
    val toResource: Mapper<String?, LanguagePreference> =
        Mapper {
            LanguagePreference(it ?: LanguagePreference.DEFAULT_LANGUAGE)
        }
}
