package data.network.api.core

/**
 * Specifies the network transport interface for HTTP client socket binding.
 *
 * Used by the platform-specific `NetworkProvider` to route traffic through
 * the appropriate network interface on Android. Ignored on iOS and Desktop
 * where the OS handles routing natively.
 *
 * @see data.network.impl.core.NetworkProvider
 */
public enum class NetworkType {

    /**
     * Routes traffic through the Wi-Fi interface.
     *
     * Use for device API calls when connected to the ESP32 hotspot.
     * On Android, binds the OkHttp socket factory to the Wi-Fi [android.net.Network].
     */
    WIFI,

    /**
     * Routes traffic through the cellular data interface.
     *
     * Use when explicitly requiring cellular connectivity (e.g., telemetry uploads).
     * On Android, binds the OkHttp socket factory to the cellular [android.net.Network].
     */
    CELLULAR,

    /**
     * Performs no network binding; the OS chooses the best available route.
     *
     * Use for general internet access (e.g., downloading articles via
     * [LinkProcessingNetworkSource][data.network.api.source.datasource.LinkProcessingNetworkSource])
     * while connected to an ESP32 hotspot that has no internet gateway.
     */
    NONE,
}
