package domain.repository.api.source.repository

import domain.core.source.model.DeviceStatusModel
import domain.core.source.model.FirmwareType
import domain.core.source.model.PreparedEpubModel
import domain.core.source.model.RemoteFileModel
import domain.core.source.model.SettingItemModel

/**
 * Repository for reader device operations — file management, device status,
 * settings, file upload, and article-to-EPUB conversion.
 *
 * Aggregates three underlying data sources:
 * - CrossPoint device REST API (files, status, settings).
 * - WebSocket upload protocol.
 * - Internet article download and EPUB generation.
 *
 * @see data.repository.impl.source.ReaderRepositoryImpl
 */
public interface ReaderRepository {

    // region File operations

    /**
     * Downloads a file from the device.
     *
     * @param path Absolute path to the file on the device.
     * @return Raw file bytes, or `null` on failure.
     */
    public suspend fun downloadFile(path: String): ByteArray?

    /**
     * Lists files and directories at the given path on the device.
     *
     * @param path Directory path. Defaults to root `/`.
     * @return List of [RemoteFileModel] entries, or empty list on failure.
     */
    public suspend fun listFiles(path: String = "/"): List<RemoteFileModel>

    /**
     * Deletes a file or directory on the device.
     *
     * @param path Absolute path to the target item.
     * @param isDirectory `true` to delete a directory.
     * @return `true` if deletion succeeded.
     */
    public suspend fun deleteItem(path: String, isDirectory: Boolean = false): Boolean

    /**
     * Renames a file or directory on the device.
     *
     * @param path Current absolute path.
     * @param newName New name for the item.
     * @return `true` if rename succeeded.
     */
    public suspend fun renameItem(path: String, newName: String): Boolean

    /**
     * Moves a file or directory to a new location on the device.
     *
     * @param sourcePath Current absolute path.
     * @param destPath Destination absolute path.
     * @return `true` if move succeeded.
     */
    public suspend fun moveItem(sourcePath: String, destPath: String): Boolean

    /**
     * Creates a new folder on the device.
     *
     * @param name Folder name.
     * @param parentPath Parent directory. Defaults to root `/`.
     * @return `true` if creation succeeded.
     */
    public suspend fun createFolder(name: String, parentPath: String = "/"): Boolean

    // endregion

    // region Device status & settings

    /**
     * Retrieves the current device status.
     *
     * @return [DeviceStatusModel] or `null` on failure.
     */
    public suspend fun getDeviceStatus(): DeviceStatusModel?

    /**
     * Retrieves the device configuration settings.
     *
     * @return List of [SettingItemModel] entries, or empty list on failure.
     */
    public suspend fun getDeviceSettings(): List<SettingItemModel>

    /**
     * Updates device settings.
     *
     * @param settings Map of setting keys to their new integer values.
     * @return `true` if update succeeded.
     */
    public suspend fun updateDeviceSettings(settings: Map<String, Int>): Boolean

    // endregion

    // region Discovery

    /**
     * Detects whether the connected device runs CrossPet or CrossPoint firmware.
     *
     * Probes the CrossPet-exclusive `/api/opds` endpoint:
     * a successful response indicates CrossPet; 404 or any failure indicates CrossPoint.
     *
     * @return [FirmwareType.CrossPet], [FirmwareType.CrossPoint], or [FirmwareType.Unknown] on error.
     */
    public suspend fun detectFirmwareType(): FirmwareType

    /**
     * Broadcasts a UDP discovery packet on the local network and returns
     * the IP addresses of all responding CrossPoint devices.
     *
     * @return List of discovered device IP address strings, or empty list if none found.
     */
    public suspend fun discoverDevices(): List<String>

    /**
     * Verifies that a device at the given IP is reachable via HTTP.
     *
     * Bypasses the persisted IP preference — the caller supplies the
     * target address directly.
     *
     * @param ip Target device IP address.
     * @return `true` if the device responds, `false` otherwise.
     */
    public suspend fun verifyDeviceAtIp(ip: String): Boolean

    // endregion

    // region Device IP

    /**
     * Returns the currently stored device IP address.
     *
     * @return IP address string (e.g., `"192.168.4.1"`), or `null` if not configured.
     */
    public suspend fun getDeviceIp(): String?

    /**
     * Saves a new device IP address, replacing any previously stored value.
     *
     * @param ip IP address string to persist.
     */
    public suspend fun setDeviceIp(ip: String)

    /**
     * Updates the stored device IP address.
     *
     * Behaves identically to [setDeviceIp] — provided as a semantic alias
     * for call sites where the intent is to replace an existing value.
     *
     * @param ip New IP address string to persist.
     */
    public suspend fun updateDeviceIp(ip: String)

    /**
     * Returns the last IP address the user successfully connected to.
     *
     * @return IP address string, or `null` if no successful connection has been made yet.
     */
    public suspend fun getLastConnectedIp(): String?

    /**
     * Persists the IP address of the last successful device connection.
     *
     * @param ip IP address string to store.
     */
    public suspend fun setLastConnectedIp(ip: String)

    // endregion

    // region Upload

    /**
     * Uploads an EPUB file to the device via WebSocket.
     *
     * The device IP is resolved internally via the network layer.
     *
     * @param fileName Name for the file on the device.
     * @param fileBytes Raw EPUB binary content.
     * @param onProgress Progress callback with percentage (0–100).
     * @param remotePath Target directory on the device. Defaults to `/`.
     * @return `true` if upload completed successfully.
     */
    public suspend fun uploadEpub(
        fileName: String,
        fileBytes: ByteArray,
        onProgress: (Int) -> Unit,
        remotePath: String = "/",
    ): Boolean

    // endregion

    // region Link processing

    /**
     * Downloads an article from a URL and converts it to EPUB.
     *
     * @param url Article URL to download and convert.
     * @param onStatus Status callback with human-readable progress messages.
     * @return [PreparedEpubModel] or `null` on failure.
     */
    public suspend fun downloadAndBuildEpub(
        url: String,
        onStatus: (String) -> Unit,
    ): PreparedEpubModel?

    // endregion
}
