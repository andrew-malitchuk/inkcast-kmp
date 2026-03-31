package presentation.feature.home.device.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation destinations for the Home-Device sub-graph.
 *
 * Each sealed variant is a [Serializable] [NavKey] used by the Navigation 3
 * back-stack inside [HomeDeviceTab].
 *
 * @see HomeDeviceTab
 * @see homeDeviceSavedStateConfig
 */
@Serializable
public sealed class HomeDeviceDestination : NavKey {
    /**
     * Root destination that hosts the device settings screen.
     *
     * This is the initial and currently only destination in the Device tab
     * navigation graph.
     */
    @Serializable
    public data object Root : HomeDeviceDestination()
}
