package presentation.feature.onboarding.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.onboarding.source.onboarding.OnboardingViewModel

public val presentationFeatureOnboardingModule: Module = module {
    viewModelOf(::OnboardingViewModel)
}
