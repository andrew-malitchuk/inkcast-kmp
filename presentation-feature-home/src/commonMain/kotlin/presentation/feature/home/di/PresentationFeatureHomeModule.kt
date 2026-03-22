package presentation.feature.home.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.source.home.HomeViewModel

public val presentationFeatureHomeModule: Module = module {
    viewModelOf(::HomeViewModel)
}
