package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.core.source.model.OnboardingModel
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.configuration.SetOnboardingStatusUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [SetOnboardingStatusUseCase].
 *
 * Persists the onboarding completion status via [ConfigureRepository].
 *
 * @property configureRepository Repository used for writing onboarding status.
 */
internal class SetOnboardingStatusUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : SetOnboardingStatusUseCase {

    /**
     * @param value `true` if onboarding is finished, `false` otherwise.
     * @return An [Optional] indicating success or failure.
     * @see SetOnboardingStatusUseCase.invoke
     */
    override suspend fun invoke(value: Boolean): Optional = resultLauncher(
        errorMapper = Failure.Technical::Preference,
    ) {
        configureRepository.setOnboarding(OnboardingModel(isCompleted = value))
    }
}
