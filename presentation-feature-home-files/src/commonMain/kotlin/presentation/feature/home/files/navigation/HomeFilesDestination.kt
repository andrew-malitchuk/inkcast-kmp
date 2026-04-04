package presentation.feature.home.files.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation destinations for the home files feature.
 *
 * Defines the set of serializable [NavKey] routes used within the files tab
 * of the home screen.
 */
@Serializable
public sealed class HomeFilesDestination : NavKey {
    /**
     * Root destination that displays the file manager screen.
     */
    @Serializable
    public data object Root : HomeFilesDestination()
}
