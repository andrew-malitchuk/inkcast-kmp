package presentation.feature.home.sleep.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
public sealed class HomeSleepDestination : NavKey {
    @Serializable
    public data object Root : HomeSleepDestination()
}
