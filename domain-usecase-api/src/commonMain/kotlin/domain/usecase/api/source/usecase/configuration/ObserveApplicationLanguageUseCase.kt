package domain.usecase.api.source.usecase.configuration

import kotlinx.coroutines.flow.Flow

/**
 * Use case for observing real-time changes to the application language.
 */
public interface ObserveApplicationLanguageUseCase {
    /**
     * @return A [Flow] emitting the current language code whenever it changes.
     */
    public operator fun invoke(): Flow<Result<String?>>
}
