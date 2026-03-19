package presentation.core.styling.core

import androidx.compose.ui.graphics.Color

public data class ThemeColor(
    // region 1. Brand
    val brand: Color,
    val brandVariant: Color,
    // endregion
    // region 2. Surface
    val canvas: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val surfaceInverse: Color,
    // endregion
    // region 3. Ink
    val inkMain: Color,
    val inkSubtle: Color,
    val inkOnBrand: Color,
    // endregion
    // region 4. Outline
    val outlineLow: Color,
    val outlineHigh: Color,
    // endregion
    // region 5. Status
    val success: Color,
    val error: Color,
    val warning: Color,
    // endregion
    // region 6. Interaction
    val disabled: Color,
    val scrim: Color,
    // endregion
)
