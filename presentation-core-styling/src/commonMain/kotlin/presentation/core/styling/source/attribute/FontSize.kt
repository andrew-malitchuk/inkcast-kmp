package presentation.core.styling.source.attribute

import androidx.compose.ui.unit.sp
import presentation.core.styling.core.ThemeFontSize

/**
 * Concrete font-size values for the application typographic scale.
 *
 * All sizes are specified in scaleable pixels (sp) so they respect the user's system
 * font-size preference. These values are consumed by [AttributeTypography] when composing
 * full [TextStyle][androidx.compose.ui.text.TextStyle] tokens.
 *
 * @see ThemeFontSize
 * @see AttributeTypography
 */
internal val attributeFontSize: ThemeFontSize =
    ThemeFontSize(
        display = 32.sp,
        title = 24.sp,
        label = 14.sp,
        body = 16.sp,
        bodyEmphasis = 16.sp, // Same size as body; emphasis is achieved via bold weight
        caption = 12.sp,
        action = 14.sp,
    )
