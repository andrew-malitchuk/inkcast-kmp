package presentation.core.ui.source.kit.atom.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

/**
 * Creates and remembers a [StackedSnakbarHostState] scoped to the current composition.
 *
 * @param maxStack Maximum number of snackbars visible simultaneously.
 * @param animation Animation preset for enter / exit transitions.
 * @return A remembered [StackedSnakbarHostState] instance.
 *
 * @see StackedSnakbarHostState
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun rememberStackedSnackbarHostState(
    maxStack: Int = Int.MAX_VALUE,
    animation: StackedSnackbarAnimation = StackedSnackbarAnimation.Bounce,
): StackedSnakbarHostState = run {
    val scope = rememberCoroutineScope()
    remember {
        StackedSnakbarHostState(animation = animation, maxStack = maxStack, coroutinesScope = scope)
    }
}
