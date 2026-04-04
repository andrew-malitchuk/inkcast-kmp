package data.network.impl.source.discovery

/**
 * Desktop stub implementation of [DeviceDiscovery].
 *
 * Always returns an empty list — UDP broadcast discovery is not
 * supported on the desktop target.
 */
internal actual class DeviceDiscovery actual constructor() {

    /**
     * No-op on desktop. Always returns an empty list.
     *
     * @return An empty list, since UDP broadcast discovery is not supported on the desktop target.
     */
    actual suspend fun discover(): List<String> = emptyList()
}
