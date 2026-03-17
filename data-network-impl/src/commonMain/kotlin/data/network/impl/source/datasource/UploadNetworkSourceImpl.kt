package data.network.impl.source.datasource

import data.network.api.core.DeviceAddressProvider
import data.network.api.source.datasource.UploadNetworkSource
import data.network.impl.core.NetworkProvider
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * Ktor WebSocket–based implementation of [UploadNetworkSource].
 *
 * Implements a chunked binary upload protocol designed for ESP32 devices
 * with limited RAM and SD card write speed. The protocol uses text frames
 * for control messages and binary frames for file data.
 *
 * The device IP is resolved dynamically via [DeviceAddressProvider] on every upload,
 * so the address can change at runtime when the user connects to a different device.
 *
 * @property deviceAddressProvider Provides the current device IP from persisted preferences.
 * @see UploadNetworkSource
 * @see NetworkProvider
 * @see DeviceAddressProvider
 */
internal class UploadNetworkSourceImpl(
    private val deviceAddressProvider: DeviceAddressProvider,
) : UploadNetworkSource {

    override suspend fun uploadEpubToDevice(
        fileName: String,
        fileBytes: ByteArray,
        onProgress: (Int) -> Unit,
        remotePath: String,
    ): Boolean {
        // NOTE: Resolve device IP from persisted preferences at upload time.
        val rawIp = deviceAddressProvider.getDeviceIp()
            ?: throw IllegalStateException("Device IP not configured")
        val cleanIp = rawIp.replace("http://", "").replace("ws://", "").trim()
        if (cleanIp.isEmpty()) throw IllegalStateException("Device IP is empty")

        val client = NetworkProvider.createClient {
            install(WebSockets) {
                // WORKAROUND: ESP32 WebSocket libraries (e.g., ESPAsyncWebServer) do not
                // handle WebSocket ping frames correctly and may close the connection.
                // Disable pings entirely to avoid premature disconnection.
                pingIntervalMillis = -1L
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000L
                // NOTE: 5-minute timeout to accommodate large EPUBs over slow SD card writes.
                requestTimeoutMillis = 300_000L
                socketTimeoutMillis = 300_000L
            }
        }

        // NOTE: 2048 bytes per chunk — smaller than typical WebSocket frames but optimized
        // for ESP32 heap constraints. Larger chunks cause out-of-memory on the device.
        val chunkSize = 2048
        var isSuccess = false

        try {
            // NOTE: ESP32 WebSocket server listens on port 81 (port 80 is HTTP API).
            client.webSocket(host = cleanIp, port = 81, path = "/") {
                val readySignal = CompletableDeferred<Unit>()
                val doneSignal = CompletableDeferred<Boolean>()

                // NOTE: Listener runs in parallel to detect READY/DONE/ERROR signals
                // from the device while we send data in the main coroutine.
                val listenerJob = launch {
                    try {
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                val text = frame.readText()
                                when {
                                    text == "READY" -> readySignal.complete(Unit)
                                    text == "DONE" -> doneSignal.complete(true)
                                    text.startsWith("ERROR") -> {
                                        doneSignal.complete(false)
                                        cancel("Server error: $text")
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        readySignal.completeExceptionally(e)
                        doneSignal.completeExceptionally(e)
                    }
                }

                // NOTE: Spaces in filenames cause issues on the ESP32 FAT filesystem.
                val safeFileName = fileName.replace(" ", "_")
                val startCmd = "START:$safeFileName:${fileBytes.size}:$remotePath"
                send(Frame.Text(startCmd))

                // NOTE: 5-second timeout — if the device is not ready by then,
                // it likely failed to open the file for writing on the SD card.
                try {
                    withTimeout(5_000L) {
                        readySignal.await()
                    }
                } catch (_: kotlinx.coroutines.TimeoutCancellationException) {
                    listenerJob.cancelAndJoin()
                    throw IllegalStateException("Device not ready (timeout)")
                }

                var offset = 0
                while (offset < fileBytes.size) {
                    val end = (offset + chunkSize).coerceAtMost(fileBytes.size)
                    val chunk = fileBytes.copyOfRange(offset, end)
                    send(Frame.Binary(true, chunk))
                    offset = end
                    onProgress((offset.toFloat() / fileBytes.size * 100).toInt())
                    // NOTE: 5 ms delay between chunks gives the ESP32 time to flush
                    // the previous chunk to the SD card before the next one arrives.
                    delay(5)
                }

                isSuccess = doneSignal.await()
                listenerJob.cancelAndJoin()
            }
        } catch (e: IllegalStateException) {
            // Re-throw domain errors (device IP missing, timeout) so they propagate to UseCase.
            throw e
        } catch (_: Exception) {
            throw IllegalStateException("Cannot connect to device at $cleanIp")
        } finally {
            client.close()
        }

        return isSuccess
    }
}
