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

@Composable
public fun AppTheme(mode: ThemeMode = ThemeMode.Light, content: @Composable () -> Unit) {
    val isSystemDark = isSystemInDarkTheme()
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
