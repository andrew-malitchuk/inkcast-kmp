package presentation.feature.about.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.about.source.about.AboutViewModel

/**
 * Koin DI module for the About feature.
 *
 * Provides [AboutViewModel] as a scoped ViewModel binding.
 *
 * @see AboutViewModel
 */
public val presentationFeatureAboutModule: Module = module {
    viewModelOf(::AboutViewModel)
}
