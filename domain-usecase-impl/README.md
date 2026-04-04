# domain-usecase-impl

> Concrete use case implementations that bridge repository contracts to the presentation layer.

## Responsibility

Implements every use case interface from `domain-usecase-api` by delegating to the corresponding repository method. Wraps all calls in the `resultLauncher` pattern to convert exceptions into the sealed `Failure` hierarchy. Provides the Koin module that registers all use case singletons.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-usecase-api` | Use case interfaces to implement |
| `domain-repository-api` | Repository interfaces to delegate to |
| `domain-core` | Domain models and Failure types |

## Public API

| Class | Description |
|---|---|
| `GetThemeUseCaseImpl` | Implements `GetThemeUseCase` |
| `SetThemeUseCaseImpl` | Implements `SetThemeUseCase` |
| `ObserveThemeUseCaseImpl` | Implements `ObserveThemeUseCase` |
| `GetOnboardingStatusUseCaseImpl` | Implements `GetOnboardingStatusUseCase` |
| `SetOnboardingStatusUseCaseImpl` | Implements `SetOnboardingStatusUseCase` |
| `GetApplicationLanguageUseCaseImpl` | Implements `GetApplicationLanguageUseCase` |
| `SetApplicationLanguageUseCaseImpl` | Implements `SetApplicationLanguageUseCase` |
| `ObserveApplicationLanguageUseCaseImpl` | Implements `ObserveApplicationLanguageUseCase` |
| `ListFilesUseCaseImpl` | Implements `ListFilesUseCase` |
| `DownloadFileUseCaseImpl` | Implements `DownloadFileUseCase` |
| `DeleteItemUseCaseImpl` | Implements `DeleteItemUseCase` |
| `RenameItemUseCaseImpl` | Implements `RenameItemUseCase` |
| `MoveItemUseCaseImpl` | Implements `MoveItemUseCase` |
| `CreateFolderUseCaseImpl` | Implements `CreateFolderUseCase` |
| `GetDeviceStatusUseCaseImpl` | Implements `GetDeviceStatusUseCase` |
| `GetDeviceSettingsUseCaseImpl` | Implements `GetDeviceSettingsUseCase` |
| `UpdateDeviceSettingsUseCaseImpl` | Implements `UpdateDeviceSettingsUseCase` |
| `DownloadAndBuildEpubUseCaseImpl` | Implements `DownloadAndBuildEpubUseCase` |
| `UploadEpubUseCaseImpl` | Implements `UploadEpubUseCase` |
| `GetDeviceIpUseCaseImpl` | Implements `GetDeviceIpUseCase` |
| `SetDeviceIpUseCaseImpl` | Implements `SetDeviceIpUseCase` |
| `UpdateDeviceIpUseCaseImpl` | Implements `UpdateDeviceIpUseCase` |
| `DiscoverDevicesUseCaseImpl` | Implements `DiscoverDevicesUseCase` |
| `VerifyDeviceUseCaseImpl` | Implements `VerifyDeviceUseCase` |
| `GetLastConnectedIpUseCaseImpl` | Implements `GetLastConnectedIpUseCase` |
| `SetLastConnectedIpUseCaseImpl` | Implements `SetLastConnectedIpUseCase` |
| `ResultLauncher` | Utility wrapping suspend calls into `Result<T>` with `Failure` mapping |
| `DomainUseCaseImplModule` | Koin module registering all use case singletons |

## Key Patterns

### ResultLauncher

All use case implementations use `resultLauncher` instead of `runCatching` to convert `Throwable` into the sealed `Failure` hierarchy:

```kotlin
public suspend fun <T> resultLauncher(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(Failure.Technical(e))
    }
```

### Koin DI Registration

```kotlin
val domainUseCaseImplModule = module {
    single<GetThemeUseCase> { GetThemeUseCaseImpl(get()) }
    single<ListFilesUseCase> { ListFilesUseCaseImpl(get()) }
    // ... all use cases registered as singletons
}
```

## Testing

```bash
./gradlew :domain-usecase-impl:test
```
