# aos-application

> Android application module — Activity, Application class, and network initialization.

## Responsibility

Android-specific entry point for Inkcast. Hosts `MainActivity` (single-activity architecture with edge-to-edge rendering), handles `ACTION_SEND` shared URL intents, and bootstraps Koin DI with Android context. `InkcastApplication` registers `ConnectivityManager` callbacks to bind OkHttp traffic to the correct network interface (WiFi or Cellular).

## Dependencies

| Depends on | Purpose |
|---|---|
| `compose-application` | Shared `App()` composable and `initKoin()` |
| `data-network-impl` | `NetworkInitializer` for WiFi network binding |

## Public API

| Class | Description |
|---|---|
| `MainActivity` | Single-activity entry point with edge-to-edge setup and shared URL extraction |
| `InkcastApplication` | Application subclass — Koin bootstrap and network callback registration |

### MainActivity

- Enables edge-to-edge rendering
- Extracts shared URLs from `ACTION_SEND` text/plain intents
- Delegates UI to shared `App(sharedUrl)` composable

### InkcastApplication

- Calls `initKoin { androidContext(this) }` on startup
- Registers `ConnectivityManager.NetworkCallback` for WiFi and Cellular
- Calls `NetworkInitializer.initialize()` to bind OkHttp to the active network interface

## Usage

```xml
<!-- AndroidManifest.xml -->
<application android:name=".InkcastApplication">
    <activity android:name=".MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.SEND" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="text/plain" />
        </intent-filter>
    </activity>
</application>
```

## Testing

```bash
./gradlew :aos-application:test
```
