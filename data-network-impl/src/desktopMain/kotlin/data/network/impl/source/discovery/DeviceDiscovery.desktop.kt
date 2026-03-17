package data.network.impl.source.discovery

internal actual class DeviceDiscovery actual constructor() {

    actual suspend fun discover(): List<String> = emptyList()
}
