package presentation.core.styling.core

import androidx.compose.ui.unit.Dp

/**
 * Design-token holder for the spacing scale used across paddings, margins, and gaps.
 *
 * The scale follows a T-shirt sizing convention (XXS through 5XL) to keep spacing
 * consistent and easily adjustable from a single source of truth.
 *
 * @param spacingXXS Extra-extra-small spacing (2 dp).
 * @param spacingXS Extra-small spacing (4 dp).
 * @param spacingS Small spacing (8 dp).
 * @param spacingM Medium spacing (12 dp) -- default gap for most list items.
 * @param spacingL Large spacing (16 dp) -- standard screen-edge padding.
 * @param spacingXL Extra-large spacing (24 dp).
 * @param spacing2XL Double extra-large spacing (32 dp).
 * @param spacing3XL Triple extra-large spacing (48 dp).
 * @param spacing4XL Quadruple extra-large spacing (64 dp).
 * @param spacing5XL Quintuple extra-large spacing (80 dp).
 * @see Theme.spacing
 */
public data class ThemeSpacing(
    val spacingXXS: Dp,
    val spacingXS: Dp,
    val spacingS: Dp,
    val spacingM: Dp,
    val spacingL: Dp,
    val spacingXL: Dp,
    val spacing2XL: Dp,
    val spacing3XL: Dp,
    val spacing4XL: Dp,
    val spacing5XL: Dp,
)
