package presentation.core.styling.source.attribute

import androidx.compose.ui.unit.dp
import presentation.core.styling.core.ThemeSpacing

/**
 * Concrete spacing-scale values used for paddings, margins, and gaps.
 *
 * The scale grows non-linearly (2, 4, 8, 12, 16, 24, 32, 48, 64, 80 dp) to provide fine
 * granularity at small sizes while still offering generous whitespace steps at larger sizes.
 *
 * @see ThemeSpacing
 */
internal val attributeSpacing: ThemeSpacing =
    ThemeSpacing(
        spacingXXS = 2.dp,
        spacingXS = 4.dp,
        spacingS = 8.dp,
        spacingM = 12.dp,
        spacingL = 16.dp,
        spacingXL = 24.dp,
        spacing2XL = 32.dp,
        spacing3XL = 48.dp,
        spacing4XL = 64.dp,
        spacing5XL = 80.dp,
    )
