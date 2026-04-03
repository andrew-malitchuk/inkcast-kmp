# presentation-core-styling

> Design system tokens — colors, typography, spacing, and theme modes for the entire app.

## Responsibility

Defines the visual language of the app through a token-based design system. Provides `AppTheme` composable that resolves light/dark color palettes, builds typography from Merriweather (serif) and Lato (sans-serif) font families, and distributes all tokens via CompositionLocals. Every UI component reads tokens through the `Theme` singleton.

## Dependencies

None. This is a core module with no internal project dependencies.

## Public API

| Class / Object | Description |
|---|---|
| `AppTheme` | Top-level composable provider for all design tokens |
| `Theme` | Singleton accessor: `Theme.color`, `Theme.fontSize`, `Theme.lineHeight`, `Theme.spacing`, `Theme.typography` |
| `ThemeMode` | Enum: `Light`, `Dark`, `System` |
| `ThemeColor` | 16-color palette (brand, canvas, surface, ink, outline, semantic) |
| `ThemeFontSize` | 7-level type scale (display → caption) |
| `ThemeLineHeight` | Matching line heights for each font size level |
| `ThemeSpacing` | 10-level spacing scale (XXS 2dp → 5XL 80dp) |
| `ThemeTypography` | 7 ready-to-use `TextStyle` tokens |

## Usage

```kotlin
// Wrap app content in theme
AppTheme(mode = ThemeMode.Dark) {
    // Access tokens anywhere in composition
    Text(
        text = "Hello",
        style = Theme.typography.title,
        color = Theme.color.inkMain,
    )
    Spacer(modifier = Modifier.height(Theme.spacing.spacingM))
}
```

## Testing

```bash
./gradlew :presentation-core-styling:test
```
