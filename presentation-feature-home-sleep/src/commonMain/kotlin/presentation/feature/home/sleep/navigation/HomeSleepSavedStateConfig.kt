package presentation.feature.home.sleep.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

internal val homeSleepSavedStateConfig: SavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(
                    HomeSleepDestination.Root::class,
                    HomeSleepDestination.Root.serializer(),
                )
            }
        }
    }
