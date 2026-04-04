# data-core

> Marker interface for all data layer resources, providing a shared type contract across data modules.

## Responsibility

Defines the `Resource` marker interface that all data-layer models (network DTOs, preference models, database entities) must implement. This ensures architectural consistency and enables generic mapper patterns in the repository layer.

## Dependencies

None. This is a leaf module with no internal project dependencies.

## Public API

| Interface | Description |
|---|---|
| `Resource` | Marker interface for all data layer representations |

## Usage

```kotlin
// All data models implement Resource
@Serializable
public data class RemoteFileNetwork(
    val name: String? = null,
    val size: Long? = null,
) : Resource
```

## Testing

```bash
./gradlew :data-core:test
```
