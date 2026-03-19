package presentation.core.ui.source.kit.atom.button.core.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
internal data class ButtonSizeValues(
    val iconSize: Dp,
    val borderSize: Dp,
    val contentPadding: PaddingValues,
    val spacing: Dp,
    val minHeight: Dp,
    val loadingSize: Dp,
)
