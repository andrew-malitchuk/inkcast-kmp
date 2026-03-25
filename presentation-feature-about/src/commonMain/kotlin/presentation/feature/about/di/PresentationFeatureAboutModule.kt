package presentation.feature.about.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.about.source.about.AboutViewModel

public val presentationFeatureAboutModule: Module = module {
    viewModelOf(::AboutViewModel)
}
