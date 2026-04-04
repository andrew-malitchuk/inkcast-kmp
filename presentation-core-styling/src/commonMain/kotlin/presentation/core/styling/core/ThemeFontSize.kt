package presentation.core.styling.core

import androidx.compose.ui.unit.TextUnit

/**
 * Design-token holder for font sizes across the typographic scale.
 *
 * Each property maps to a semantic text role and is expressed in [TextUnit] (sp).
 * Values are consumed by [ThemeTypography] when constructing full [TextStyle][androidx.compose.ui.text.TextStyle] tokens.
 *
 * @param display Font size for large hero / display text (e.g., splash headings).
 * @param title Font size for section or screen titles.
 * @param label Font size for form labels and small headings.
 * @param body Font size for primary body copy.
 * @param bodyEmphasis Font size for emphasized body copy (same size as [body], paired with bolder weight).
 * @param caption Font size for auxiliary or secondary descriptions.
 * @param action Font size for button and interactive-element labels.
 * @see ThemeTypography
 * @see Theme.fontSize
 */
public data class ThemeFontSize(
    val display: TextUnit,
    val title: TextUnit,
    val label: TextUnit,
    val body: TextUnit,
    val bodyEmphasis: TextUnit,
    val caption: TextUnit,
    val action: TextUnit,
)
