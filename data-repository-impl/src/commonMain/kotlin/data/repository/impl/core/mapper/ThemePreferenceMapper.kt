package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.preference.api.source.model.ThemePreference
import data.repository.impl.core.mapper.base.ModelResourceMapper
import domain.core.source.model.ThemeModel

/**
 * Bidirectional mapper between [ThemeModel] and [ThemePreference].
 *
 * Converts the data-layer [ThemePreference] (string-based) to the domain-layer
 * [ThemeModel] enum and vice-versa. Falls back to `null` when the stored string
 * does not match any known [ThemeModel] entry.
 */
internal object ThemePreferenceMapper : ModelResourceMapper<ThemeModel, ThemePreference> {

    /** @see ModelResourceMapper.toModel */
    override val toModel: Mapper<ThemePreference, ThemeModel> =
        Mapper { preference ->
            ThemeModel.entries.firstOrNull { it.mode == preference.theme }
                ?: ThemeModel.Light
        }

    /** @see ModelResourceMapper.toResource */
    override val toResource: Mapper<ThemeModel, ThemePreference> =
        Mapper { model ->
            ThemePreference(theme = model.mode)
        }
}
