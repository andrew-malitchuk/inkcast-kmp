package presentation.core.navigation.api.source.destination

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
public sealed class Destination : NavKey {
    @Serializable
    public data object Splash : Destination()
    @Serializable
    public data object Onboarding : Destination()
    @Serializable
    public data object Home : Destination()
    @Serializable
    public data object HomeCreate : Destination()
    @Serializable
    public data object HomeDevice : Destination()
    @Serializable
    public data object HomeFiles : Destination()
    @Serializable
    public data object HomeSleep : Destination()
    @Serializable
    public data object Settings : Destination()
    @Serializable
    public data class Connection(val isInitialSetup: Boolean = false) : Destination()
    @Serializable
    public data object About : Destination()
}
