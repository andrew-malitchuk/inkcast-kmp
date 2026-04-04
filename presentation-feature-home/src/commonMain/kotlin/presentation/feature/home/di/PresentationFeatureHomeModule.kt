package presentation.feature.home.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.source.home.HomeViewModel

/**
 * Koin module for the home feature.
 *
 * Registers [HomeViewModel] with automatic constructor injection so it can
 * be resolved by `koinViewModel()` inside [presentation.feature.home.source.home.HomeScreen].
 */
public val presentationFeatureHomeModule: Module = module {
    viewModelOf(::HomeViewModel)
}
