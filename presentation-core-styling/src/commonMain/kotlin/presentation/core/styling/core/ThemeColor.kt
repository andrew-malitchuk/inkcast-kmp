package presentation.core.styling.core

import androidx.compose.ui.graphics.Color

/**
 * Design-token holder for the application color palette.
 *
 * Colors are grouped into semantic roles (brand, surface, ink, outline, status, interaction)
 * so that UI components reference roles rather than raw hex values. Light and dark palettes
 * each supply their own [ThemeColor] instance.
 *
 * @param brand Primary brand accent color used for key interactive elements.
 * @param brandVariant Secondary brand accent, typically a lighter or darker shade of [brand].
 * @param canvas Background color for the root app surface (screen-level background).
 * @param surface Background color for cards, sheets, and elevated containers.
 * @param surfaceVariant Alternative surface tone for visual separation of nested containers.
 * @param surfaceInverse Inverted surface color, used for high-contrast overlays or tooltips.
 * @param inkMain Primary text / icon color rendered on [canvas] or [surface].
 * @param inkSubtle Secondary text / icon color for less prominent content.
 * @param inkOnBrand Text / icon color rendered on top of [brand] backgrounds.
 * @param outlineLow Low-emphasis border or divider color.
 * @param outlineHigh High-emphasis border or divider color.
 * @param success Semantic color indicating a successful or positive state.
 * @param error Semantic color indicating an error or destructive state.
 * @param warning Semantic color indicating a cautionary or warning state.
 * @param disabled Color applied to disabled / inactive UI elements.
 * @param scrim Semi-transparent overlay color used behind modals and bottom sheets.
 * @see Theme.color
 */
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
