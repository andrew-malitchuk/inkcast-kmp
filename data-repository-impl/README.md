# data-repository-impl

> Repository implementations bridging domain interfaces to network and preference data sources.

## Responsibility

Implements `ConfigureRepository` and `ReaderRepository` from the domain layer by orchestrating calls to network and preference data sources. Uses the `ModelResourceMapper` pattern for bidirectional conversion between domain models and data-layer resources. All mappers are internal singleton objects.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-repository-api` | Repository interfaces to implement |
| `domain-core` | Domain models and Failure types |
| `data-network-api` | Network data source interfaces |
| `data-preference-api` | Preference data source interfaces |
| `data-core` | `Resource` marker interface |
| `common-core` | Shared utilities |

## Public API

| Symbol | Description |
|---|---|
| `ModelResourceMapper<MODEL, RESOURCE>` | Base interface for bidirectional mappers (`toModel` / `toResource`) |
| `dataRepositoryImplModule` | Koin module registering both repository singletons |

### Internal Implementations

| Class | Implements | Data Sources Used |
|---|---|---|
| `ConfigureRepositoryImpl` | `ConfigureRepository` | `ThemePreferenceSource`, `OnboardingPreferenceSource`, `LanguagePreferenceSource` |
| `ReaderRepositoryImpl` | `ReaderRepository` | `CrossPointNetworkSource`, `UploadNetworkSource`, `LinkProcessingNetworkSource`, `DeviceIpPreferenceSource`, `LastConnectedIpPreferenceSource`, `DeviceDiscoverySource` |

### Mappers

| Object | Model | Resource |
|---|---|---|
| `ThemePreferenceMapper` | `ThemeModel` | `ThemePreference` |
| `OnboardingPreferenceMapper` | `OnboardingModel` | `OnboardingPreference` |
| `RemoteFileNetworkMapper` | `RemoteFileModel` | `RemoteFileNetwork` |
| `DeviceStatusNetworkMapper` | `DeviceStatusModel` | `DeviceStatusNetwork` |
| `SettingItemNetworkMapper` | `SettingItemModel` | `SettingItemNetwork` |
| `PreparedEpubNetworkMapper` | `PreparedEpubModel` | `PreparedEpubNetwork` |
| `LanguagePreferenceMapper` | `String?` | `LanguagePreference` |

## Usage

```kotlin
// Koin DI wiring
val repositoryModule = dataRepositoryImplModule

// Koin registration (inside the module)
singleOf(::ConfigureRepositoryImpl) bind ConfigureRepository::class
singleOf(::ReaderRepositoryImpl) bind ReaderRepository::class
```

## Testing

```bash
./gradlew :data-repository-impl:test
```
