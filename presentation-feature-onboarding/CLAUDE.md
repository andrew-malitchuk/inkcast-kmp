# CLAUDE.md — presentation-feature-onboarding

## Purpose

User onboarding flow for initial app setup and introduction.

## Convention Plugins

- `dev.yamh.io.convention.feature`
- `dev.yamh.io.convention.di`

## Key Files

| File | Purpose |
|------|---------|
| `OnboardingScreen` | Entry point composable |
| `OnboardingViewModel` | Orbit MVI ViewModel |
| `OnboardingContract` | State / SideEffect / Intent definitions |

## Module Dependencies

- `domain-usecase-api`
- `presentation-core-localisation`
- `presentation-core-styling`
- `presentation-core-ui`
- `presentation-core-navigation-api`

## Build

```shell
./gradlew :presentation-feature-onboarding:build
```
