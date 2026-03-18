package domain.usecase.impl.di

import domain.usecase.api.source.usecase.configuration.GetApplicationLanguageUseCase
import domain.usecase.api.source.usecase.configuration.GetOnboardingStatusUseCase
import domain.usecase.api.source.usecase.configuration.GetThemeUseCase
import domain.usecase.api.source.usecase.configuration.ObserveApplicationLanguageUseCase
import domain.usecase.api.source.usecase.configuration.ObserveThemeUseCase
import domain.usecase.api.source.usecase.configuration.SetApplicationLanguageUseCase
import domain.usecase.api.source.usecase.configuration.SetOnboardingStatusUseCase
import domain.usecase.api.source.usecase.configuration.SetThemeUseCase
import domain.usecase.api.source.usecase.reader.DiscoverDevicesUseCase
import domain.usecase.api.source.usecase.reader.VerifyDeviceUseCase
import domain.usecase.api.source.usecase.reader.CreateFolderUseCase
import domain.usecase.api.source.usecase.reader.DeleteItemUseCase
import domain.usecase.api.source.usecase.reader.DownloadAndBuildEpubUseCase
import domain.usecase.api.source.usecase.reader.DownloadFileUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceSettingsUseCase
import domain.usecase.api.source.usecase.reader.GetLastConnectedIpUseCase
import domain.usecase.api.source.usecase.reader.GetDeviceStatusUseCase
import domain.usecase.api.source.usecase.reader.ListFilesUseCase
import domain.usecase.api.source.usecase.reader.MoveItemUseCase
import domain.usecase.api.source.usecase.reader.RenameItemUseCase
import domain.usecase.api.source.usecase.reader.SetDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.SetLastConnectedIpUseCase
import domain.usecase.api.source.usecase.reader.UpdateDeviceIpUseCase
import domain.usecase.api.source.usecase.reader.UpdateDeviceSettingsUseCase
import domain.usecase.api.source.usecase.reader.UploadEpubUseCase
import domain.usecase.impl.source.usecase.configuration.GetApplicationLanguageUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.GetOnboardingStatusUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.GetThemeUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.ObserveApplicationLanguageUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.ObserveThemeUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.SetApplicationLanguageUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.SetOnboardingStatusUseCaseImpl
import domain.usecase.impl.source.usecase.configuration.SetThemeUseCaseImpl
import domain.usecase.impl.source.usecase.reader.DiscoverDevicesUseCaseImpl
import domain.usecase.impl.source.usecase.reader.VerifyDeviceUseCaseImpl
import domain.usecase.impl.source.usecase.reader.CreateFolderUseCaseImpl
import domain.usecase.impl.source.usecase.reader.DeleteItemUseCaseImpl
import domain.usecase.impl.source.usecase.reader.DownloadAndBuildEpubUseCaseImpl
import domain.usecase.impl.source.usecase.reader.DownloadFileUseCaseImpl
import domain.usecase.impl.source.usecase.reader.GetDeviceIpUseCaseImpl
import domain.usecase.impl.source.usecase.reader.GetDeviceSettingsUseCaseImpl
import domain.usecase.impl.source.usecase.reader.GetLastConnectedIpUseCaseImpl
import domain.usecase.impl.source.usecase.reader.GetDeviceStatusUseCaseImpl
import domain.usecase.impl.source.usecase.reader.ListFilesUseCaseImpl
import domain.usecase.impl.source.usecase.reader.MoveItemUseCaseImpl
import domain.usecase.impl.source.usecase.reader.RenameItemUseCaseImpl
import domain.usecase.impl.source.usecase.reader.SetDeviceIpUseCaseImpl
import domain.usecase.impl.source.usecase.reader.SetLastConnectedIpUseCaseImpl
import domain.usecase.impl.source.usecase.reader.UpdateDeviceIpUseCaseImpl
import domain.usecase.impl.source.usecase.reader.UpdateDeviceSettingsUseCaseImpl
import domain.usecase.impl.source.usecase.reader.UploadEpubUseCaseImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin module that provides domain-usecase-impl bindings.
 *
 * Registers all use case implementations as singletons
 * bound to their corresponding API interfaces.
 */
public val domainUseCaseImplModule: Module = module {
    // Configuration
    singleOf(::GetThemeUseCaseImpl) bind GetThemeUseCase::class
    singleOf(::SetThemeUseCaseImpl) bind SetThemeUseCase::class
    singleOf(::ObserveThemeUseCaseImpl) bind ObserveThemeUseCase::class
    singleOf(::GetOnboardingStatusUseCaseImpl) bind GetOnboardingStatusUseCase::class
    singleOf(::SetOnboardingStatusUseCaseImpl) bind SetOnboardingStatusUseCase::class
    singleOf(::SetApplicationLanguageUseCaseImpl) bind SetApplicationLanguageUseCase::class
    singleOf(::GetApplicationLanguageUseCaseImpl) bind GetApplicationLanguageUseCase::class
    singleOf(::ObserveApplicationLanguageUseCaseImpl) bind ObserveApplicationLanguageUseCase::class
    // Reader — Discovery
    singleOf(::DiscoverDevicesUseCaseImpl) bind DiscoverDevicesUseCase::class
    singleOf(::VerifyDeviceUseCaseImpl) bind VerifyDeviceUseCase::class
    // Reader — Device IP
    singleOf(::GetDeviceIpUseCaseImpl) bind GetDeviceIpUseCase::class
    singleOf(::SetDeviceIpUseCaseImpl) bind SetDeviceIpUseCase::class
    singleOf(::UpdateDeviceIpUseCaseImpl) bind UpdateDeviceIpUseCase::class
    singleOf(::GetLastConnectedIpUseCaseImpl) bind GetLastConnectedIpUseCase::class
    singleOf(::SetLastConnectedIpUseCaseImpl) bind SetLastConnectedIpUseCase::class
    // Reader — File operations
    singleOf(::DownloadFileUseCaseImpl) bind DownloadFileUseCase::class
    singleOf(::ListFilesUseCaseImpl) bind ListFilesUseCase::class
    singleOf(::DeleteItemUseCaseImpl) bind DeleteItemUseCase::class
    singleOf(::RenameItemUseCaseImpl) bind RenameItemUseCase::class
    singleOf(::MoveItemUseCaseImpl) bind MoveItemUseCase::class
    singleOf(::CreateFolderUseCaseImpl) bind CreateFolderUseCase::class
    singleOf(::GetDeviceStatusUseCaseImpl) bind GetDeviceStatusUseCase::class
    singleOf(::GetDeviceSettingsUseCaseImpl) bind GetDeviceSettingsUseCase::class
    singleOf(::UpdateDeviceSettingsUseCaseImpl) bind UpdateDeviceSettingsUseCase::class
    singleOf(::UploadEpubUseCaseImpl) bind UploadEpubUseCase::class
    singleOf(::DownloadAndBuildEpubUseCaseImpl) bind DownloadAndBuildEpubUseCase::class
}
