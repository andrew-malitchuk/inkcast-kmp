# CLAUDE.md — presentation-feature-home

## Purpose

Main app hub with tab navigation — acts as a container for FILES, CREATE, SLEEP, and DEVICE tabs.

## Convention Plugins

- `dev.yamh.io.convention.feature`
- `dev.yamh.io.convention.di`

## Key Files

| File | Purpose |
|------|---------|
| `HomeScreen` | Tab container composable |
| `HomeViewModel` | Orbit MVI ViewModel managing tab selection |
| `HomeContract` | State / SideEffect / Intent definitions |
| `HomeTab` | Enum with 4 tabs: FILES, CREATE, SLEEP, DEVICE |

## Module Dependencies

- `domain-usecase-api`
- `presentation-core-localisation`
- `presentation-core-styling`
- `presentation-core-ui`
- `presentation-core-navigation-api`
- `presentation-feature-home-files`
- `presentation-feature-home-create`
- `presentation-feature-home-device`
- `presentation-feature-home-sleep`

## Build

```shell
./gradlew :presentation-feature-home:build
```
