package presentation.feature.home.device.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.device.source.device.HomeDeviceViewModel

/**
 * Koin module for the home-device feature.
 *
 * Registers [HomeDeviceViewModel] with automatic constructor injection
 * of the required use cases (GetDeviceStatus, GetDeviceSettings,
 * UpdateDeviceSettings, GetDeviceIp).
 */
public val presentationFeatureHomeDeviceModule: Module = module {
    viewModelOf(::HomeDeviceViewModel)
}
