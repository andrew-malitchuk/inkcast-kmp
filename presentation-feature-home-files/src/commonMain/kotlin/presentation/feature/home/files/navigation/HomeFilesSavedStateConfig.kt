package presentation.feature.home.files.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

internal val homeFilesSavedStateConfig: SavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(
                    HomeFilesDestination.Root::class,
                    HomeFilesDestination.Root.serializer(),
                )
            }
        }
    }
