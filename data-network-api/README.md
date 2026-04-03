# data-network-api

> Network data source interfaces and DTOs for communicating with the CrossPoint e-reader device.

## Responsibility

Declares the contracts for all network operations: REST API calls to the device, UDP device discovery, WebSocket file uploads, and article-to-EPUB processing. Consumers depend on these interfaces without knowing the underlying HTTP client or transport details.

## Dependencies

| Depends on | Purpose |
|---|---|
| `data-core` | `Resource` marker interface for network models |

## Public API

### Interfaces

| Interface | Description |
|---|---|
| `DeviceAddressProvider` | Supplies the current device IP address for network calls |
| `CrossPointNetworkSource` | REST API for device file operations, status, settings, and verification |
| `DeviceDiscoverySource` | UDP broadcast discovery of devices on the local network |
| `UploadNetworkSource` | WebSocket-based chunked EPUB upload to device |
| `LinkProcessingNetworkSource` | Downloads an article URL and builds an EPUB archive |

### Models

| Class | Description |
|---|---|
| `RemoteFileNetwork` | File/directory entry from device (name, size, isDirectory, isEpub) |
| `DeviceStatusNetwork` | Device hardware status (version, IP, mode, RSSI, freeHeap, uptime) |
| `SettingItemNetwork` | Device setting with key, name, category, type, value, and options |
| `PreparedEpubNetwork` | Downloaded article as EPUB (title + byte array) |
| `NetworkType` | Enum: WIFI, CELLULAR, NONE |

### CrossPointNetworkSource Methods

| Method | Description |
|---|---|
| `downloadFile(path)` | Download file bytes from device |
| `listFiles(path)` | List files at given path |
| `deleteItem(path, isDirectory)` | Delete a file or directory |
| `renameItem(path, newName)` | Rename a file or directory |
| `moveItem(sourcePath, destPath)` | Move a file or directory |
| `createFolder(name, parentPath)` | Create a new folder |
| `getStatus()` | Query device hardware/firmware status |
| `verifyDeviceAtIp(ip)` | Check if a device responds at given IP |
| `getSettings()` | Retrieve device settings list |
| `updateSettings(settings)` | Update device settings |

## Usage

```kotlin
// Consumed via DI in repository layer
class ReaderRepositoryImpl(
    private val networkSource: CrossPointNetworkSource,
    private val discoverySource: DeviceDiscoverySource,
) {
    suspend fun listFiles(path: String): List<RemoteFileModel> =
        networkSource.listFiles(path).map { it.toModel() }
}
```

## Testing

```bash
./gradlew :data-network-api:test
```
