package data.preference.api.source.datasource

import data.preference.api.source.datasource.base.PreferenceSource
import data.preference.api.source.model.LanguagePreference

/**
 * Preference data source for the selected application locale.
 *
 * @see LanguagePreference
 */
public interface LanguagePreferenceSource : PreferenceSource<LanguagePreference>
