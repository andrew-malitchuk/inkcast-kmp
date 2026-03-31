package presentation.feature.home.sleep.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation destinations for the home sleep feature.
 *
 * Defines the set of serializable [NavKey] routes used within the sleep tab
 * of the home screen.
 */
@Serializable
public sealed class HomeSleepDestination : NavKey {
    /**
     * Root destination that displays the sleep screen editor.
     */
    @Serializable
    public data object Root : HomeSleepDestination()
}
