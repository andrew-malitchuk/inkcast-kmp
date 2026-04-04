package presentation.core.ui.source.kit.atom.button.core.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

/**
 * Contract for resolving button colors based on interaction state, enabled flag, and loading flag.
 *
 * Implementations provide reactive [State] values so that Compose can animate between
 * states automatically.
 */
internal interface ButtonColor {
    /** Border color for the current interaction state. */
    @Composable
    fun borderColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color>

    /** Foreground (text / icon) color for the current interaction state. */
    @Composable
    fun foregroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color>

    /** Background (container) color for the current interaction state. */
    @Composable
    fun backgroundColor(interactionState: Int, enabled: Boolean, loading: Boolean): State<Color>
}
