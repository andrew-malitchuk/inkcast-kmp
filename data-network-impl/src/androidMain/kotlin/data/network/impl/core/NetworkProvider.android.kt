package data.network.impl.core

import android.net.ConnectivityManager
import android.net.Network
import data.network.api.core.NetworkType
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

/**
 * Wraps a platform [SocketFactory] to catch `EPERM` errors during socket creation.
 *
 * When the bound [Network] disappears between the time we bind and the time
 * OkHttp actually opens a socket, the OS throws `EPERM`. This wrapper catches
 * that and falls back to the default (unbound) system socket factory so the
 * request still has a chance to complete rather than crashing.
 *
 * @param delegate The network-bound socket factory to wrap.
 */
private class SafeSocketFactory(private val delegate: SocketFactory) : SocketFactory() {

    // WORKAROUND: Android throws EPERM when a bound Network disappears mid-request.
    // Each override catches the exception and retries through the default unbound factory.

    override fun createSocket(): Socket =
        try { delegate.createSocket() } catch (_: Exception) { getDefault().createSocket() }

    override fun createSocket(host: String, port: Int): Socket =
        try { delegate.createSocket(host, port) } catch (_: Exception) { getDefault().createSocket(host, port) }

    override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket =
        try { delegate.createSocket(host, port, localHost, localPort) } catch (_: Exception) { getDefault().createSocket(host, port, localHost, localPort) }

    override fun createSocket(host: InetAddress, port: Int): Socket =
        try { delegate.createSocket(host, port) } catch (_: Exception) { getDefault().createSocket(host, port) }

    override fun createSocket(host: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket =
        try { delegate.createSocket(host, port, localAddress, localPort) } catch (_: Exception) { getDefault().createSocket(host, port, localAddress, localPort) }
}

/**
 * Android implementation of [NetworkProvider] using the OkHttp Ktor engine.
 *
 * Binds HTTP traffic to a specific [Network] interface (Wi-Fi or Cellular)
 * by injecting a [SafeSocketFactory] into OkHttp's configuration. This allows
 * the app to communicate with the ESP32 over Wi-Fi while the device is also
 * connected to the internet via cellular.
 *
 * Must be initialized via [initialize] before any client creation. Typically
 * called from `Application.onCreate()` after obtaining the system `ConnectivityManager`.
 *
 * @see SafeSocketFactory
 */
internal actual object NetworkProvider {

    @Volatile
    private var connectivityManager: ConnectivityManager? = null

    @Volatile
    private var wifiNetwork: Network? = null

    @Volatile
    private var cellularNetwork: Network? = null

    /**
     * Initializes the provider with the Android system [ConnectivityManager].
     *
     * @param cm System connectivity manager obtained via `getSystemService`.
     */
    fun initialize(cm: ConnectivityManager) {
        connectivityManager = cm
    }

    /**
     * Updates the current Wi-Fi [Network] reference used for socket binding.
     *
     * @param network The active Wi-Fi network, or `null` when Wi-Fi is lost.
     */
    fun setWifiNetwork(network: Network?) {
        wifiNetwork = network
    }

    /**
     * Updates the current Cellular [Network] reference used for socket binding.
     *
     * @param network The active cellular network, or `null` when cellular is lost.
     */
    fun setCellularNetwork(network: Network?) {
        cellularNetwork = network
    }

    /**
     * Returns the current Wi-Fi [Network] reference, if available.
     *
     * @return The bound Wi-Fi network, or `null` if not set or lost.
     */
    fun getWifiNetwork(): Network? = wifiNetwork

    actual fun createClient(
        network: NetworkType,
        block: HttpClientConfig<out HttpClientEngineConfig>.() -> Unit,
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    // NOTE: NetworkType.NONE intentionally skips binding so the OS
                    // picks the best available route (typically cellular for internet
                    // access while connected to a captive ESP32 hotspot).
                    val target = when (network) {
                        NetworkType.WIFI -> wifiNetwork
                        NetworkType.CELLULAR -> cellularNetwork
                        NetworkType.NONE -> null
                    }
                    target?.let {
                        socketFactory(SafeSocketFactory(it.socketFactory))
                    }
                }
            }
            block()
        }
    }
}
