package presentation.core.styling.source.attribute.color

import androidx.compose.ui.graphics.Color
import presentation.core.styling.core.ThemeColor

/**
 * Light-mode color palette providing concrete [Color] values for every [ThemeColor] role.
 *
 * The palette uses a warm off-white canvas with dark ink tones optimised for daylight
 * readability. Brand accent is slightly darkened compared to the dark variant to maintain
 * sufficient contrast on light surfaces.
 *
 * @see attributeDarkColorPalette
 * @see ThemeColor
 */
internal val attributeLightColorPalette: ThemeColor =
    ThemeColor(
        // region 1. Brand
        brand = Color(0xFFF56E0F),
        brandVariant = Color(0xFFD45E0D), // Darker variant for improved contrast on light surfaces
        // endregion
        // region 2. Surface
        canvas = Color(0xFFFBFBFB), // Warm off-white root background
        surface = Color(0xFFFFFFFF), // Pure white card / sheet background
        surfaceVariant = Color(0xFFF0F0F0),
        surfaceInverse = Color(0xFF151419),
        // endregion
        // region 3. Ink
        inkMain = Color(0xFF151419),
        inkSubtle = Color(0xFF878787),
        inkOnBrand = Color(0xFFFBFBFB),
        // endregion
        // region 4. Outline
        outlineLow = Color(0xFFE0E0E0),
        outlineHigh = Color(0xFF878787),
        // endregion
        // region 5. Status
        success = Color(0xFF2E7D32),
        error = Color(0xFFC62828),
        warning = Color(0xFFF9A825),
        // endregion
        // region 6. Interaction
        disabled = Color(0xFFBDBDBD),
        scrim = Color(0x4D151419), // ~30 % dark overlay
        // endregion
    )
