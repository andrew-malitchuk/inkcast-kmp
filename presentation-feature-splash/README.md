# presentation-feature-splash

Splash screen feature module — displays initial loading state and navigates to the appropriate destination (onboarding or home).

## Module Type

Feature (KMP + Compose Multiplatform)

## Key Classes

| Class | Description |
|-------|-------------|
| `SplashScreen` | Entry point composable for the splash feature |
| `SplashViewModel` | Orbit MVI ViewModel handling initial app state resolution |
| `SplashContract` | MVI contract: `State`, `SideEffect`, `Intent` |
| `SplashContent` | Content composable with state-driven rendering |

## Architecture

Follows Orbit MVI pattern: `Screen` → `ViewModel (ContainerHost)` → `Contract (State/SideEffect/Intent)`.

## Dependencies

- `domain-usecase-api` — use case interfaces
- `presentation-core-*` — shared UI, styling, localisation, navigation
- Orbit MVI — state management
- Koin — dependency injection
