package data.network.api.source.datasource

/**
 * Contract for discovering CrossPoint devices on the local network.
 *
 * Implementations send a UDP broadcast and collect responses from
 * reachable ESP32 devices within a timeout window.
 *
 * @see data.network.impl.source.datasource.DeviceDiscoverySourceImpl
 */
public interface DeviceDiscoverySource {

    /**
     * Broadcasts a discovery packet on the local network and returns
     * the IP addresses of all responding devices.
     *
     * @return List of discovered device IP address strings (e.g., `"192.168.4.1"`).
     *   Returns an empty list if no devices respond within the timeout window.
     */
    public suspend fun discover(): List<String>
}
