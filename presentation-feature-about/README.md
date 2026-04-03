# presentation-feature-about

> About screen — displays app information, version, and credits.

## Responsibility

Provides a simple informational screen showing app details. Minimal MVI implementation with back navigation support.

## Dependencies

| Depends on | Purpose |
|---|---|
| `presentation-core-ui` | Shared UI components |
| `presentation-core-localisation` | Localized strings |
| `presentation-core-styling` | Design tokens |
| `presentation-core-navigation-api` | Back navigation |

## Public API

| Class / Composable | Description |
|---|---|
| `AboutScreen` | Entry point composable for the About screen |
| `AboutViewModel` | Orbit MVI ViewModel (minimal — handles back navigation) |

### MVI Contract

**State (`AboutState`):**
`isLoading`

**Intents (`AboutIntent`):**
`OnBackClick`

**Side Effects (`AboutSideEffect`):**
`GoBackEffect`, `ShowError(messageId)`

## Usage

```kotlin
// Navigated to from Settings or NavigationHost
AboutScreen()
```

## Testing

```bash
./gradlew :presentation-feature-about:test
```
