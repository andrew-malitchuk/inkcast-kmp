package data.network.impl.source.datasource

import data.network.api.source.datasource.DeviceDiscoverySource
import data.network.impl.source.discovery.DeviceDiscovery

/**
 * Default implementation of [DeviceDiscoverySource].
 *
 * Delegates to the platform-specific [DeviceDiscovery] expect/actual class
 * which performs UDP broadcast scanning.
 *
 * @see DeviceDiscoverySource
 * @see DeviceDiscovery
 */
internal class DeviceDiscoverySourceImpl : DeviceDiscoverySource {

    override suspend fun discover(): List<String> = DeviceDiscovery().discover()
}
