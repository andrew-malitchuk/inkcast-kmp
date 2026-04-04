package data.network.api.source.datasource

/**
 * Contract for uploading files to the device via WebSocket binary protocol.
 *
 * Designed for transferring EPUB files to the connected ESP32 device
 * using a chunked binary stream over a WebSocket connection on port 81.
 *
 * @see data.network.impl.source.datasource.UploadNetworkSourceImpl
 */
public interface UploadNetworkSource {

    /**
     * Uploads an EPUB file to the device over a WebSocket connection.
     *
     * Protocol sequence:
     * 1. Opens WebSocket to `ws://{deviceIp}:81/`.
     * 2. Sends `START:{fileName}:{fileSize}:{remotePath}` text frame.
     * 3. Waits up to 5 seconds for `READY` text frame from the device.
     * 4. Streams binary data in 2048-byte chunks with 5 ms inter-chunk delay.
     * 5. Waits for `DONE` text frame confirming successful write.
     *
     * The device IP is resolved internally via [data.network.api.core.DeviceAddressProvider].
     *
     * @param fileName Name for the file on the device. Spaces are replaced with underscores.
     * @param fileBytes Raw EPUB binary content to upload.
     * @param onProgress Callback invoked on the caller's coroutine context with upload percentage (0–100).
     *   Will not throw — exceptions in this lambda are the caller's responsibility.
     * @param remotePath Target directory on the device. Defaults to `/`.
     * @return `true` if the device confirmed successful receipt with `DONE`, `false` on any error or timeout.
     */
    public suspend fun uploadEpubToDevice(
        fileName: String,
        fileBytes: ByteArray,
        onProgress: (Int) -> Unit,
        remotePath: String = "/",
    ): Boolean
}
