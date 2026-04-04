# build-logic/convention

> Gradle convention plugins for standardized KMP module configuration.

## Responsibility

Provides four convention plugins that standardize build setup across all project modules. Each plugin extends `BaseConventionPlugin`, which offers hooks for plugin application, platform configuration, and dependency management. Module naming is derived automatically from the Gradle project path.

## Registered Plugins

| Plugin ID | Class | Purpose |
|---|---|---|
| `dev.yamh.io.convention.application` | `ApplicationConventionPlugin` | App modules (Android + iOS + Desktop targets, Compose) |
| `dev.yamh.io.convention.feature` | `FeatureConventionPlugin` | Feature modules (KMP + Compose, identical to application) |
| `dev.yamh.io.convention.library` | `LibraryConventionPlugin` | Library modules (KMP without Compose) |
| `dev.yamh.io.convention.di` | `DiConventionPlugin` | Adds Koin DI dependencies and enables explicit API mode |

## Public API

| Class | Description |
|---|---|
| `BaseConventionPlugin` | Abstract base with hooks: `configurePlugin()`, `configureAndroidPlatform()`, `configureIOsPlatform()`, `configureCommonDependencies()`, `configureAndroidDependencies()`, `configureIOsDependencies()` |
| `ApplicationConventionPlugin` | Applies Kotlin Multiplatform, AGP, Compose; configures Android (namespace, SDK), iOS (static frameworks), Desktop (JVM) |
| `FeatureConventionPlugin` | Same as Application — used for feature modules |
| `LibraryConventionPlugin` | Applies Kotlin Multiplatform, AGP — no Compose plugins |
| `DiConventionPlugin` | Adds `koin-core` (api), `koin-compose`, `koin-viewmodel`, `koin-android`, `koin-android-compose` |

### Extension Properties

| Property | Description |
|---|---|
| `Project.libs` | Access to `libs.versions.toml` version catalog |
| `Project.moduleName` | Converts project path to module name (`:feature-login` -> `feature.login`) |

## Configuration Details

- **Kotlin:** 2.3.20, context receivers enabled
- **JVM target:** 21
- **Android:** minSdk 27, targetSdk 36, AGP 9.0
- **iOS:** Static frameworks for arm64, x64, simulator arm64
- **Explicit API mode:** Enforced in all KMP modules

## Usage

```kotlin
// build.gradle.kts for a feature module
plugins {
    id("dev.yamh.io.convention.feature")
    id("dev.yamh.io.convention.di")
}
```

```kotlin
// build.gradle.kts for a library module
plugins {
    id("dev.yamh.io.convention.library")
}
```

## Testing

```bash
./gradlew :build-logic:convention:test
```
