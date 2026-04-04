# presentation-feature-connection

Device discovery and connection feature module — handles IP input, UDP device scanning, and connection verification.

## Module Type

Feature (KMP + Compose Multiplatform)

## Key Classes

| Class | Description |
|-------|-------------|
| `ConnectionScreen` | Entry point composable for device connection |
| `ConnectionViewModel` | Orbit MVI ViewModel with device discovery logic |
| `ConnectionContract` | MVI contract: `State`, `SideEffect`, `Intent` |

## Architecture

Follows Orbit MVI pattern: `Screen` → `ViewModel (ContainerHost)` → `Contract (State/SideEffect/Intent)`.

## Dependencies

- `domain-usecase-api` — use case interfaces
- `presentation-core-*` — shared UI, styling, localisation, navigation
- `kotlinx-serialization` — navigation argument serialization
- Orbit MVI — state management
- Koin — dependency injection
