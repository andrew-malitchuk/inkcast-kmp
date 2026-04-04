package presentation.feature.home.device.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/**
 * [SavedStateConfiguration] for the Home-Device navigation sub-graph.
 *
 * Registers polymorphic serializers for every [HomeDeviceDestination]
 * variant so that the Navigation 3 back-stack can be saved and restored
 * across configuration changes and process death.
 *
 * @see HomeDeviceDestination
 * @see HomeDeviceTab
 */
internal val homeDeviceSavedStateConfig: SavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(
                    HomeDeviceDestination.Root::class,
                    HomeDeviceDestination.Root.serializer(),
                )
            }
        }
    }
