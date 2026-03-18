package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.configuration.SetApplicationLanguageUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [SetApplicationLanguageUseCase].
 *
 * Persists the application locale code via [ConfigureRepository].
 *
 * @property configureRepository Repository used for writing language configuration.
 */
internal class SetApplicationLanguageUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : SetApplicationLanguageUseCase {

    /**
     * @param localeCode The ISO language code (e.g., "uk", "en").
     * @return An [Optional] indicating success or failure.
     * @see SetApplicationLanguageUseCase.invoke
     */
    override suspend fun invoke(localeCode: String): Optional = resultLauncher(
        errorMapper = Failure.Technical::Preference,
    ) {
        configureRepository.setApplicationLanguage(localeCode)
    }
}
