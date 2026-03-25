package presentation.core.ui.source.kit.atom.snackbar.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarDuration

/**
 * Sealed hierarchy representing the data model for a single snackbar in the stack.
 *
 * @property showDuration How long the snackbar should remain visible before auto-dismiss.
 */
@Stable
internal sealed class StackedSnackbarData(val showDuration: StackedSnackbarDuration) {
    /**
     * Standard text-based snackbar with an optional description and action button.
     */
    data class Normal(
        val title: String,
        val description: String? = null,
        val actionTitle: String? = null,
        val action: (() -> Unit)? = null,
        val duration: StackedSnackbarDuration = StackedSnackbarDuration.Short,
    ) : StackedSnackbarData(duration)

    /**
     * Fully custom snackbar whose content is provided by the caller.
     */
    data class Custom(
        val content: @Composable (() -> Unit) -> Unit,
        val duration: StackedSnackbarDuration = StackedSnackbarDuration.Short,
    ) : StackedSnackbarData(duration)
}
