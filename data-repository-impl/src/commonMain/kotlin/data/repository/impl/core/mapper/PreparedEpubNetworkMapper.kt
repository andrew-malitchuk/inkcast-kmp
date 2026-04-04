package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.network.api.source.model.PreparedEpubNetwork
import data.repository.impl.core.mapper.base.ModelResourceMapper
import domain.core.source.model.PreparedEpubModel

/**
 * Bidirectional mapper between [PreparedEpubModel] and [PreparedEpubNetwork].
 *
 * @see PreparedEpubModel
 * @see PreparedEpubNetwork
 */
internal object PreparedEpubNetworkMapper : ModelResourceMapper<PreparedEpubModel, PreparedEpubNetwork> {

    /** @see ModelResourceMapper.toModel */
    override val toModel: Mapper<PreparedEpubNetwork, PreparedEpubModel> =
        Mapper { resource ->
            PreparedEpubModel(
                title = resource.title,
                bytes = resource.bytes,
            )
        }

    /** @see ModelResourceMapper.toResource */
    override val toResource: Mapper<PreparedEpubModel, PreparedEpubNetwork> =
        Mapper { model ->
            PreparedEpubNetwork(
                title = model.title,
                bytes = model.bytes,
            )
        }
}
