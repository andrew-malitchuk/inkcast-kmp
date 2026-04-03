# presentation-feature-home

Main home screen feature module — tab-based container for FILES, CREATE, SLEEP, and DEVICE sub-features.

## Module Type

Feature (KMP + Compose Multiplatform)

## Key Classes

| Class | Description |
|-------|-------------|
| `HomeScreen` | Tab container composable |
| `HomeViewModel` | Orbit MVI ViewModel managing active tab |
| `HomeContract` | MVI contract: `State`, `SideEffect`, `Intent` |
| `HomeTab` | Enum defining available tabs |

## Architecture

Follows Orbit MVI pattern. Embeds child feature screens (`HomeFiles`, `HomeCreate`, `HomeSleep`, `HomeDevice`) as tab content.

## Dependencies

- `domain-usecase-api` — use case interfaces
- `presentation-core-*` — shared UI, styling, localisation, navigation
- `presentation-feature-home-files` — file browser tab
- `presentation-feature-home-create` — EPUB creation tab
- `presentation-feature-home-device` — device info tab
- `presentation-feature-home-sleep` — sleep/motion tab
- Orbit MVI — state management
- Koin — dependency injection
