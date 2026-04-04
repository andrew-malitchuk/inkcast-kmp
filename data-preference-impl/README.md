# data-preference-impl

> Platform-specific preference storage implementations using multiplatform-settings and Android DataStore.

## Responsibility

Implements all preference source interfaces from `data-preference-api` with platform-appropriate storage backends. Each implementation uses `MutableStateFlow` for reactive observation. The module provides a Koin module that registers the platform-specific `Settings` instance and all preference source singletons.

## Dependencies

| Depends on | Purpose |
|---|---|
| `data-preference-api` | Preference interfaces to implement |
| `data-core` | `Resource` marker interface |

## Public API

| Symbol | Description |
|---|---|
| `dataPreferenceImplModule` | Koin module registering `Settings` and all 5 preference source singletons |

### Internal Implementations

| Class | Implements | Storage Key |
|---|---|---|
| `DeviceIpPreferenceSourceImpl` | `DeviceIpPreferenceSource` | `pref_device_ip` |
| `LastConnectedIpPreferenceSourceImpl` | `LastConnectedIpPreferenceSource` | `pref_last_connected_ip` |
| `LanguagePreferenceSourceImpl` | `LanguagePreferenceSource` | `pref_language` |
| `ThemePreferenceSourceImpl` | `ThemePreferenceSource` | `pref_theme` |
| `OnboardingPreferenceSourceImpl` | `OnboardingPreferenceSource` | `pref_onboarding_completed` |

### Platform-Specific Settings Provider

| Platform | Backend |
|---|---|
| Android | AndroidX Preferences DataStore via custom `DataStoreSettings` bridge |
| iOS | `NSUserDefaults` via `NSUserDefaultsSettings` |
| Desktop | `java.util.prefs.Preferences` via `PreferencesSettings` (node: `"inkcast"`) |

## Usage

```kotlin
// Koin DI wiring — include in your app module list
startKoin {
    modules(dataPreferenceImplModule)
}
```

## Testing

```bash
./gradlew :data-preference-impl:test
```
