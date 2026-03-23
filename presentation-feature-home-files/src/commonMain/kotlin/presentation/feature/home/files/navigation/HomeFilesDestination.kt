package presentation.feature.home.files.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
public sealed class HomeFilesDestination : NavKey {
    @Serializable
    public data object Root : HomeFilesDestination()
}
