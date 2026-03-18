package domain.usecase.impl.source.usecase.configuration

import domain.core.monad.Failure
import domain.core.source.model.ThemeModel
import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.source.usecase.configuration.ObserveThemeUseCase
import domain.usecase.impl.core.resultLauncher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Default implementation of [ObserveThemeUseCase].
 *
 * Observes real-time theme changes from [ConfigureRepository] and wraps each
 * emission in a [Result].
 *
 * @property configureRepository Repository used for observing theme configuration.
 */
internal class ObserveThemeUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : ObserveThemeUseCase {

    /**
     * @return A [Flow] emitting the current [ThemeModel] wrapped in [Result] whenever it changes.
     * @see ObserveThemeUseCase.invoke
     */
    override fun invoke(): Flow<Result<ThemeModel?>> =
        configureRepository.observeTheme().map { Result.success(it) }
}
