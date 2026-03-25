package presentation.core.ui.source.kit.atom.button.core.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * Concrete dimension values resolved from a [ButtonSizeType], used by the draw layer
 * to size icons, padding, borders, and the loading indicator.
 *
 * @property iconSize Side length of the leading / trailing icon.
 * @property borderSize Stroke width of the button border.
 * @property contentPadding Inner padding between the border and the content.
 * @property spacing Horizontal gap between the icon and the label text.
 * @property minHeight Minimum height constraint of the button.
 * @property loadingSize Diameter of the loading indicator.
 */
@Immutable
internal data class ButtonSizeValues(
    val iconSize: Dp,
    val borderSize: Dp,
    val contentPadding: PaddingValues,
    val spacing: Dp,
    val minHeight: Dp,
    val loadingSize: Dp,
)
