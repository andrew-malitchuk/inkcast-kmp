# common-core

> Shared utilities — type mapper interface and coroutine execution helpers.

## Responsibility

Provides foundational utilities used across all layers: a generic `Mapper<I, O>` functional interface for type conversions, and coroutine execution helpers (`executeCoroutine`, `executeResult`) that wrap suspend operations with loading/result/error callbacks.

## Dependencies

None. This is a leaf module with no internal project dependencies.

## Public API

| Class / Function | Description |
|---|---|
| `Mapper<I, O>` | Functional interface: `fun map(input: I): O` — used for model transformations across layers |
| `executeCoroutine<T>()` | Launches a coroutine with `loading`, `result`, and `errorBlock` callbacks |
| `executeResult<T>()` | Launches a coroutine returning `Result<T>`, folds into success/failure callbacks |

## Usage

```kotlin
// Define a mapper
object UserMapper : Mapper<UserNetwork, UserModel> {
    override fun map(input: UserNetwork): UserModel =
        UserModel(name = input.name.orEmpty())
}

// Execute with loading state
executeCoroutine(
    scope = viewModelScope,
    loading = { isLoading -> state.copy(isLoading = isLoading) },
    result = { data -> state.copy(data = data) },
    errorBlock = { error -> showError(error) },
    request = { repository.getData() },
)
```

## Testing

```bash
./gradlew :common-core:test
```
