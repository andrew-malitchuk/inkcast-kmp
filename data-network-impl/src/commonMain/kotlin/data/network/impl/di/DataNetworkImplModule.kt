package data.network.impl.di

import data.network.api.core.DeviceAddressProvider
import data.network.api.source.datasource.CrossPointNetworkSource
import data.network.api.source.datasource.DeviceDiscoverySource
import data.network.api.source.datasource.LinkProcessingNetworkSource
import data.network.api.source.datasource.UploadNetworkSource
import data.network.impl.core.DeviceAddressProviderImpl
import data.network.impl.source.datasource.CrossPointNetworkSourceImpl
import data.network.impl.source.datasource.DeviceDiscoverySourceImpl
import data.network.impl.source.datasource.LinkProcessingNetworkSourceImpl
import data.network.impl.source.datasource.UploadNetworkSourceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin dependency injection module for the network data layer.
 *
 * Registers the following bindings:
 * - [DeviceAddressProvider] — **singleton** reading device IP from persisted preferences.
 * - [CrossPointNetworkSource] — **singleton** resolving IP dynamically via [DeviceAddressProvider].
 * - [UploadNetworkSource] — **singleton** for WebSocket file uploads.
 * - [LinkProcessingNetworkSource] — **singleton** for article download and EPUB generation.
 * - [DeviceDiscoverySource] — **singleton** for UDP broadcast device discovery.
 *
 * @see data.preference.impl.di.DataPreferenceImplModule
 */
public val dataNetworkImplModule: Module = module {
    singleOf(::DeviceAddressProviderImpl) bind DeviceAddressProvider::class
    singleOf(::CrossPointNetworkSourceImpl) bind CrossPointNetworkSource::class
    singleOf(::UploadNetworkSourceImpl) bind UploadNetworkSource::class
    singleOf(::LinkProcessingNetworkSourceImpl) bind LinkProcessingNetworkSource::class
    singleOf(::DeviceDiscoverySourceImpl) bind DeviceDiscoverySource::class
}
