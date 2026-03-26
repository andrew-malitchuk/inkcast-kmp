# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Inkcast KMP — a Kotlin Multiplatform (KMP) project targeting Android and iOS, using Compose Multiplatform for shared UI. Package: `dev.yaxca.io` (source), `dev.inkcast.aos` (Android app ID).

## Build Commands

```shell
# Build Android debug APK
./gradlew :composeApp:assembleDebug

# Run all checks
./gradlew check

# Build iOS framework (used by Xcode)
./gradlew :composeApp:linkDebugFrameworkIosArm64

# Clean build
./gradlew clean
```

iOS app is built via Xcode from the `iosApp/` directory.

## Architecture

### Module Structure

- **composeApp** — Single shared KMP module containing all app code (common, Android, iOS source sets)
- **build-logic/convention** — Gradle convention plugins for standardized module configuration
- **iosApp** — Xcode project wrapping the Compose UI in SwiftUI via `ComposeUIViewController`

### Convention Plugins (build-logic)

Four registered plugins under `dev.yamh.io.convention`:
- `application` — Android/iOS app module setup
- `feature` — Feature modules with Compose dependencies
- `library` — Shared library modules
- `di` — Koin dependency injection wiring

All plugins extend `BaseConventionPlugin` which provides hooks for plugin, platform, and dependency configuration. Module naming derives from project path (e.g., `:feature-login` → `feature.login`). Context receivers are enabled globally. Explicit API mode is enforced in all KMP modules.

### Platform Entry Points

- **Android:** `MainActivity` → `setContent { App() }`
- **iOS:** `MainViewController()` → `ComposeUIViewController { App() }`, wrapped in SwiftUI via `ComposeView` (UIViewControllerRepresentable)
- **Common:** `App()` composable with Material3 theming

### Key Dependencies

| Purpose | Library |
|---------|---------|
| UI | Compose Multiplatform 1.10.2, Material3 |
| DI | Koin 4.1.1 (core, compose, viewmodel) |
| Navigation | AndroidX Navigation Compose 2.9.2 |
| Database | CouchbaseLite (KotBase) 3.2.4 |
| Settings | multiplatform-settings 1.3.0 |
| Connectivity | connectivity-core 2.4.1 |
| Serialization | kotlinx-serialization 1.6.0 |
| Coroutines | kotlinx-coroutines-core 1.9.0 |
| Widgets (Android) | Glance 1.1.1 |

### Toolchain

- Kotlin 2.3.20, Gradle 9.1.0, JVM target 21
- Android: minSdk 27, targetSdk 36
- iOS: Static frameworks for arm64, x64, simulator arm64
