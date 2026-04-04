# presentation-feature-onboarding

User onboarding feature module — guides the user through initial app setup.

## Module Type

Feature (KMP + Compose Multiplatform)

## Key Classes

| Class | Description |
|-------|-------------|
| `OnboardingScreen` | Entry point composable |
| `OnboardingViewModel` | Orbit MVI ViewModel managing onboarding steps |
| `OnboardingContract` | MVI contract: `State`, `SideEffect`, `Intent` |

## Architecture

Follows Orbit MVI pattern: `Screen` → `ViewModel (ContainerHost)` → `Contract (State/SideEffect/Intent)`.

## Dependencies

- `domain-usecase-api` — use case interfaces
- `presentation-core-*` — shared UI, styling, localisation, navigation
- Orbit MVI — state management
- Koin — dependency injection
