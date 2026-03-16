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

public val dataPreferenceImplModule: Module = module {
    provideSettings()
    singleOf(::ThemePreferenceSourceImpl) bind ThemePreferenceSource::class
    singleOf(::OnboardingPreferenceSourceImpl) bind OnboardingPreferenceSource::class
    singleOf(::LanguagePreferenceSourceImpl) bind LanguagePreferenceSource::class
    singleOf(::DeviceIpPreferenceSourceImpl) bind DeviceIpPreferenceSource::class
    singleOf(::LastConnectedIpPreferenceSourceImpl) bind LastConnectedIpPreferenceSource::class
}
