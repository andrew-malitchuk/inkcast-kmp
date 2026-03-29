package data.preference.api.source.model

import data.core.source.resource.Resource

/**
 * Persisted preference tracking whether the user has completed the onboarding wizard.
 *
 * @property isCompleted `true` after the user finishes onboarding, `false` by default.
 * @see data.preference.api.source.datasource.OnboardingPreferenceSource
 */
public data class OnboardingPreference(
    val isCompleted: Boolean = false,
) : Resource
