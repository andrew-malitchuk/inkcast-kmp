package data.preference.api.source.model

import data.core.source.resource.Resource

/**
 * Persisted preference holding the IP address of the connected reader device.
 *
 * @property ip Device IP address string (e.g., `"192.168.4.1"`), or `null` if no device is configured.
 */
public data class DeviceIpPreference(
    val ip: String? = null,
) : Resource
