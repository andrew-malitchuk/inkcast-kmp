# presentation-core-platform

> Platform-specific utilities — image processing, date/time, and e-ink rendering.

## Responsibility

Provides platform-abstracted utilities for image decoding (with EXIF rotation), e-ink image processing (crop, resize, grayscale, Floyd-Steinberg dithering), BMP encoding, and calendar/quote widget rendering. Each platform (Android, iOS, Desktop) supplies its own implementation via expect/actual declarations.

## Dependencies

| Depends on | Purpose |
|---|---|
| AndroidX ExifInterface | EXIF orientation handling (Android only) |

## Public API

### Image Decoding (expect/actual)

| Class | Description |
|---|---|
| `ImageDecoder` | Platform-specific image decoder: `decode(bytes) → DecodedImage?`, `toImageBitmap(pixels, w, h) → ImageBitmap` |
| `DecodedImage` | Platform-agnostic pixel representation: `pixels: IntArray`, `width: Int`, `height: Int` |

### Image Processing (pure Kotlin)

| Object | Description |
|---|---|
| `ImageProcessor` | `centerCrop()`, `cropWithTransform()`, `resize()`, `toGrayscale()`, `floydSteinbergDither()` — all targeting 480x800 e-ink display |
| `BmpEncoder` | Encodes grayscale pixel array to 24-bit BMP bytes |

### Widget Rendering

| Object | Description |
|---|---|
| `WidgetRenderer` | Draws calendar or quote overlay onto grayscale pixel buffer |
| `WidgetRenderer.WidgetType` | Enum: `NONE`, `CALENDAR`, `QUOTE` |

### Date/Time (expect/actual)

| Function | Description |
|---|---|
| `getCurrentDate()` | Returns `CurrentDate(year, month, day)` |
| `currentTimeMillis()` | Returns milliseconds since Unix epoch |

### Platform Backends

| Platform | ImageDecoder | Date/Time |
|---|---|---|
| Android | `BitmapFactory` + `ExifInterface` | `java.util.Calendar` |
| iOS | `UIImage` + CoreGraphics | `NSCalendar` + `NSDate` |
| Desktop | (shared JVM) | `java.util.Calendar` |

## Usage

```kotlin
// Decode an image from bytes
val decoded = ImageDecoder.decode(imageBytes) ?: return

// Process for e-ink display
val cropped = ImageProcessor.centerCrop(decoded)
val resized = ImageProcessor.resize(cropped)
val gray = ImageProcessor.toGrayscale(resized)
ImageProcessor.floydSteinbergDither(gray, 480, 800)

// Render calendar widget overlay
WidgetRenderer.render(gray, 480, 800, WidgetType.CALENDAR, 2026, 4)

// Encode to BMP
val bmpBytes = BmpEncoder.encode(gray, 480, 800)
```

## Testing

```bash
./gradlew :presentation-core-platform:test
```
