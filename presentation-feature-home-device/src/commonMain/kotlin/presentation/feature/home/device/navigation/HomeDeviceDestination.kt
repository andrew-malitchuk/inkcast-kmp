package presentation.feature.home.device.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
public sealed class HomeDeviceDestination : NavKey {
    @Serializable
    public data object Root : HomeDeviceDestination()
}
