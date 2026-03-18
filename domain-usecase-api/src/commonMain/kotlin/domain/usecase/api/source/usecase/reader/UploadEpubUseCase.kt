package domain.usecase.api.source.usecase.reader

import domain.usecase.api.core.monnad.Optional

/**
 * Use case for uploading an EPUB file to the device via WebSocket.
 *
 * The device IP is resolved internally by the network layer from persisted preferences.
 */
public interface UploadEpubUseCase {
    /**
     * @param fileName Name for the file on the device.
     * @param fileBytes Raw EPUB binary content.
     * @param onProgress Progress callback invoked with percentage (0–100).
     * @param remotePath Target directory on the device. Defaults to `/`.
     * @return An [Optional] result indicating success or failure.
     */
    public suspend operator fun invoke(
        fileName: String,
        fileBytes: ByteArray,
        onProgress: (Int) -> Unit,
        remotePath: String = "/",
    ): Optional
}
