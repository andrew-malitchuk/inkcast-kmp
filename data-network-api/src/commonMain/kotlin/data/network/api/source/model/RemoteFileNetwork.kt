package data.network.api.source.model

import data.core.source.resource.Resource
import kotlinx.serialization.Serializable

/**
 * Represents a remote file or directory entry returned by the device.
 *
 * Maps to items in the `/api/files` endpoint response array.
 * All fields are nullable to tolerate partial or malformed server responses.
 *
 * JSON example:
 * ```json
 * {
 *     "name": "book.epub",
 *     "size": 1048576,
 *     "isDirectory": false,
 *     "isEpub": true
 * }
 * ```
 *
 * @property name File or directory name, `null` if omitted by the server.
 * @property size File size in bytes, `null` if omitted by the server.
 * @property isDirectory Whether this entry is a directory, `null` if omitted by the server.
 * @property isEpub Whether this entry is an EPUB file, `null` if omitted by the server.
 * @see CrossPointNetworkSource
 */
@Serializable
public data class RemoteFileNetwork(
    val name: String? = null,
    val size: Long? = null,
    val isDirectory: Boolean? = null,
    val isEpub: Boolean? = null,
) : Resource
