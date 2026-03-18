package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.source.usecase.configuration.GetApplicationLanguageUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetApplicationLanguageUseCase].
 *
 * Retrieves the current application language from [ConfigureRepository],
 * falling back to `"en"` when no language has been persisted yet.
 *
 * @property configureRepository Repository used for reading language configuration.
 */
internal class GetApplicationLanguageUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : GetApplicationLanguageUseCase {

    /**
     * @return A [Result] containing the current language locale code.
     * @see GetApplicationLanguageUseCase.invoke
     */
    override suspend fun invoke(): Result<String> = resultLauncher(
        errorMapper = Failure.Technical::Preference,
    ) {
        configureRepository.getApplicationLanguage() ?: DEFAULT_LANGUAGE
    }

    private companion object {
        const val DEFAULT_LANGUAGE = "en"
    }
}
