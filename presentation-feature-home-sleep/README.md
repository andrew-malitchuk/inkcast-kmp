# presentation-feature-home-sleep

> Sleep screen tab — create, crop, and upload custom sleep/screensaver images for the e-ink display.

## Responsibility

Implements the SLEEP tab of the Home screen. Provides an image editing pipeline: pick image, interactive crop with pan/zoom, resize to 480x800, convert to grayscale, apply Floyd-Steinberg dithering, optionally overlay a calendar or quote widget, and upload as BMP to the device. Also manages a gallery of uploaded sleep images.

## Dependencies

| Depends on | Purpose |
|---|---|
| `domain-usecase-api` | Use cases for file listing, upload, delete, device settings |
| `presentation-core-ui` | Shared UI components (CropBox, etc.) |
| `presentation-core-platform` | Image decoding, processing, BMP encoding, widget rendering |
| `presentation-core-localisation` | Localized strings |
| `presentation-core-styling` | Design tokens |
| FileKit | Native image picker dialog |

## Public API

| Class / Composable | Description |
|---|---|
| `HomeSleepScreen` | Entry point composable for the Sleep tab |
| `HomeSleepViewModel` | Orbit MVI ViewModel managing image pipeline and gallery |

### MVI Contract

**State (`HomeSleepState`):**
`isRefreshing`, `hasImage`, `sourcePreview`, `offsetX`, `offsetY`, `zoom`, `selectedWidget`, `quoteText`, `isProcessing`, `previewBitmap`, `isUploading`, `uploadProgress`, `statusMessage`, `isLoadingGallery`, `galleryFiles`, `deleteTarget`

**Intents (`HomeSleepIntent`):**
`PickImage`, `ImageSelected(bytes)`, `CropChanged(offsetX, offsetY, zoom)`, `CropFinished`, `WidgetSelected(index)`, `QuoteChanged(text)`, `Upload`, `OnGalleryItemMoreClick`, `ConfirmDeleteGalleryItem`, `DismissDialog`, `RefreshGallery`

**Side Effects (`HomeSleepSideEffect`):**
`ShowMessage(message)`, `ShowError(message)`, `LaunchImagePicker`

## Usage

```kotlin
// Embedded as a tab in HomeScreen
HomeSleepScreen()
```

## Testing

```bash
./gradlew :presentation-feature-home-sleep:test
```
