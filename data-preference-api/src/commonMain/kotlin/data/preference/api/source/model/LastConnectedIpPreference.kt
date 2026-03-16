package data.preference.api.source.model

import data.core.source.resource.Resource

/**
 * Persisted preference holding the last IP address the user successfully connected to.
 *
 * Used to pre-fill the manual IP input on the connection screen so the user
 * does not need to re-enter the address after a reconnection.
 *
 * @property ip Last connected device IP address string (e.g., `"192.168.4.1"`),
 *   or `null` if no successful connection has been made yet.
 */
public data class LastConnectedIpPreference(
    val ip: String? = null,
) : Resource
