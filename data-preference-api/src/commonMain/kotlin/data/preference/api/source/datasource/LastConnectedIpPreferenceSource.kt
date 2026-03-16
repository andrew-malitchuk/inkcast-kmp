package data.preference.api.source.datasource

import data.preference.api.core.PreferenceSource
import data.preference.api.source.model.LastConnectedIpPreference

/**
 * Preference data source for the last successfully connected device IP address.
 *
 * @see LastConnectedIpPreference
 */
public interface LastConnectedIpPreferenceSource : PreferenceSource<LastConnectedIpPreference>
