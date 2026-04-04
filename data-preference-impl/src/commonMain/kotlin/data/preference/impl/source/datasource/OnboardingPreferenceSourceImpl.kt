package data.preference.impl.source.datasource

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.preference.api.source.datasource.OnboardingPreferenceSource
import data.preference.api.source.model.OnboardingPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Default implementation of [OnboardingPreferenceSource].
 *
 * Persists the onboarding completion flag via [Settings] and exposes
 * a [MutableStateFlow] for reactive observation.
 *
 * @property settings Platform settings store injected by Koin.
 * @see OnboardingPreferenceSource
 */
internal class OnboardingPreferenceSourceImpl(
    private val settings: Settings,
) : OnboardingPreferenceSource {

    /** In-memory state flow initialised with the persisted value on construction. */
    private val _flow = MutableStateFlow(readFromSettings())

    /**
     * Reads the onboarding completion flag from the platform [Settings].
     *
     * Defaults to `false` (onboarding not completed) when no value has been persisted.
     *
     * @return An [OnboardingPreference] reflecting the stored or default state.
     */
    private fun readFromSettings(): OnboardingPreference {
        return OnboardingPreference(
            isCompleted = settings.getBoolean(KEY_ONBOARDING_COMPLETED, false),
        )
    }

    /**
     * Retrieves the current onboarding preference from persistent storage.
     *
     * @return The latest [OnboardingPreference].
     */
    override suspend fun getData(): OnboardingPreference = readFromSettings()

    /**
     * Persists the given [data] and notifies observers.
     *
     * @param data The [OnboardingPreference] to persist.
     */
    override suspend fun setData(data: OnboardingPreference) {
        settings[KEY_ONBOARDING_COMPLETED] = data.isCompleted
        _flow.value = data
    }

    /**
     * Observes the onboarding preference as a reactive [Flow].
     *
     * @return A read-only [Flow] emitting [OnboardingPreference] on every change.
     */
    override fun observeData(): Flow<OnboardingPreference> = _flow.asStateFlow()

    private companion object {
        /** Settings key under which the onboarding completion flag is stored. */
        const val KEY_ONBOARDING_COMPLETED = "pref_onboarding_completed"
    }
}
