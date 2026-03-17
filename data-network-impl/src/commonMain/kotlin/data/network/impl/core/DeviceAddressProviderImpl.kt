package data.network.impl.core

import data.network.api.core.DeviceAddressProvider
import data.preference.api.source.datasource.DeviceIpPreferenceSource

/**
 * Preference-backed implementation of [DeviceAddressProvider].
 *
 * Reads the device IP from the persisted [DeviceIpPreferenceSource],
 * ensuring all network data sources always use the latest configured address.
 *
 * @property deviceIpPreferenceSource Preference store holding the device IP.
 * @see DeviceAddressProvider
 * @see DeviceIpPreferenceSource
 */
internal class DeviceAddressProviderImpl(
    private val deviceIpPreferenceSource: DeviceIpPreferenceSource,
) : DeviceAddressProvider {

    override suspend fun getDeviceIp(): String? =
        deviceIpPreferenceSource.getData().ip
}
