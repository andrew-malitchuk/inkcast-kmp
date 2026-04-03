# presentation-feature-home-create

> EPUB creation tab — convert article URLs or plain text into EPUB files and upload to the device.

## Responsibility

Implements the CREATE tab of the Home screen. Supports two modes: URL-to-EPUB (downloads article, extracts content, builds EPUB) and text-to-EPUB (wraps user-provided text). Performs device reachability check before upload. Handles shared URLs from Android ACTION_SEND intents automatically.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-usecase-api` | Use cases for EPUB creation, upload, device verification |
| `presentation-core-ui` | Shared UI components |
| `presentation-core-localisation` | Localized strings |
| `presentation-core-styling` | Design tokens |
| `presentation-core-navigation-api` | Navigation to Connection screen |

## Public API

| Class / Composable | Description |
|---|---|
| `HomeCreateScreen` | Entry point composable for the Create tab |
| `HomeCreateViewModel` | Orbit MVI ViewModel managing EPUB creation and upload flow |

### MVI Contract

**State (`HomeCreateState`):**
`isLoading`, `isRefreshing`, `selectedModeIndex`, `modeOptions`, `url`, `title`, `text`, `isProcessing`, `isUploading`, `progress`, `statusMessage`, `errorMessage`, `epubReady`, `epubTitle`, `epubSizeKb`, `showDeviceNotReachable`

**Intents (`HomeCreateIntent`):**
`Refresh`, `SelectMode(index)`, `UpdateUrl(url)`, `UpdateTitle(title)`, `UpdateText(text)`, `CreateEpub`, `UploadToDevice`, `DismissDeviceNotReachable`, `RetryUpload`, `SetUpDevice`

**Side Effects (`HomeCreateSideEffect`):**
`ShowError`, `ShowUploadSuccess`, `NavigateToConnection`

## Usage

```kotlin
// Embedded as a tab in HomeScreen
HomeCreateScreen()
```

## Testing

```bash
./gradlew :presentation-feature-home-create:test
```
