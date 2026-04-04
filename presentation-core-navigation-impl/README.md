# presentation-core-navigation-impl

> Navigation graph implementation — wires all feature screens into a single NavigationHost.

## Responsibility

Implements the `AppNavigator` interface using Navigation3 and assembles the full navigation graph. The `NavigationHost` composable maps each `Destination` to its corresponding feature screen composable. Handles saved state serialization for process death recovery.

## Dependencies

| Depends on | Purpose |
|---|---|
| `presentation-core-navigation-api` | Navigator interface and Destination types |
| `presentation-feature-splash` | Splash screen |
| `presentation-feature-onboarding` | Onboarding screen |
| `presentation-feature-home` | Home screen with tabs |
| `presentation-feature-connection` | Connection setup screen |
| `presentation-feature-settings` | Settings screen |
| `presentation-feature-about` | About screen |

## Public API

| Class / Composable | Description |
|---|---|
| `AppNavigatorImpl` | Navigation3-based implementation of `AppNavigator` |
| `NavigationHost` | Root composable that creates the back stack and routes destinations to screens |
| `navSavedStateConfiguration` | Serialization config registering all Destination subclasses for saved state |

### Destination Routing

| Destination | Screen |
|---|---|
| `Splash` | `SplashScreen()` |
| `Onboarding` | `OnboardingScreen()` |
| `Home` | `HomeScreen()` |
| `Connection` | `ConnectionScreen(isInitialSetup)` |
| `Settings` | `SettingsScreen()` |
| `About` | `AboutScreen()` |

## Usage

```kotlin
// Used in App.kt — typically called once at the root
@Composable
fun App() {
    AppTheme {
        NavigationHost()
    }
}
```

## Testing

```bash
./gradlew :presentation-core-navigation-impl:test
```
