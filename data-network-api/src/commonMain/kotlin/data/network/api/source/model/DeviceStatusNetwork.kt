package data.network.api.source.model

import data.core.source.resource.Resource
import kotlinx.serialization.Serializable

/**
 * Represents the current hardware and firmware status of the connected device.
 *
 * Maps to the `/api/status` endpoint response.
 * All fields are nullable to tolerate partial or malformed server responses.
 *
 * JSON example:
 * ```json
 * {
 *     "version": "1.2.0",
 *     "ip": "192.168.4.1",
 *     "mode": "AP",
 *     "rssi": -45,
 *     "freeHeap": 128000,
 *     "uptime": 3600
 * }
 * ```
 *
 * @property version Firmware version string, `null` if omitted by the server.
 * @property ip Device IP address on the local network, `null` if omitted by the server.
 * @property mode Current operating mode (e.g., "AP", "STA"), `null` if omitted by the server.
 * @property rssi Wi-Fi signal strength in dBm (negative value), `null` if omitted by the server.
 * @property freeHeap Available heap memory in bytes, `null` if omitted by the server.
 * @property uptime Device uptime in seconds since last reboot, `null` if omitted by the server.
 * @see CrossPointNetworkSource.getStatus
 */
@Serializable
public data class DeviceStatusNetwork(
    val version: String? = null,
    val ip: String? = null,
    val mode: String? = null,
    val rssi: Int? = null,
    val freeHeap: Long? = null,
    val uptime: Long? = null,
) : Resource
