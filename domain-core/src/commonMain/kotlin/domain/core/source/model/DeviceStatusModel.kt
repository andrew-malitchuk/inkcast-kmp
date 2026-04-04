package domain.core.source.model

import domain.core.source.model.base.Model

/**
 * Represents the current hardware and firmware status of the connected device.
 *
 * @property version Firmware version string.
 * @property ip Device IP address on the local network.
 * @property mode Current operating mode (e.g., "AP", "STA").
 * @property rssi Wi-Fi signal strength in dBm.
 * @property freeHeap Available heap memory in bytes.
 * @property uptime Device uptime in seconds since last reboot.
 * @see domain.repository.api.source.repository.ReaderRepository.getDeviceStatus
 */
public data class DeviceStatusModel(
    val version: String?,
    val ip: String?,
    val mode: String?,
    val rssi: Int?,
    val freeHeap: Long?,
    val uptime: Long?,
) : Model
