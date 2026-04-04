# domain-usecase-api

> Use case interfaces that define the primary domain API consumed by the presentation layer.

## Responsibility

Provides single-responsibility use case contracts, organized by feature. Each use case wraps one repository operation and serves as the sole entry point from presentation into the domain layer.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-core` | Domain models and Failure types |

## Public API

### Configuration Use Cases

| Interface | Description |
|---|---|
| `GetThemeUseCase` | Retrieve current theme |
| `SetThemeUseCase` | Update theme preference |
| `ObserveThemeUseCase` | Observe theme changes as Flow |
| `GetOnboardingStatusUseCase` | Get onboarding completion status |
| `SetOnboardingStatusUseCase` | Mark onboarding as complete |
| `GetApplicationLanguageUseCase` | Get current language |
| `SetApplicationLanguageUseCase` | Set application language |
| `ObserveApplicationLanguageUseCase` | Observe language changes as Flow |

### Reader Use Cases

| Interface | Description |
|---|---|
| `ListFilesUseCase` | List files at a path on device |
| `DownloadFileUseCase` | Download a file from device |
| `DeleteItemUseCase` | Delete a file or folder |
| `RenameItemUseCase` | Rename a file or folder |
| `MoveItemUseCase` | Move a file or folder |
| `CreateFolderUseCase` | Create a new folder on device |
| `GetDeviceStatusUseCase` | Query device hardware status |
| `GetDeviceSettingsUseCase` | Get device settings list |
| `UpdateDeviceSettingsUseCase` | Update device settings |
| `DownloadAndBuildEpubUseCase` | Download article URL and build EPUB |
| `UploadEpubUseCase` | Upload EPUB to device |
| `GetDeviceIpUseCase` | Get stored device IP |
| `SetDeviceIpUseCase` | Store device IP |
| `UpdateDeviceIpUseCase` | Update device IP |
| `DiscoverDevicesUseCase` | Discover devices via UDP broadcast |
| `VerifyDeviceUseCase` | Verify device at a given IP |
| `GetLastConnectedIpUseCase` | Get last successful connection IP |
| `SetLastConnectedIpUseCase` | Store last connection IP |

### Utilities

| Class | Description |
|---|---|
| `Optional<T>` | Monad for representing optional values |

## Usage

```kotlin
// In a ViewModel — use cases are injected via Koin
class HomeFilesViewModel(
    private val listFilesUseCase: ListFilesUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel(), ContainerHost<State, SideEffect> {

    fun loadFiles(path: String) = intent {
        listFilesUseCase(path)
            .onSuccess { files -> reduce { state.copy(files = files) } }
            .onFailure { postSideEffect(SideEffect.ShowError(it)) }
    }
}
```

## Testing

```bash
./gradlew :domain-usecase-api:test
```
