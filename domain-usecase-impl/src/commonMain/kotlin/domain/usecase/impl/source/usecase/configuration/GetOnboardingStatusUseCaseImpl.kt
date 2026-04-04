package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.source.usecase.configuration.GetOnboardingStatusUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetOnboardingStatusUseCase].
 *
 * Retrieves the onboarding completion flag from [ConfigureRepository].
 * Throws [Failure.Logic.NotFound] when no onboarding status has been persisted yet.
 *
 * @property configureRepository Repository used for reading onboarding status.
 */
internal class GetOnboardingStatusUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : GetOnboardingStatusUseCase {

    /**
     * @return A [Result] containing `true` if onboarding is completed, `false` otherwise.
     * @see GetOnboardingStatusUseCase.invoke
     */
    override suspend fun invoke(): Result<Boolean> = resultLauncher(
        errorMapper = Failure.Technical::Preference,
    ) {
        configureRepository.getOnboarding()?.isCompleted ?: throw Failure.Logic.NotFound
    }
}
