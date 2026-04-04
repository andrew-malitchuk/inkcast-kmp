package data.repository.impl.core.mapper

import common.core.core.mapper.Mapper
import data.network.api.source.model.DeviceStatusNetwork
import data.repository.impl.core.mapper.base.ModelResourceMapper
import domain.core.source.model.DeviceStatusModel

/**
 * Bidirectional mapper between [DeviceStatusModel] and [DeviceStatusNetwork].
 *
 * @see DeviceStatusModel
 * @see DeviceStatusNetwork
 */
internal object DeviceStatusNetworkMapper : ModelResourceMapper<DeviceStatusModel, DeviceStatusNetwork> {

    /** @see ModelResourceMapper.toModel */
    override val toModel: Mapper<DeviceStatusNetwork, DeviceStatusModel> =
        Mapper { resource ->
            DeviceStatusModel(
                version = resource.version,
                ip = resource.ip,
                mode = resource.mode,
                rssi = resource.rssi,
                freeHeap = resource.freeHeap,
                uptime = resource.uptime,
            )
        }

    /** @see ModelResourceMapper.toResource */
    override val toResource: Mapper<DeviceStatusModel, DeviceStatusNetwork> =
        Mapper { model ->
            DeviceStatusNetwork(
                version = model.version,
                ip = model.ip,
                mode = model.mode,
                rssi = model.rssi,
                freeHeap = model.freeHeap,
                uptime = model.uptime,
            )
        }
}
