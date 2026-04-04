package presentation.feature.home.sleep.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * Saved-state serialization configuration for the home sleep navigation back stack.
 *
 * Registers polymorphic serializers for all [HomeSleepDestination] subtypes so that
 * the navigation state can be saved and restored across configuration changes.
 *
 * @see HomeSleepDestination
 */
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
