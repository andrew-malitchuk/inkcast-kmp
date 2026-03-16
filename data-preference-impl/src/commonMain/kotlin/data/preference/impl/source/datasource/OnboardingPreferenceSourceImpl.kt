package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.OnboardingPreferenceSource
import data.preference.api.source.model.OnboardingPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class OnboardingPreferenceSourceImpl(
    private val settings: Settings,
) : OnboardingPreferenceSource {

    private val _flow = MutableStateFlow(readFromSettings())

    private fun readFromSettings(): OnboardingPreference {
        return OnboardingPreference(
            isCompleted = settings.getBoolean(KEY_ONBOARDING_COMPLETED, false),
        )
    }

    override suspend fun getData(): OnboardingPreference = readFromSettings()

    override suspend fun setData(data: OnboardingPreference) {
        settings[KEY_ONBOARDING_COMPLETED] = data.isCompleted
        _flow.value = data
    }

    override fun observeData(): Flow<OnboardingPreference> = _flow.asStateFlow()

    private companion object {
        const val KEY_ONBOARDING_COMPLETED = "pref_onboarding_completed"
    }
}
