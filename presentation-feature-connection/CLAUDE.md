# CLAUDE.md — presentation-feature-connection

## Purpose

Device discovery and connection setup — allows users to input an IP address or scan for devices on the network.

## Convention Plugins

- `dev.yamh.io.convention.feature`
- `dev.yamh.io.convention.di`

## Key Files

| File | Purpose |
|------|---------|
| `ConnectionScreen` | Entry point composable |
| `ConnectionViewModel` | Orbit MVI ViewModel with discovery logic |
| `ConnectionContract` | State / SideEffect / Intent definitions |

## Notes

- Destination is serializable: `Destination.Connection(isInitialSetup: Boolean)`
- Uses `kotlinx-serialization` for navigation args

## Module Dependencies

- `domain-usecase-api`
- `presentation-core-localisation`
- `presentation-core-styling`
- `presentation-core-ui`
- `presentation-core-navigation-api`

## Build

```shell
./gradlew :presentation-feature-connection:build
```
