package presentation.core.styling.source.attribute.color

import androidx.compose.ui.graphics.Color
import presentation.core.styling.core.ThemeColor

/**
 * Dark-mode color palette providing concrete [Color] values for every [ThemeColor] role.
 *
 * The palette uses a near-black canvas with lighter ink tones to ensure comfortable readability
 * in low-light environments. Brand accent is slightly brightened compared to the light variant
 * to maintain sufficient contrast on dark surfaces.
 *
 * @see attributeLightColorPalette
 * @see ThemeColor
 */
internal val attributeDarkColorPalette: ThemeColor =
    ThemeColor(
        // region 1. Brand
        brand = Color(0xFFF56E0F),
        brandVariant = Color(0xFFFF8A3D), // Lighter variant for improved contrast on dark surfaces
        // endregion
        // region 2. Surface
        canvas = Color(0xFF151419), // Near-black root background
        surface = Color(0xFF1B1B1E), // Slightly elevated card / sheet background
        surfaceVariant = Color(0xFF262626),
        surfaceInverse = Color(0xFFFBFBFB),
        // endregion
        // region 3. Ink
        inkMain = Color(0xFFFBFBFB),
        inkSubtle = Color(0xFF878787),
        inkOnBrand = Color(0xFFFBFBFB),
        // endregion
        // region 4. Outline
        outlineLow = Color(0xFF262626),
        outlineHigh = Color(0xFF878787),
        // endregion
        // region 5. Status
        success = Color(0xFF66BB6A),
        error = Color(0xFFEF5350),
        warning = Color(0xFFFDD835),
        // endregion
        // region 6. Interaction
        disabled = Color(0xFF424242),
        scrim = Color(0x80000000), // 50 % black overlay
        // endregion
    )
