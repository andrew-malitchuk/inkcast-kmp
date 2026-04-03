# domain-repository-api

> Repository interfaces defining the contract between domain and data layers.

## Responsibility

Declares the repository abstractions that the domain layer consumes and the data layer implements. This module enforces the dependency inversion principle — upper layers depend only on these interfaces, never on concrete data implementations.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-core` | Domain models and Failure types |

## Public API

| Interface | Description |
|---|---|
| `ConfigureRepository` | App configuration: theme, onboarding status, language preferences |
| `ReaderRepository` | Device interaction: files, status, settings, discovery, upload, link processing |

### ConfigureRepository — Key Methods

| Method | Description |
|---|---|
| `observeTheme()` | Observe theme changes as `Flow` |
| `getTheme()` / `setTheme()` | Read/write current theme |
| `observeOnboarding()` | Observe onboarding status |
| `getOnboardingStatus()` / `setOnboardingStatus()` | Read/write onboarding completion |
| `observeApplicationLanguage()` | Observe language preference |
| `getApplicationLanguage()` / `setApplicationLanguage()` | Read/write language |

### ReaderRepository — Key Methods

| Method | Description |
|---|---|
| `listFiles(path)` | List files at given path on device |
| `downloadFile(path)` | Download a file from device |
| `deleteItem(path)` / `renameItem()` / `moveItem()` | File management operations |
| `createFolder(path)` | Create a new folder |
| `getDeviceStatus()` | Query device hardware/firmware status |
| `getDeviceSettings()` / `updateDeviceSettings()` | Read/write device settings |
| `discoverDevices()` | UDP broadcast discovery on local network |
| `verifyDeviceAtIp(ip)` | Verify a device at given IP |
| `getDeviceIp()` / `setDeviceIp()` | Manage stored device IP |
| `getLastConnectedIp()` / `setLastConnectedIp()` | Track last successful connection |
| `uploadEpub(epub, onProgress)` | Upload EPUB to device with progress callback |
| `downloadAndBuildEpub(url, onStatus)` | Download article and convert to EPUB |

## Usage

```kotlin
// Injected via Koin in ViewModel or UseCase
class GetDeviceStatusUseCaseImpl(
    private val readerRepository: ReaderRepository,
) : GetDeviceStatusUseCase {
    override suspend fun invoke(): Result<DeviceStatusModel> =
        readerRepository.getDeviceStatus()
}
```

## Testing

```bash
./gradlew :domain-repository-api:test
```
