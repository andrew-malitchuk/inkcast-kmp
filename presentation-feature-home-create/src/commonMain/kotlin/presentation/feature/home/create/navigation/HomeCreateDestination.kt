package presentation.feature.home.create.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation destinations for the Home-Create sub-graph.
 *
 * Each sealed variant is a [Serializable] [NavKey] used by the Navigation 3
 * back-stack inside [HomeCreateTab].
 *
 * @see HomeCreateTab
 * @see homeCreateSavedStateConfig
 */
@Serializable
public sealed class HomeCreateDestination : NavKey {
    /**
     * Root destination that hosts the EPUB creation screen.
     *
     * This is the initial and currently only destination in the Create tab
     * navigation graph.
     */
    @Serializable
    public data object Root : HomeCreateDestination()
}
