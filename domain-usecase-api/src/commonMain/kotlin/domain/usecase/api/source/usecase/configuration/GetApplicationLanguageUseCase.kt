package domain.usecase.api.source.usecase.configuration

/**
 * Use case for retrieving the current application language.
 */
public interface GetApplicationLanguageUseCase {
    /**
     * @return A [Result] containing the language locale code (e.g., "en", "uk").
     */
    public suspend operator fun invoke(): Result<String>
}
