package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.network.api.source.model.SettingItemNetwork
import data.repository.impl.core.mapper.base.ModelResourceMapper
import domain.core.source.model.SettingItemModel

/**
 * Bidirectional mapper between [SettingItemModel] and [SettingItemNetwork].
 *
 * The [SettingItemNetwork.value] (flexible [JsonElement]) is flattened to
 * [SettingItemModel.intValue] in the domain layer for simplicity.
 *
 * @see SettingItemModel
 * @see SettingItemNetwork
 */
internal object SettingItemNetworkMapper : ModelResourceMapper<SettingItemModel, SettingItemNetwork> {

    /** @see ModelResourceMapper.toModel */
    override val toModel: Mapper<SettingItemNetwork, SettingItemModel> =
        Mapper { resource ->
            SettingItemModel(
                key = resource.key,
                name = resource.name,
                category = resource.category,
                type = resource.type,
                // NOTE: intValue is a derived property on SettingItemNetwork that
                // safely extracts the integer from the flexible JsonElement value.
                intValue = resource.intValue,
                options = resource.options,
            )
        }

    /** @see ModelResourceMapper.toResource */
    override val toResource: Mapper<SettingItemModel, SettingItemNetwork> =
        Mapper { model ->
            SettingItemNetwork(
                key = model.key,
                name = model.name,
                category = model.category,
                type = model.type,
                // NOTE: Only the key is needed for update requests (Map<String, Int>),
                // so the value field is left null in the reverse mapping.
                value = null,
                options = model.options,
            )
        }
}
