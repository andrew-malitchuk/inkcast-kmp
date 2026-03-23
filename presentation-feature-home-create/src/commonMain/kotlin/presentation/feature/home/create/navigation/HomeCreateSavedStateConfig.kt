package presentation.feature.home.create.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

internal val homeCreateSavedStateConfig: SavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(
                    HomeCreateDestination.Root::class,
                    HomeCreateDestination.Root.serializer(),
                )
            }
        }
    }
