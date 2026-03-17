package data.network.api.core

/**
 * Provides the current device IP address for network requests.
 *
 * Abstracts the device address resolution so that data sources do not
 * hold a hardcoded IP. The underlying implementation reads from the
 * persisted preference store, allowing the address to change at runtime
 * (e.g., when the user connects to a different device).
 *
 * @see data.network.impl.core.DeviceAddressProviderImpl
 */
public interface DeviceAddressProvider {

    /**
     * Returns the currently configured device IP address.
     *
     * @return IP address string (e.g., `"192.168.4.1"`), or `null` if no device is configured.
     */
    public suspend fun getDeviceIp(): String?
}
