package data.repository.impl.source

import data.preference.api.source.datasource.LanguagePreferenceSource
import data.preference.api.source.datasource.OnboardingPreferenceSource
import data.preference.api.source.datasource.ThemePreferenceSource
import data.preference.api.source.model.OnboardingPreference
import data.preference.api.source.model.ThemePreference
import data.repository.impl.core.mapper.LanguagePreferenceMapper
import data.repository.impl.core.mapper.OnboardingPreferenceMapper
import data.repository.impl.core.mapper.ThemePreferenceMapper
import domain.core.source.model.OnboardingModel
import domain.core.source.model.ThemeModel
import domain.repository.api.source.repository.ConfigureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map

/**
 * Default implementation of [ConfigureRepository].
 *
 * Delegates theme, onboarding, and language persistence to the corresponding
 * [PreferenceSource] instances and exposes an in-memory [MutableSharedFlow]
 * for move-detector motion events.
 *
 * @property themePreferenceSource Data source for theme preferences.
 * @property onboardingPreferenceSource Data source for onboarding preferences.
 * @property languagePreferenceSource Data source for language preferences.
 */
internal class ConfigureRepositoryImpl(
    private val themePreferenceSource: ThemePreferenceSource,
    private val onboardingPreferenceSource: OnboardingPreferenceSource,
    private val languagePreferenceSource: LanguagePreferenceSource,
) : ConfigureRepository {

    /**
     * In-memory channel used to broadcast move-detector motion events.
     *
     * Intentionally has no replay buffer — late subscribers should only receive
     * events that occur after they start collecting. Missed events are acceptable
     * because motion detection is a real-time signal, not a persistent state.
     */
    private val moveDetectorFlow = MutableSharedFlow<Unit>()

    /** @see ConfigureRepository.getTheme */
    override suspend fun getTheme(): ThemeModel? =
        ThemePreferenceMapper.toModel.map(themePreferenceSource.getData())

    /** @see ConfigureRepository.setTheme */
    override suspend fun setTheme(theme: ThemeModel?) {
        val preference = theme?.let { ThemePreferenceMapper.toResource.map(it) } ?: ThemePreference()
        themePreferenceSource.setData(preference)
    }

    /** @see ConfigureRepository.observeTheme */
    override fun observeTheme(): Flow<ThemeModel?> =
        themePreferenceSource.observeData().map { ThemePreferenceMapper.toModel.map(it) }

    /** @see ConfigureRepository.getOnboarding */
    override suspend fun getOnboarding(): OnboardingModel? =
        OnboardingPreferenceMapper.toModel.map(onboardingPreferenceSource.getData())

    /** @see ConfigureRepository.setOnboarding */
    override suspend fun setOnboarding(status: OnboardingModel?) {
        val preference = status?.let { OnboardingPreferenceMapper.toResource.map(it) } ?: OnboardingPreference()
        onboardingPreferenceSource.setData(preference)
    }

    /** @see ConfigureRepository.observeOnboarding */
    override fun observeOnboarding(): Flow<OnboardingModel?> =
        onboardingPreferenceSource.observeData().map { OnboardingPreferenceMapper.toModel.map(it) }

    /** @see ConfigureRepository.observeMoveDetectorMotion */
    override fun observeMoveDetectorMotion(): Flow<Unit> =
        moveDetectorFlow.asSharedFlow()

    /** @see ConfigureRepository.emitMoveDetectorMotion */
    override suspend fun emitMoveDetectorMotion() {
        moveDetectorFlow.emit(Unit)
    }

    /** @see ConfigureRepository.setApplicationLanguage */
    override suspend fun setApplicationLanguage(localeCode: String) {
        languagePreferenceSource.setData(LanguagePreferenceMapper.toResource.map(localeCode))
    }

    /** @see ConfigureRepository.getApplicationLanguage */
    override suspend fun getApplicationLanguage(): String? =
        LanguagePreferenceMapper.toModel.map(languagePreferenceSource.getData())

    /** @see ConfigureRepository.observeApplicationLanguage */
    override fun observeApplicationLanguage(): Flow<String?> =
        languagePreferenceSource.observeData().map { LanguagePreferenceMapper.toModel.map(it) }
}
