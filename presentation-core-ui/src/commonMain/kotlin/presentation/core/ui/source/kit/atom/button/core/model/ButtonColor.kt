package presentation.core.ui.source.kit.atom.button.core.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

internal interface ButtonColor {
    @Composable
    fun borderColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color>

    @Composable
    fun foregroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color>

    @Composable
    fun backgroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color>
}
