# presentation-feature-home-device

> Device info tab — displays hardware status, firmware version, and device settings.

## Responsibility

Implements the DEVICE tab of the Home screen. Fetches and displays device system information (uptime, free RAM, firmware version, IP address, WiFi mode). Allows changing device orientation and theme settings with optimistic UI updates.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-usecase-api` | Use cases for device status, settings, and IP |
| `presentation-core-ui` | Shared UI components |
| `presentation-core-localisation` | Localized strings |
| `presentation-core-styling` | Design tokens |
| `presentation-core-navigation-api` | Navigation to Connection screen |

## Public API

| Class / Composable | Description |
|---|---|
| `HomeDeviceScreen` | Entry point composable for the Device tab |
| `HomeDeviceViewModel` | Orbit MVI ViewModel managing device info display |

### MVI Contract

**State (`HomeDeviceState`):**
`isLoading`, `isOnline`, `isRefreshing`, `uptime`, `freeRam`, `freeRamFraction`, `firmware`, `ipAddress`, `wifiMode`, `selectedOrientationIndex`, `orientationOptions`, `selectedThemeIndex`, `themeOptions`

**Intents (`HomeDeviceIntent`):**
`Refresh`, `SelectOrientation(index)`, `SelectTheme(index)`, `ChangeDevice`

**Side Effects (`HomeDeviceSideEffect`):**
`ShowError`, `NavigateToConnection`

## Usage

```kotlin
// Embedded as a tab in HomeScreen
HomeDeviceScreen()
```

## Testing

```bash
./gradlew :presentation-feature-home-device:test
```
