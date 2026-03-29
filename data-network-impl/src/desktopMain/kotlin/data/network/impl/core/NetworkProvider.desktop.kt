package data.network.impl.core

import data.network.api.core.NetworkType
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.cio.CIO

/**
 * Desktop (JVM) implementation of [NetworkProvider] using the CIO Ktor engine.
 *
 * No network interface binding is available on desktop JVM.
 * The [NetworkType] parameter is accepted for API compatibility but ignored.
 */
internal actual object NetworkProvider {

    /**
     * Creates a Ktor [HttpClient] using the CIO engine.
     *
     * On desktop JVM, network interface binding is not available, so the [network]
     * parameter is accepted for API compatibility but ignored.
     *
     * @param network The requested network type (ignored on desktop).
     * @param block Optional configuration block applied to the [HttpClient].
     * @return A configured [HttpClient] instance.
     */
    actual fun createClient(
        network: NetworkType,
        block: HttpClientConfig<out HttpClientEngineConfig>.() -> Unit,
    ): HttpClient {
        return HttpClient(CIO) {
            block()
        }
    }
}
