package presentation.feature.home.create.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.create.source.create.HomeCreateViewModel

/**
 * Koin module for the home-create feature.
 *
 * Registers [HomeCreateViewModel] with automatic constructor injection so it can
 * be resolved by `koinViewModel()` inside [presentation.feature.home.create.source.create.HomeCreateScreen].
 *
 * @see HomeCreateViewModel
 */
public val presentationFeatureHomeCreateModule: Module = module {
    viewModelOf(::HomeCreateViewModel)
}
