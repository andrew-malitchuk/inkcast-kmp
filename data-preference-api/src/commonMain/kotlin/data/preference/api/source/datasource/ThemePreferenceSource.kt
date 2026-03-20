package data.preference.api.source.datasource

import data.preference.api.source.datasource.base.PreferenceSource
import data.preference.api.source.model.ThemePreference

/**
 * Preference data source for the selected application theme.
 *
 * @see ThemePreference
 */
public interface ThemePreferenceSource : PreferenceSource<ThemePreference>
