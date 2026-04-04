# data-network-impl

> Ktor-based implementations of network data sources for CrossPoint device communication.

## Responsibility

Provides concrete HTTP, WebSocket, and UDP implementations for all network interfaces defined in `data-network-api`. Handles platform-specific networking: OkHttp on Android (with WiFi network binding), Darwin on iOS, and CIO on Desktop. Includes EPUB 3 archive building from downloaded articles.

## Dependencies

| Depends on | Purpose |
|---|---|
| `data-network-api` | Network interfaces and DTOs to implement |
| `data-preference-api` | Device IP preference for address resolution |

## Public API

| Class | Description |
|---|---|
| `NetworkInitializer` | Android-only object — must be called from `Application.onCreate()` to bind HTTP traffic to WiFi network |
| `dataNetworkImplModule` | Koin module registering all network data source singletons |

All implementation classes are `internal` — consumers interact only through the interfaces from `data-network-api`.

### Internal Implementations

| Class | Implements | Description |
|---|---|---|
| `CrossPointNetworkSourceImpl` | `CrossPointNetworkSource` | Ktor HTTP client for device REST API |
| `UploadNetworkSourceImpl` | `UploadNetworkSource` | Ktor WebSocket chunked upload for ESP32 devices |
| `LinkProcessingNetworkSourceImpl` | `LinkProcessingNetworkSource` | Article download + EPUB generation |
| `DeviceDiscoverySourceImpl` | `DeviceDiscoverySource` | Delegates to platform-specific UDP broadcast |
| `DeviceAddressProviderImpl` | `DeviceAddressProvider` | Reads device IP from preferences |

### Platform-Specific (expect/actual)

| Class | Android | iOS | Desktop |
|---|---|---|---|
| `NetworkProvider` | OkHttp with WiFi network binding | Darwin with connectivity wait | CIO |
| `DeviceDiscovery` | `DatagramSocket` UDP broadcast | POSIX UDP sockets via `en0` | Stub (empty list) |
| `EpubBuilder` | `ZipOutputStream` | Manual ZIP assembler | `ZipOutputStream` |

## Usage

```kotlin
// Android Application.onCreate()
class InkcastApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkInitializer.initialize(connectivityManager, NetworkType.WIFI)
    }
}

// Koin DI wiring
val networkModule = dataNetworkImplModule
```

## Testing

```bash
./gradlew :data-network-impl:test
```
