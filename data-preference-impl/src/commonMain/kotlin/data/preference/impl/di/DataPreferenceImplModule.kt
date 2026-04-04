package data.preference.impl.di

import data.preference.api.source.datasource.DeviceIpPreferenceSource
import data.preference.api.source.datasource.LastConnectedIpPreferenceSource
import data.preference.api.source.datasource.LanguagePreferenceSource
import data.preference.api.source.datasource.OnboardingPreferenceSource
import data.preference.api.source.datasource.ThemePreferenceSource
import data.preference.impl.core.provideSettings
import data.preference.impl.source.datasource.DeviceIpPreferenceSourceImpl
import data.preference.impl.source.datasource.LastConnectedIpPreferenceSourceImpl
import data.preference.impl.source.datasource.LanguagePreferenceSourceImpl
import data.preference.impl.source.datasource.OnboardingPreferenceSourceImpl
import data.preference.impl.source.datasource.ThemePreferenceSourceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin dependency injection module for the preference data layer.
 *
 * Registers the following bindings:
 * - [com.russhwolf.settings.Settings] — **singleton** platform-specific settings store.
 * - [ThemePreferenceSource] — **singleton** for theme preferences.
 * - [OnboardingPreferenceSource] — **singleton** for onboarding state.
 * - [LanguagePreferenceSource] — **singleton** for language preferences.
 * - [DeviceIpPreferenceSource] — **singleton** for device IP address.
 * - [LastConnectedIpPreferenceSource] — **singleton** for last connected IP.
 *
 * @see data.network.impl.di.dataNetworkImplModule
 */
public val dataPreferenceImplModule: Module = module {
    provideSettings()
    singleOf(::ThemePreferenceSourceImpl) bind ThemePreferenceSource::class
    singleOf(::OnboardingPreferenceSourceImpl) bind OnboardingPreferenceSource::class
    singleOf(::LanguagePreferenceSourceImpl) bind LanguagePreferenceSource::class
    singleOf(::DeviceIpPreferenceSourceImpl) bind DeviceIpPreferenceSource::class
    singleOf(::LastConnectedIpPreferenceSourceImpl) bind LastConnectedIpPreferenceSource::class
}
