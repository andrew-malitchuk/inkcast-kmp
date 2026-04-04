package presentation.feature.home.files.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.files.source.files.HomeFilesViewModel

/**
 * Koin dependency injection module for the home files feature.
 *
 * Provides the following bindings:
 * - [HomeFilesViewModel] — file manager screen ViewModel.
 *
 * @see HomeFilesViewModel
 */
public val presentationFeatureHomeFilesModule: Module = module {
    viewModelOf(::HomeFilesViewModel)
}
