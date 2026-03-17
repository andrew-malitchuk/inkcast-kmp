package data.network.impl.source.datasource

import data.network.api.core.DeviceAddressProvider
import data.network.api.source.datasource.CrossPointNetworkSource
import data.network.api.source.model.DeviceStatusNetwork
import data.network.api.source.model.RemoteFileNetwork
import data.network.api.source.model.SettingItemNetwork
import data.network.impl.core.NetworkProvider
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.timeout
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Ktor-based implementation of [CrossPointNetworkSource] for ESP32 device communication.
 *
 * Creates a **fresh HTTP client per request** to ensure clean connection state and
 * proper Wi-Fi network binding via [NetworkProvider]. Each client is closed in a
 * `finally` block to prevent resource leaks.
 *
 * The device IP is resolved dynamically via [DeviceAddressProvider] on every request,
 * so the address can change at runtime when the user connects to a different device.
 *
 * @property deviceAddressProvider Provides the current device IP from persisted preferences.
 * @see CrossPointNetworkSource
 * @see NetworkProvider
 * @see DeviceAddressProvider
 */
internal class CrossPointNetworkSourceImpl(
    private val deviceAddressProvider: DeviceAddressProvider,
) : CrossPointNetworkSource {

    /**
     * Creates a pre-configured Ktor HTTP client with JSON content negotiation.
     *
     * @return A new [io.ktor.client.HttpClient] bound to the Wi-Fi network.
     */
    private fun createClient() = NetworkProvider.createClient {
        install(ContentNegotiation) {
            // NOTE: ignoreUnknownKeys is required because the ESP32 firmware may
            // add new fields in updates that the app does not yet model.
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            // NOTE: The ESP32 can be slow to respond — especially on first
            // contact after deep sleep — so allow a generous connect timeout.
            connectTimeoutMillis = 10_000
            requestTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }
    }

    // NOTE: Resolved on every call to pick up runtime IP changes.
    private suspend fun baseUrl(): String {
        val ip = deviceAddressProvider.getDeviceIp()
            ?: error("Device IP is not configured")
        return "http://$ip"
    }

    override suspend fun downloadFile(path: String): ByteArray? {
        val client = createClient()
        return try {
            val base = baseUrl()
            val response = client.get("$base/download") {
                parameter("path", path)
                timeout {
                    // NOTE: Extended timeouts for file downloads — EPUB files can be
                    // several MB and the ESP32 SD card read speed is limited.
                    requestTimeoutMillis = 60_000
                    socketTimeoutMillis = 60_000
                    connectTimeoutMillis = 15_000
                }
            }
            if (response.status.isSuccess()) response.body<ByteArray>() else null
        } catch (_: Exception) {
            null
        } finally {
            client.close()
        }
    }

    override suspend fun listFiles(path: String): List<RemoteFileNetwork> {
        val client = createClient()
        return try {
            val base = baseUrl()
            client.get("$base/api/files") {
                parameter("path", path)
            }.body()
        } catch (_: Exception) {
            emptyList()
        } finally {
            client.close()
        }
    }

    override suspend fun deleteItem(path: String, isDirectory: Boolean): Boolean {
        val client = createClient()
        return try {
            val base = baseUrl()
            val response = client.submitForm(
                url = "$base/delete",
                formParameters = parameters {
                    append("path", path)
                    // NOTE: The device firmware distinguishes between "folder" and "file"
                    // deletion — directories require recursive removal on the SD card.
                    append("type", if (isDirectory) "folder" else "file")
                },
            )
            response.status.isSuccess()
        } catch (_: Exception) {
            false
        } finally {
            client.close()
        }
    }

    override suspend fun renameItem(path: String, newName: String): Boolean {
        val client = createClient()
        return try {
            val base = baseUrl()
            val response = client.submitForm(
                url = "$base/rename",
                formParameters = parameters {
                    append("path", path)
                    append("name", newName)
                },
            )
            response.status.isSuccess()
        } catch (_: Exception) {
            false
        } finally {
            client.close()
        }
    }

    override suspend fun moveItem(sourcePath: String, destPath: String): Boolean {
        val client = createClient()
        return try {
            val base = baseUrl()
            val response = client.submitForm(
                url = "$base/move",
                formParameters = parameters {
                    append("path", sourcePath)
                    append("dest", destPath)
                },
            )
            response.status.isSuccess()
        } catch (_: Exception) {
            false
        } finally {
            client.close()
        }
    }

    override suspend fun createFolder(name: String, parentPath: String): Boolean {
        val client = createClient()
        return try {
            val base = baseUrl()
            val response = client.submitForm(
                url = "$base/mkdir",
                formParameters = parameters {
                    append("name", name)
                    append("path", parentPath)
                },
            )
            response.status.isSuccess()
        } catch (_: Exception) {
            false
        } finally {
            client.close()
        }
    }

    override suspend fun getStatus(): DeviceStatusNetwork? {
        val client = createClient()
        return try {
            val base = baseUrl()
            client.get("$base/api/status").body()
        } catch (_: Exception) {
            null
        } finally {
            client.close()
        }
    }

    override suspend fun verifyDeviceAtIp(ip: String): Boolean {
        val client = createClient()
        return try {
            val response = client.get("http://$ip/api/status")
            response.status.isSuccess()
        } catch (_: Exception) {
            false
        } finally {
            client.close()
        }
    }

    override suspend fun getSettings(): List<SettingItemNetwork> {
        val client = createClient()
        return try {
            val base = baseUrl()
            client.get("$base/api/settings").body()
        } catch (_: Exception) {
            emptyList()
        } finally {
            client.close()
        }
    }

    override suspend fun updateSettings(settings: Map<String, Int>): Boolean {
        val client = createClient()
        return try {
            val base = baseUrl()
            val jsonBody = Json.encodeToString(settings)
            val response = client.post("$base/api/settings") {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
            }
            response.status.isSuccess()
        } catch (_: Exception) {
            false
        } finally {
            client.close()
        }
    }
}
