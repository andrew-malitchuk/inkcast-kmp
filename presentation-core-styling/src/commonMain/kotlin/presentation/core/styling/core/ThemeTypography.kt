package presentation.core.styling.core

import androidx.compose.ui.text.TextStyle

/**
 * Design-token holder for fully composed [TextStyle] instances across the typographic scale.
 *
 * Each style combines the corresponding [ThemeFontSize], [ThemeLineHeight], font family, and
 * font weight into a ready-to-use [TextStyle]. Consumers should reference these tokens via
 * [Theme.typography] rather than constructing ad-hoc styles.
 *
 * @param display Style for large hero / display text (Merriweather SemiBold, 32 sp).
 * @param title Style for section or screen titles (Merriweather SemiBold, 24 sp).
 * @param label Style for form labels and small headings (Lato Regular, 14 sp).
 * @param body Style for primary body copy (Lato Regular, 16 sp).
 * @param bodyEmphasis Style for emphasized body copy (Lato Bold, 16 sp).
 * @param caption Style for auxiliary descriptions (Lato Regular, 12 sp).
 * @param action Style for button and interactive-element labels (Lato Bold, 14 sp).
 * @see Theme.typography
 * @see ThemeFontSize
 * @see ThemeLineHeight
 */
public data class ThemeTypography(
    val display: TextStyle,
    val title: TextStyle,
    val label: TextStyle,
    val body: TextStyle,
    val bodyEmphasis: TextStyle,
    val caption: TextStyle,
    val action: TextStyle,
)
