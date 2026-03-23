package presentation.core.styling.source.provider

import androidx.compose.runtime.staticCompositionLocalOf
import presentation.core.styling.core.ThemeColor
import presentation.core.styling.core.ThemeFontSize
import presentation.core.styling.core.ThemeLineHeight
import presentation.core.styling.core.ThemeSpacing
import presentation.core.styling.core.ThemeTypography

// CompositionLocal instances that carry each design-token group down the tree.
// Using `staticCompositionLocalOf` because theme tokens rarely change mid-composition;
// static locals avoid per-recomposition lookup overhead compared to `compositionLocalOf`.

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] providing the current [ThemeColor] palette.
 *
 * @see ThemeColor
 */
internal val LocalThemeColor = staticCompositionLocalOf<ThemeColor> { error("No ThemeColor provided") }

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] providing the current [ThemeFontSize] tokens.
 *
 * @see ThemeFontSize
 */
internal val LocalThemeFontSize = staticCompositionLocalOf<ThemeFontSize> { error("No ThemeFontSize provided") }

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] providing the current [ThemeLineHeight] tokens.
 *
 * @see ThemeLineHeight
 */
internal val LocalThemeLineHeight = staticCompositionLocalOf<ThemeLineHeight> { error("No ThemeLineHeight provided") }

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] providing the current [ThemeSpacing] tokens.
 *
 * @see ThemeSpacing
 */
internal val LocalThemeSpacing = staticCompositionLocalOf<ThemeSpacing> { error("No ThemeSpacing provided") }

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] providing the current [ThemeTypography] tokens.
 *
 * @see ThemeTypography
 */
internal val LocalThemeTypography = staticCompositionLocalOf<ThemeTypography> { error("No ThemeTypography provided") }
