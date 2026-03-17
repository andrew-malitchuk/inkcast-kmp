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

    actual fun createClient(
        network: NetworkType,
        block: HttpClientConfig<out HttpClientEngineConfig>.() -> Unit,
    ): HttpClient {
        return HttpClient(CIO) {
            block()
        }
    }
}
