package domain.usecase.impl.source.usecase.configuration

import domain.repository.api.source.repository.ConfigureRepository
import domain.usecase.api.source.usecase.configuration.ObserveApplicationLanguageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Default implementation of [ObserveApplicationLanguageUseCase].
 *
 * Observes real-time language changes from [ConfigureRepository] and wraps each
 * emission in a [Result].
 *
 * @property configureRepository Repository used for observing language configuration.
 */
internal class ObserveApplicationLanguageUseCaseImpl(
    private val configureRepository: ConfigureRepository,
) : ObserveApplicationLanguageUseCase {

    /**
     * @return A [Flow] emitting the current language code wrapped in [Result] whenever it changes.
     * @see ObserveApplicationLanguageUseCase.invoke
     */
    override fun invoke(): Flow<Result<String?>> =
        configureRepository.observeApplicationLanguage().map { Result.success(it) }
}
