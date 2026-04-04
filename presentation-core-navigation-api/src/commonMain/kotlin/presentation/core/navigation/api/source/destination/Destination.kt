package presentation.core.navigation.api.source.destination

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Sealed hierarchy of all navigable screens in the application.
 *
 * Each subclass is [Serializable] to support Navigation 3 saved-state persistence.
 * Register new destinations in [navSavedStateConfiguration] and [NavigationHost].
 *
 * @see AppNavigator
 */
@Serializable
public sealed class Destination : NavKey {
    /** App launch splash screen with animation. */
    @Serializable
    public data object Splash : Destination()

    /** First-time onboarding flow. */
    @Serializable
    public data object Onboarding : Destination()

    /** Main home screen with tabbed navigation (files, create, sleep, device). */
    @Serializable
    public data object Home : Destination()

    /** Home tab: content creation. */
    @Serializable
    public data object HomeCreate : Destination()

    /** Home tab: device status and configuration. */
    @Serializable
    public data object HomeDevice : Destination()

    /** Home tab: file browser for the connected e-reader. */
    @Serializable
    public data object HomeFiles : Destination()

    /** Home tab: sleep/widget screen configuration. */
    @Serializable
    public data object HomeSleep : Destination()

    /** Application settings (theme, language, connection). */
    @Serializable
    public data object Settings : Destination()

    /**
     * Device connection setup screen.
     *
     * @property isInitialSetup When `true`, navigates to Home on success instead of popping back.
     */
    @Serializable
    public data class Connection(val isInitialSetup: Boolean = false) : Destination()

    /** About screen with app information. */
    @Serializable
    public data object About : Destination()
}
