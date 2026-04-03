# presentation-core-localisation

> Multiplatform locale management for Compose Resources, supporting EN, UK, ES, and DE.

## Responsibility

Provides the `AppLocaleProvider` composable that overrides the Compose Resources locale for the entire composition tree. All UI strings in the app flow through this module via `stringResource()` from Compose Resources, ensuring consistent language switching without app restart.

## Dependencies

None. This is a core module with no internal project dependencies.

## Public API

| Composable / Class | Description |
|---|---|
| `AppLocaleProvider` | Composable wrapper that configures `ResourceEnvironment` with the active language code |
| `LocalLocalization` | CompositionLocal holding the current language code (`"en"`, `"uk"`, `"es"`, `"de"`) |

## Supported Languages

| Code | Language |
|---|---|
| `en` | English |
| `uk` | Ukrainian |
| `es` | Spanish |
| `de` | German |

## Usage

```kotlin
// Wrap your app content to apply locale
AppLocaleProvider(languageCode = "uk") {
    // All stringResource() calls inside resolve to Ukrainian
    Text(text = stringResource(Res.string.app_name))
}

// Read the current locale from within composition
val currentLang = LocalLocalization.current
```

## Testing

```bash
./gradlew :presentation-core-localisation:test
```
