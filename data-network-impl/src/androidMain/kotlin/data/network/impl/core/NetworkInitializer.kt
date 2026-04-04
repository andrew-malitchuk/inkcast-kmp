package data.network.impl.core

import android.net.ConnectivityManager
import android.net.Network

/**
 * Public Android-specific entry point for initializing the network layer.
 *
 * Must be called from `Application.onCreate()` after obtaining the system
 * [ConnectivityManager]. Without this call, HTTP clients will still function
 * but will not be bound to a specific network interface (Wi-Fi / Cellular).
 */
public object NetworkInitializer {

    /**
     * Initializes the network provider with the system [ConnectivityManager].
     *
     * @param connectivityManager System connectivity manager from `getSystemService`.
     */
    public fun initialize(connectivityManager: ConnectivityManager) {
        NetworkProvider.initialize(connectivityManager)
    }

    /**
     * Updates the current Wi-Fi [Network] reference used for socket binding.
     *
     * @param network The active Wi-Fi network, or `null` when Wi-Fi is lost.
     */
    public fun setWifiNetwork(network: Network?) {
        NetworkProvider.setWifiNetwork(network)
    }

    /**
     * Updates the current Cellular [Network] reference used for socket binding.
     *
     * @param network The active cellular network, or `null` when cellular is lost.
     */
    public fun setCellularNetwork(network: Network?) {
        NetworkProvider.setCellularNetwork(network)
    }
}
