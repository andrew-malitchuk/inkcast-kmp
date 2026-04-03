# presentation-core-ui

> Shared UI component library — atoms, molecules, and organisms for Compose Multiplatform.

## Responsibility

Provides the full set of reusable UI building blocks used across all feature screens. Components follow an atomic design hierarchy: atoms (buttons, inputs, icons), molecules (headers, cards, tab bars), and organisms (bottom sheets, pull-to-refresh). Includes custom shapes (squircle), animations (wavy progress, shimmer), and a rich icon set.

## Dependencies

| Depends on | Purpose |
|---|---|
| Compose Material3 | Foundation components |
| Stately Collections | Thread-safe collections for snackbar state |
| AtomicFU | Atomic operations |

## Public API

### Atoms — Buttons

| Composable / Class | Description |
|---|---|
| `Button` | Primary design-system button with style, size, icons, loading state |
| `IconButton` | Icon-only button with selection/loading states |
| `ButtonStyle` | Sealed class: `Primary`, `Secondary`, `Text` |
| `ButtonSizeType` | Enum: `Small`, `Medium`, `Large` |

### Atoms — Input & Controls

| Composable | Description |
|---|---|
| `Input` | Single-line text input with animated border, validation regex, clear button |
| `MultilineInput` | Multi-line text input variant |
| `Chip` | Selectable pill-shaped chip (filled/outlined) |
| `SimpleToggle` | Lightweight animated toggle switch |
| `Toggle` | Full-featured toggle with drag gestures and Cupertino-style animation |

### Atoms — Text & Layout

| Composable | Description |
|---|---|
| `SectionHeader` | Section heading text |
| `AutoSizeText` | Text that auto-scales to fit constraints |
| `IconContainer` | Rounded square container with icon and background |
| `SafeContainer` | Safe-area aware container |

### Atoms — Icons

`AppIcon`, `AlertTriangle`, `Battery`, `BookOpen`, `ChevronLeft`, `ChevronRight`, `Clock`, `FileText`, `Folder`, `HardDrive`, `Home`, `Image`, `Lock`, `MoreVertical`, `Plus`, `RefreshCcw`, `Settings`, `Smartphone`, `Sun`, `Type`, `Wifi`, `Wizard`

### Atoms — Shapes

| Class | Description |
|---|---|
| `SquircleShape` | Custom shape with smooth rounded corners |
| `GentleSquircleShape` | Softer squircle variant |

### Atoms — Progress & Animation

| Composable / Class | Description |
|---|---|
| `WavyProgressIndicator` | Animated wavy progress bar |
| `WavySlider` | Slider with animated sine-wave active track |
| `HorizontalAnimatedDivider` | Animated horizontal divider |
| `CropBox` | Gesture-driven crop viewport with pan/zoom |
| `Shimmer` | Modifier for shimmer loading effect |

### Molecules

| Composable | Description |
|---|---|
| `NavigationHeader` | Back button + title row |
| `SimpleHeader` | Title-only header |
| `ActionHeader` | Title + trailing action icon button |
| `ChipGroup` | Horizontally-wrapping flow of selectable chips |
| `ItemCard` | File-system item card with icon, title, overflow menu |
| `FolderNavigationCard` | Folder navigation card |
| `SettingRow` | Settings row with icon, label, subtitle, trailing slot |
| `TabBar` | Pill-shaped bottom tab bar with icon-only tabs |
| `SegmentedButtonGroup` | Horizontal group of segmented buttons |
| `ErrorState` | Full-screen error placeholder with icon and description |

### Organisms

| Composable | Description |
|---|---|
| `AppBottomSheet` | Modal bottom sheet with squircle top shape |
| `AppPullToRefreshBox` | Pull-to-refresh with wavy progress indicator |
| `AnimatedSequenceHost` | Host for animated sequences |

### Utilities

| Class | Description |
|---|---|
| `StackedSnackbarHostState` | Custom host state supporting stacked snackbars |

## Usage

```kotlin
// Button with style
Button(
    text = "Upload",
    style = ButtonStyle.Primary,
    size = ButtonSizeType.Medium,
    onClick = { /* ... */ },
)

// Navigation header
NavigationHeader(
    title = "Settings",
    onNavigationClick = { navigator.popBackStack() },
)

// Item card for file browser
ItemCard(
    title = "document.epub",
    description = "1.2 MB",
    type = ItemCardType.Book,
    onCallback = { callback -> /* handle click/more */ },
)
```

## Testing

```bash
./gradlew :presentation-core-ui:test
```
