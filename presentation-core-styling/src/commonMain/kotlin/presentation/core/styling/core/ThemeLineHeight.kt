package presentation.core.styling.core

import androidx.compose.ui.unit.TextUnit

/**
 * Design-token holder for line heights across the typographic scale.
 *
 * Each property corresponds to its counterpart in [ThemeFontSize] and is expressed in
 * [TextUnit] (sp). These values ensure consistent vertical rhythm throughout the UI.
 *
 * @param display Line height for display text.
 * @param title Line height for section or screen titles.
 * @param label Line height for form labels and small headings.
 * @param body Line height for primary body copy.
 * @param bodyEmphasis Line height for emphasized body copy.
 * @param caption Line height for auxiliary descriptions.
 * @param action Line height for button and interactive-element labels.
 * @see ThemeFontSize
 * @see ThemeTypography
 * @see Theme.lineHeight
 */
public data class ThemeLineHeight(
    val display: TextUnit,
    val title: TextUnit,
    val label: TextUnit,
    val body: TextUnit,
    val bodyEmphasis: TextUnit,
    val caption: TextUnit,
    val action: TextUnit,
)
