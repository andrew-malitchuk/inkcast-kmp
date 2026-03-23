package presentation.feature.home.create.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.create.source.create.HomeCreateViewModel

public val presentationFeatureHomeCreateModule: Module = module {
    viewModelOf(::HomeCreateViewModel)
}
