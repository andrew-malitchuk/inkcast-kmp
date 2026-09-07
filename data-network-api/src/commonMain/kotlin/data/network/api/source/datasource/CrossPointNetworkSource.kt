package data.network.api.source.datasource

import data.network.api.source.model.DeviceStatusNetwork
import data.network.api.source.model.RemoteFileNetwork
import data.network.api.source.model.SettingItemNetwork

/**
 * Contract for CrossPoint device REST API operations over HTTP.
 *
 * Exposes file management, device status, and settings endpoints
 * on the connected ESP32 device. All operations are suspending and
 * safe to call from any coroutine context — implementations handle
 * dispatcher switching internally.
 *
 * @see data.network.impl.source.datasource.CrossPointNetworkSourceImpl
 */
public interface CrossPointNetworkSource {

    /**
     * Downloads a file from the device filesystem.
     *
     * Endpoint: `GET /download?path={path}`
     *
     * @param path Absolute path to the file on the device (e.g., `/books/novel.epub`).
     * @return Raw file bytes, or `null` if the download failed or the file does not exist.
     */
    public suspend fun downloadFile(path: String): ByteArray?

    /**
     * Lists files and directories at the specified path.
     *
     * Endpoint: `GET /api/files?path={path}`
     *
     * @param path Directory path on the device. Defaults to root `/`.
     * @return List of [RemoteFileNetwork] entries, or an empty list on failure.
     */
    public suspend fun listFiles(path: String = "/"): List<RemoteFileNetwork>

    /**
     * Deletes a file or directory on the device.
     *
     * Endpoint: `POST /delete` (form-encoded: `path`, `type`)
     *
     * @param path Absolute path to the target item.
     * @param isDirectory `true` to delete a directory, `false` for a file.
     * @return `true` if the server confirmed successful deletion.
     */
    public suspend fun deleteItem(path: String, isDirectory: Boolean = false): Boolean

    /**
     * Renames a file or directory on the device.
     *
     * Endpoint: `POST /rename` (form-encoded: `path`, `name`)
     *
     * @param path Current absolute path of the item to rename.
     * @param newName New name (not a full path) for the item.
     * @return `true` if the server confirmed successful rename.
     */
    public suspend fun renameItem(path: String, newName: String): Boolean

    /**
     * Moves a file or directory to a new location on the device.
     *
     * Endpoint: `POST /move` (form-encoded: `path`, `dest`)
     *
     * @param sourcePath Current absolute path of the item.
     * @param destPath Destination absolute path.
     * @return `true` if the server confirmed successful move.
     */
    public suspend fun moveItem(sourcePath: String, destPath: String): Boolean

    /**
     * Creates a new folder on the device filesystem.
     *
     * Endpoint: `POST /mkdir` (form-encoded: `name`, `path`)
     *
     * @param name Folder name to create.
     * @param parentPath Parent directory path. Defaults to root `/`.
     * @return `true` if the server confirmed successful creation.
     */
    public suspend fun createFolder(name: String, parentPath: String = "/"): Boolean

    /**
     * Retrieves the current device hardware and firmware status.
     *
     * Endpoint: `GET /api/status`
     *
     * @return [DeviceStatusNetwork] with the device state, or `null` on failure.
     */
    public suspend fun getStatus(): DeviceStatusNetwork?

    /**
     * Verifies that a device at the given IP is reachable by hitting its status endpoint.
     *
     * Unlike [getStatus], this method does **not** read the IP from persisted preferences —
     * the caller supplies the target address directly. Useful for connection verification
     * before the IP has been saved.
     *
     * Endpoint: `GET http://{ip}/api/status`
     *
     * @param ip Target device IP address (e.g., `"192.168.4.1"`).
     * @return `true` if the device responds with a valid status, `false` otherwise.
     */
    public suspend fun verifyDeviceAtIp(ip: String): Boolean

    /**
     * Retrieves the full list of device configuration settings.
     *
     * Endpoint: `GET /api/settings`
     *
     * @return List of [SettingItemNetwork] entries, or an empty list on failure.
     */
    public suspend fun getSettings(): List<SettingItemNetwork>

    /**
     * Applies updated settings to the device.
     *
     * Endpoint: `POST /api/settings` (JSON body: `Map<String, Int>`)
     *
     * @param settings Map of setting keys to their new integer values.
     * @return `true` if the server confirmed successful update.
     */
    public suspend fun updateSettings(settings: Map<String, Int>): Boolean

    /**
     * Probes the CrossPet-exclusive `/api/opds` endpoint to identify the firmware variant.
     *
     * CrossPet exposes `/api/opds` for its built-in OPDS server management.
     * CrossPoint does not have this endpoint and returns 404.
     *
     * Endpoint: `GET /api/opds`
     *
     * @return `true` if the device responds with a success status (CrossPet),
     *         `false` if the endpoint is absent or the request fails (CrossPoint / unknown).
     */
    public suspend fun probeOpdsEndpoint(): Boolean
}
