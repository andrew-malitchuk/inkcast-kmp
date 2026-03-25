package presentation.feature.settings.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.settings.source.settings.SettingsViewModel

/**
 * Koin module for the Settings feature.
 *
 * Registers [SettingsViewModel] so it can be injected via `koinViewModel()`.
 */
public val presentationFeatureSettingsModule: Module = module {
    viewModelOf(::SettingsViewModel)
}
