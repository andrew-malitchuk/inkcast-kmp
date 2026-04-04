package presentation.core.styling.source.attribute

import androidx.compose.ui.unit.sp
import presentation.core.styling.core.ThemeLineHeight

/**
 * Concrete line-height values for the application typographic scale.
 *
 * Each value is paired with its [attributeFontSize] counterpart to maintain a consistent
 * vertical rhythm (roughly 1.25x -- 1.5x the font size). Expressed in sp for accessibility.
 *
 * @see ThemeLineHeight
 * @see attributeFontSize
 * @see AttributeTypography
 */
internal val attributeLineHeight: ThemeLineHeight =
    ThemeLineHeight(
        display = 40.sp,
        title = 32.sp,
        label = 20.sp,
        body = 24.sp,
        bodyEmphasis = 24.sp,
        caption = 16.sp,
        action = 20.sp,
    )
