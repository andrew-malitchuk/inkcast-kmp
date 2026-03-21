package presentation.feature.splash.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.splash.source.splash.SplashViewModel

public val presentationFeatureSplashModule: Module = module {
    viewModelOf(::SplashViewModel)
}
