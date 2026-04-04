package presentation.core.styling.source.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import presentation.core.styling.core.ThemeMode
import presentation.core.styling.source.attribute.AttributeTypography
import presentation.core.styling.source.attribute.attributeFontSize
import presentation.core.styling.source.attribute.attributeLineHeight
import presentation.core.styling.source.attribute.attributeSpacing
import presentation.core.styling.source.attribute.color.attributeDarkColorPalette
import presentation.core.styling.source.attribute.color.attributeLightColorPalette
import presentation.core.styling.source.provider.LocalThemeColor
import presentation.core.styling.source.provider.LocalThemeFontSize
import presentation.core.styling.source.provider.LocalThemeLineHeight
import presentation.core.styling.source.provider.LocalThemeSpacing
import presentation.core.styling.source.provider.LocalThemeTypography

/**
 * Top-level composable that provides Inkcast design-system tokens to the composition tree.
 *
 * Wrap your root content (or any isolated subtree) with [AppTheme] so that child composables
 * can access tokens via [Theme][presentation.core.styling.core.Theme]:
 * ```
 * AppTheme(mode = ThemeMode.System) {
 *     Scaffold(containerColor = Theme.color.canvas) { ... }
 * }
 * ```
 *
 * Internally, [AppTheme] resolves the correct color palette for the given [mode], builds
 * typography styles from bundled font resources, and supplies all tokens through
 * [CompositionLocalProvider].
 *
 * @param mode The desired appearance mode; defaults to [ThemeMode.Light].
 * @param content Composable content that will have access to the provided design tokens.
 * @see presentation.core.styling.core.Theme
 * @see ThemeMode
 * @see <a href="https://www.figma.com">Figma design spec (TBD)</a>
 */
@Composable
public fun AppTheme(mode: ThemeMode = ThemeMode.Light, content: @Composable () -> Unit) {
    val isSystemDark = isSystemInDarkTheme()

    // Resolve light / dark palette based on the requested mode
    val currentColorPalette = when (mode) {
        ThemeMode.Light -> attributeLightColorPalette
        ThemeMode.Dark -> attributeDarkColorPalette
        ThemeMode.System -> if (isSystemDark) attributeDarkColorPalette else attributeLightColorPalette
    }

    val typography = AttributeTypography()

    CompositionLocalProvider(
        LocalThemeColor provides currentColorPalette,
        LocalThemeFontSize provides attributeFontSize,
        LocalThemeLineHeight provides attributeLineHeight,
        LocalThemeSpacing provides attributeSpacing,
        LocalThemeTypography provides typography,
        content = content,
    )
}
