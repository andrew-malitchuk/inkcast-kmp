package presentation.feature.onboarding.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.onboarding.source.onboarding.OnboardingViewModel

/**
 * Koin module for the Onboarding feature.
 *
 * Registers [OnboardingViewModel] so it can be injected via `koinViewModel()`.
 */
public val presentationFeatureOnboardingModule: Module = module {
    // Provides OnboardingViewModel with its SetOnboardingStatusUseCase dependency
    viewModelOf(::OnboardingViewModel)
}
