package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.core.source.model.ThemeModel
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.source.usecase.configuration.GetThemeUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [GetThemeUseCase].
 *
 * Retrieves the current application theme from [ConfigureRepository].
 * Throws [Failure.Logic.NotFound] when no theme has been persisted yet.
 *
 * @property configureRepository Repository used for reading theme configuration.
 */
internal class GetThemeUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : GetThemeUseCase {

    /**
     * @return A [Result] containing the current [ThemeModel].
     * @see GetThemeUseCase.invoke
     */
    override suspend fun invoke(): Result<ThemeModel> = resultLauncher(
        errorMapper = Failure.Technical::Preference,
    ) {
        configureRepository.getTheme() ?: throw Failure.Logic.NotFound
    }
}
