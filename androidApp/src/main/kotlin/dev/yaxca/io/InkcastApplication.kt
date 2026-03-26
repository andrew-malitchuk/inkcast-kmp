package dev.yaxca.io

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import data.network.impl.core.NetworkInitializer
import dev.yaxca.io.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

/**
 * Custom [Application] subclass for the Inkcast Android target.
 *
 * Responsibilities:
 * - Bootstraps the Koin dependency graph via [initKoin].
 * - Registers system-level [ConnectivityManager] callbacks so that
 *   the networking layer can bind sockets to the correct Wi-Fi or
 *   Cellular [android.net.Network] interface.
 */
class InkcastApplication : Application() {

    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@InkcastApplication)
        }
        setupNetworkProvider()
    }

    /**
     * Initialises [NetworkInitializer] with the system [ConnectivityManager]
     * and registers callbacks for Wi-Fi and Cellular network availability,
     * allowing OkHttp clients to bind to the appropriate network interface.
     */
    private fun setupNetworkProvider() {
        connectivityManager = getSystemService(ConnectivityManager::class.java)
        NetworkInitializer.initialize(connectivityManager)

        val wifiRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        val cellularRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(
            wifiRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    NetworkInitializer.setWifiNetwork(network)
                }

                override fun onLost(network: Network) {
                    NetworkInitializer.setWifiNetwork(null)
                }
            },
        )

        connectivityManager.registerNetworkCallback(
            cellularRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    NetworkInitializer.setCellularNetwork(network)
                }

                override fun onLost(network: Network) {
                    NetworkInitializer.setCellularNetwork(null)
                }
            },
        )
    }
}
