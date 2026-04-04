package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.preference.api.source.model.OnboardingPreference
import data.repository.impl.core.mapper.base.ModelResourceMapper
import domain.core.source.model.OnboardingModel

/**
 * Bidirectional mapper between [OnboardingModel] and [OnboardingPreference].
 *
 * Converts the data-layer [OnboardingPreference] (boolean-based) to the domain-layer
 * [OnboardingModel] and vice-versa.
 */
internal object OnboardingPreferenceMapper : ModelResourceMapper<OnboardingModel, OnboardingPreference> {

    /** @see ModelResourceMapper.toModel */
    override val toModel: Mapper<OnboardingPreference, OnboardingModel> =
        Mapper { preference ->
            OnboardingModel(isCompleted = preference.isCompleted)
        }

    /** @see ModelResourceMapper.toResource */
    override val toResource: Mapper<OnboardingModel, OnboardingPreference> =
        Mapper { model ->
            OnboardingPreference(isCompleted = model.isCompleted)
        }
}
