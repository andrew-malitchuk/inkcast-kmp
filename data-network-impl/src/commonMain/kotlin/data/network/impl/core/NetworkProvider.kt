package data.network.impl.core

import data.network.api.core.NetworkType
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig

/**
 * Platform-specific HTTP client factory with optional network interface binding.
 *
 * Each platform supplies its own Ktor engine:
 * - **Android:** OkHttp with optional Wi-Fi / Cellular socket binding via `ConnectivityManager`.
 * - **iOS:** Darwin with `waitsForConnectivity` session configuration.
 * - **Desktop:** CIO engine without network binding.
 *
 * @see NetworkType
 * @see data.network.impl.source.datasource.CrossPointNetworkSourceImpl
 */
internal expect object NetworkProvider {

    /**
     * Creates a new [HttpClient] optionally bound to the specified [network] interface.
     *
     * Callers **must** close the returned client when done to release resources.
     * A fresh client is created per call to ensure clean connection state.
     *
     * @param network Target network transport for socket binding. Defaults to [NetworkType.WIFI].
     *   Ignored on iOS and Desktop where the OS handles routing natively.
     * @param block Additional Ktor client configuration lambda. Executed synchronously
     *   during client construction — do not perform I/O inside this block.
     * @return A configured [HttpClient] instance. The caller owns its lifecycle.
     */
    fun createClient(
        network: NetworkType = NetworkType.WIFI,
        block: HttpClientConfig<out HttpClientEngineConfig>.() -> Unit = {},
    ): HttpClient
}
