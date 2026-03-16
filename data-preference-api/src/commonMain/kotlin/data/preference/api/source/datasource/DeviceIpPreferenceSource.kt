package data.preference.api.source.datasource

import data.preference.api.core.PreferenceSource
import data.preference.api.source.model.DeviceIpPreference

/**
 * Preference data source for the connected device IP address.
 *
 * @see DeviceIpPreference
 */
public interface DeviceIpPreferenceSource : PreferenceSource<DeviceIpPreference>
