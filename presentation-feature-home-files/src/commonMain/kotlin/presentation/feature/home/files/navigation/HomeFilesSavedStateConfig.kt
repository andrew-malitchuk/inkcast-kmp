package presentation.feature.home.files.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * Saved-state serialization configuration for the home files navigation back stack.
 *
 * Registers polymorphic serializers for all [HomeFilesDestination] subtypes so that
 * the navigation state can be saved and restored across configuration changes.
 *
 * @see HomeFilesDestination
 */
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
