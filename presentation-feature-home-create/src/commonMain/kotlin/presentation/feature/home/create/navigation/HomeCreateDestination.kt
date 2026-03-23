package presentation.feature.home.create.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
public sealed class HomeCreateDestination : NavKey {
    @Serializable
    public data object Root : HomeCreateDestination()
}
