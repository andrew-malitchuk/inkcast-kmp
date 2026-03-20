package data.preference.api.source.datasource

import data.preference.api.source.datasource.base.PreferenceSource
import data.preference.api.source.model.OnboardingPreference

/**
 * Preference data source for onboarding completion status.
 *
 * @see OnboardingPreference
 */
public interface OnboardingPreferenceSource : PreferenceSource<OnboardingPreference>
