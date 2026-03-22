package presentation.feature.connection.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.connection.source.connection.ConnectionViewModel

/**
 * Koin module for the connection feature.
 *
 * Registers [ConnectionViewModel] with automatic constructor injection
 * of the required use cases (GetDeviceIp, SetDeviceIp, GetDeviceStatus).
 */
public val presentationFeatureConnectionModule: Module = module {
    viewModelOf(::ConnectionViewModel)
}
