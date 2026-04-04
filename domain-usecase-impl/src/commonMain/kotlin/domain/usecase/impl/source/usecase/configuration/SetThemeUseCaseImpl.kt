package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.core.source.model.ThemeModel
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.core.monnad.Optional
import domain.usecase.api.source.usecase.configuration.SetThemeUseCase
import domain.usecase.impl.core.resultLauncher

/**
 * Default implementation of [SetThemeUseCase].
 *
 * Persists the provided [ThemeModel] via [ConfigureRepository].
 *
 * @property configureRepository Repository used for writing theme configuration.
 */
internal class SetThemeUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : SetThemeUseCase {

    /**
     * @param value The new [ThemeModel] to apply.
     * @return An [Optional] indicating success or failure.
     * @see SetThemeUseCase.invoke
     */
    override suspend fun invoke(value: ThemeModel): Optional = resultLauncher(
        errorMapper = Failure.Technical::Preference,
    ) {
        configureRepository.setTheme(value)
    }
}
