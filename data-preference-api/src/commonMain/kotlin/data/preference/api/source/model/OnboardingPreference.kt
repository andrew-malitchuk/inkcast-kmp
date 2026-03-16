package data.preference.api.source.model

import data.core.source.resource.Resource

public data class OnboardingPreference(
    val isCompleted: Boolean = false,
) : Resource
