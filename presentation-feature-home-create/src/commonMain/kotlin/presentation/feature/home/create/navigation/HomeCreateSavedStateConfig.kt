package presentation.feature.home.create.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * [SavedStateConfiguration] for the Home-Create navigation sub-graph.
 *
 * Registers polymorphic serializers for every [HomeCreateDestination]
 * variant so that the Navigation 3 back-stack can be saved and restored
 * across configuration changes and process death.
 *
 * @see HomeCreateDestination
 * @see HomeCreateTab
 */
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
