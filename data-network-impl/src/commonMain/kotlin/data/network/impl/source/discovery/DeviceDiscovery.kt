package data.network.impl.source.discovery

/**
 * Platform-specific UDP device discovery.
 *
 * Sends a `"hello"` broadcast to `255.255.255.255:8134` and collects
 * responses from CrossPoint devices within a 3-second timeout window.
 *
 * - **Android:** Java `DatagramSocket` bound to the Wi-Fi network.
 * - **iOS:** POSIX UDP socket with `SO_BROADCAST`.
 * - **Desktop:** Stub returning an empty list.
 */
internal expect class DeviceDiscovery() {

    /**
     * Performs a UDP broadcast scan and returns discovered device IPs.
     *
     * @return List of IP address strings from responding devices.
     */
    suspend fun discover(): List<String>
}
