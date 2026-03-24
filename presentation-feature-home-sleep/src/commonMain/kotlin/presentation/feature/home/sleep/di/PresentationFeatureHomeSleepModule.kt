package presentation.feature.home.sleep.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.sleep.source.sleep.HomeSleepViewModel

public val presentationFeatureHomeSleepModule: Module = module {
    viewModelOf(::HomeSleepViewModel)
}
