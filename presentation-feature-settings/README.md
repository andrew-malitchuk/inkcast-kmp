# presentation-feature-settings

> App settings screen — language and theme preference management.

## Responsibility

Allows users to change the application language (EN, UK, ES, DE) and theme (Light, Dark). Reads current preferences on init and persists changes immediately via use cases.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-usecase-api` | Use cases for language and theme preferences |
| `presentation-core-ui` | Shared UI components (SettingRow, NavigationHeader, etc.) |
| `presentation-core-localisation` | Localized strings |
| `presentation-core-styling` | Design tokens |
| `presentation-core-navigation-api` | Back navigation |

## Public API

| Class / Composable | Description |
|---|---|
| `SettingsScreen` | Entry point composable for the Settings screen |
| `SettingsViewModel` | Orbit MVI ViewModel managing preferences |

### MVI Contract

**State (`SettingsState`):**
`isLoading`, `selectedLanguageIndex`, `languageOptions`, `languageCodes`, `selectedThemeIndex`, `themeOptions`

**Intents (`SettingsIntent`):**
`OnBackClick`, `SelectLanguage(index)`, `SelectTheme(index)`

**Side Effects (`SettingsSideEffect`):**
`GoBackEffect`, `ShowError(messageId)`

## Usage

```kotlin
// Navigated to from Home or NavigationHost
SettingsScreen()
```

## Testing

```bash
./gradlew :presentation-feature-settings:test
```
