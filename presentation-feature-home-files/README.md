# presentation-feature-home-files

> File browser tab — browse, download, upload, rename, move, and delete files on the connected device.

## Responsibility

Implements the FILES tab of the Home screen. Provides full file management capabilities: directory navigation with breadcrumb trail, file CRUD operations (create folder, rename, delete, move), and EPUB upload with progress tracking via WebSocket.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-usecase-api` | Use cases for file operations and upload |
| `presentation-core-ui` | Shared UI components (ItemCard, NavigationHeader, etc.) |
| `presentation-core-localisation` | Localized strings |
| `presentation-core-styling` | Design tokens |
| FileKit | Native file picker dialog |

## Public API

| Class / Composable | Description |
|---|---|
| `HomeFilesScreen` | Entry point composable for the Files tab |
| `HomeFilesViewModel` | Orbit MVI ViewModel managing file browser state |

### MVI Contract

**State (`HomeFilesState`):**
`isLoading`, `isRefreshing`, `isUploading`, `uploadProgress`, `currentPath`, `files`, `showAddMenu`, `showCreateFolderDialog`, `actionTarget`, `deleteTarget`, `renameTarget`

**Intents (`HomeFilesIntent`):**
`Refresh`, `OnAddClick`, `OnUploadFileClick`, `OnItemClick`, `OnItemMoreClick`, `NavigateBack`, `ShowCreateFolderDialog`, `DismissDialog`, `SelectDeleteAction`, `SelectRenameAction`, `ConfirmCreateFolder`, `ConfirmDelete`, `ConfirmRename`, `FileSelected`

**Side Effects (`HomeFilesSideEffect`):**
`ShowMessage(message)`, `ShowError(message)`, `LaunchFilePicker`

## Usage

```kotlin
// Embedded as a tab in HomeScreen
HomeFilesScreen()
```

## Testing

```bash
./gradlew :presentation-feature-home-files:test
```
