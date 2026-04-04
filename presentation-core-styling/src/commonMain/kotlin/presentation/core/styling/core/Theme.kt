package presentation.core.styling.core

import androidx.compose.runtime.Composable
import presentation.core.styling.source.provider.LocalThemeColor
import presentation.core.styling.source.provider.LocalThemeFontSize
import presentation.core.styling.source.provider.LocalThemeLineHeight
import presentation.core.styling.source.provider.LocalThemeSpacing
import presentation.core.styling.source.provider.LocalThemeTypography

/**
 * Central accessor for all design-system tokens provided by [AppTheme][presentation.core.styling.source.theme.AppTheme].
 *
 * Usage from any `@Composable` scope:
 * ```
 * val bg = Theme.color.canvas
 * val gap = Theme.spacing.spacingM
 * ```
 *
 * Each property delegates to a [CompositionLocal][androidx.compose.runtime.CompositionLocal]
 * supplied by [AppTheme][presentation.core.styling.source.theme.AppTheme], so values
 * automatically update when the theme mode changes.
 *
 * @see presentation.core.styling.source.theme.AppTheme
 */
public object Theme {

    /**
     * Current color palette resolved for the active theme mode (light / dark / system).
     *
     * @return [ThemeColor] instance provided by [LocalThemeColor][presentation.core.styling.source.provider.LocalThemeColor].
     * @see ThemeColor
     */
    public val color: ThemeColor
        @Composable get() = LocalThemeColor.current

    /**
     * Font-size design tokens for each typographic scale level.
     *
     * @return [ThemeFontSize] instance provided by [LocalThemeFontSize][presentation.core.styling.source.provider.LocalThemeFontSize].
     * @see ThemeFontSize
     */
    public val fontSize: ThemeFontSize
        @Composable get() = LocalThemeFontSize.current

    /**
     * Line-height design tokens matching each typographic scale level.
     *
     * @return [ThemeLineHeight] instance provided by [LocalThemeLineHeight][presentation.core.styling.source.provider.LocalThemeLineHeight].
     * @see ThemeLineHeight
     */
    public val lineHeight: ThemeLineHeight
        @Composable get() = LocalThemeLineHeight.current

    /**
     * Spacing scale used for paddings, margins, and gaps across the UI.
     *
     * @return [ThemeSpacing] instance provided by [LocalThemeSpacing][presentation.core.styling.source.provider.LocalThemeSpacing].
     * @see ThemeSpacing
     */
    public val spacing: ThemeSpacing
        @Composable get() = LocalThemeSpacing.current

    /**
     * Pre-built [TextStyle][androidx.compose.ui.text.TextStyle] tokens combining font family,
     * size, line height, and weight for each typographic scale level.
     *
     * @return [ThemeTypography] instance provided by [LocalThemeTypography][presentation.core.styling.source.provider.LocalThemeTypography].
     * @see ThemeTypography
     */
    public val typography: ThemeTypography
        @Composable get() = LocalThemeTypography.current
}
