# data-preference-api

> Preference data source interfaces and models for persisting app configuration.

## Responsibility

Declares the contracts for reading, writing, and observing app preferences. Each preference type has a dedicated source interface extending `PreferenceSource<T>`, ensuring type-safe access to persisted values. The repository layer consumes these interfaces to manage theme, language, onboarding, and device connection settings.

## Dependencies

| Depends on | Purpose |
|---|---|
| `data-core` | `Resource` marker interface for preference models |

## Public API

### Base Interface

| Interface | Description |
|---|---|
| `PreferenceSource<T>` | Generic contract: `getData(): T`, `setData(data: T)`, `observeData(): Flow<T>` |

### Preference Sources

| Interface | Type Parameter | Description |
|---|---|---|
| `DeviceIpPreferenceSource` | `DeviceIpPreference` | Current connected device IP |
| `LastConnectedIpPreferenceSource` | `LastConnectedIpPreference` | Last successfully connected device IP |
| `LanguagePreferenceSource` | `LanguagePreference` | App language locale code |
| `ThemePreferenceSource` | `ThemePreference` | App theme (light, dark, system) |
| `OnboardingPreferenceSource` | `OnboardingPreference` | Onboarding completion flag |

### Models

| Class | Properties | Description |
|---|---|---|
| `DeviceIpPreference` | `ip: String?` | Device IP address |
| `LastConnectedIpPreference` | `ip: String?` | Last connected device IP |
| `LanguagePreference` | `languageCode: String` (default: `"en"`) | ISO 639-1 language code |
| `ThemePreference` | `theme: String` (default: `"system"`) | Theme identifier |
| `OnboardingPreference` | `isCompleted: Boolean` (default: `false`) | Onboarding status |

## Usage

```kotlin
// Read and observe a preference
val source: ThemePreferenceSource = get()
val current = source.getData() // ThemePreference(theme = "dark")
source.observeData().collect { pref -> applyTheme(pref.theme) }

// Write a preference
source.setData(ThemePreference(theme = "light"))
```

## Testing

```bash
./gradlew :data-preference-api:test
```
