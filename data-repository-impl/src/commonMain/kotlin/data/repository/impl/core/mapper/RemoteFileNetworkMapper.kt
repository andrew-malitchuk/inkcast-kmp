package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.network.api.source.model.RemoteFileNetwork
import data.repository.impl.core.mapper.base.ModelResourceMapper
import domain.core.source.model.RemoteFileModel

/**
 * Bidirectional mapper between [RemoteFileModel] and [RemoteFileNetwork].
 *
 * @see RemoteFileModel
 * @see RemoteFileNetwork
 */
internal object RemoteFileNetworkMapper : ModelResourceMapper<RemoteFileModel, RemoteFileNetwork> {

    /** @see ModelResourceMapper.toModel */
    override val toModel: Mapper<RemoteFileNetwork, RemoteFileModel> =
        Mapper { resource ->
            RemoteFileModel(
                name = resource.name,
                size = resource.size,
                isDirectory = resource.isDirectory,
                isEpub = resource.isEpub,
            )
        }

    /** @see ModelResourceMapper.toResource */
    override val toResource: Mapper<RemoteFileModel, RemoteFileNetwork> =
        Mapper { model ->
            RemoteFileNetwork(
                name = model.name,
                size = model.size,
                isDirectory = model.isDirectory,
                isEpub = model.isEpub,
            )
        }
}
