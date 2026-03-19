package presentation.core.styling.source.provider

import androidx.compose.runtime.staticCompositionLocalOf
import presentation.core.styling.core.ThemeColor
import presentation.core.styling.core.ThemeFontSize
import presentation.core.styling.core.ThemeLineHeight
import presentation.core.styling.core.ThemeSpacing
import presentation.core.styling.core.ThemeTypography

internal val LocalThemeColor = staticCompositionLocalOf<ThemeColor> { error("No ThemeColor provided") }
internal val LocalThemeFontSize = staticCompositionLocalOf<ThemeFontSize> { error("No ThemeFontSize provided") }
internal val LocalThemeLineHeight = staticCompositionLocalOf<ThemeLineHeight> { error("No ThemeLineHeight provided") }
internal val LocalThemeSpacing = staticCompositionLocalOf<ThemeSpacing> { error("No ThemeSpacing provided") }
internal val LocalThemeTypography = staticCompositionLocalOf<ThemeTypography> { error("No ThemeTypography provided") }
