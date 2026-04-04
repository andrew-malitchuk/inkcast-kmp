package data.network.impl.core

import data.network.api.core.NetworkType
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.darwin.Darwin

/**
 * iOS implementation of [NetworkProvider] using the Darwin Ktor engine.
 *
 * Configures the underlying `NSURLSession` to wait for connectivity rather
 * than failing immediately when the network is temporarily unavailable.
 * The [NetworkType] parameter is ignored — iOS handles network routing
 * natively without explicit socket binding.
 */
internal actual object NetworkProvider {

    actual fun createClient(
        network: NetworkType,
        block: HttpClientConfig<out HttpClientEngineConfig>.() -> Unit,
    ): HttpClient {
        return HttpClient(Darwin) {
            engine {
                configureSession {
                    // NOTE: waitsForConnectivity delays requests until a viable route
                    // exists, rather than failing with a network-unavailable error.
                    setWaitsForConnectivity(true)
                }
            }
            block()
        }
    }
}
