package domain.repository.api.source.repository

import domain.core.source.model.OnboardingModel
import domain.core.source.model.ThemeModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing various system and UI configurations.
 */
public interface ConfigureRepository {
    /** Retrieves the current application theme. */
    public suspend fun getTheme(): ThemeModel?

    /** Updates the application [theme]. */
    public suspend fun setTheme(theme: ThemeModel?)

    /** Observes changes to the application theme. */
    public fun observeTheme(): Flow<ThemeModel?>

    /** Retrieves the onboarding completion status. */
    public suspend fun getOnboarding(): OnboardingModel?

    /** Updates the onboarding [status]. */
    public suspend fun setOnboarding(status: OnboardingModel?)

    /** Observes changes to the onboarding status. */
    public fun observeOnboarding(): Flow<OnboardingModel?>

    /** Observes motion detection events from the move detector. */
    public fun observeMoveDetectorMotion(): Flow<Unit>

    /** Manually triggers a motion detection event. */
    public suspend fun emitMoveDetectorMotion()

    /** Updates the application language using the provided [localeCode] (e.g., "en", "uk"). */
    public suspend fun setApplicationLanguage(localeCode: String)

    /** Retrieves the current application language locale code. */
    public suspend fun getApplicationLanguage(): String?

    /** Observes changes to the application language locale code. */
    public fun observeApplicationLanguage(): Flow<String?>
}
