package presentation.core.ui.source.kit.atom.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import presentation.core.ui.source.kit.atom.snackbar.internal.StackedSnackbar
import presentation.core.ui.source.kit.atom.snackbar.internal.StackedSnackbarData

/**
 * Composable host that observes [StackedSnakbarHostState] and renders stacked snackbars.
 *
 * Auto-dismisses the top snackbar after its configured duration and supports
 * manual swipe-to-dismiss via the internal [StackedSnackbar] component.
 *
 * @param hostState State holder driving the snackbar queue.
 * @param modifier Modifier applied to the host container.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun StackedSnackbarHost(hostState: StackedSnakbarHostState, modifier: Modifier = Modifier) {
    val firstItemVisible by hostState.newSnackbarHosted.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(hostState.currentSnackbarData) {
        val data = hostState.currentSnackbarData
        data.firstOrNull()?.let {
            delay(it.showDuration.toMillis())
            if (data.size == 1) {
                hostState.newSnackbarHosted.value = false
                delay(500)
                hostState.currentSnackbarData =
                    hostState.currentSnackbarData.toMutableList().apply {
                        remove(it)
                    }
                hostState.newSnackbarHosted.value = true
            }
        }
    }
    if (hostState.currentSnackbarData.isNotEmpty()) {
        StackedSnackbar(
            snackbarData = hostState.currentSnackbarData.toList(),
            onSnackbarRemoved = {
                hostState.newSnackbarHosted.value = false
                coroutineScope.launch {
                    delay(500)
                    hostState.currentSnackbarData =
                        hostState.currentSnackbarData.toMutableList().apply {
                            removeLastOrNull()
                        }
                    hostState.newSnackbarHosted.value = true
                }
            },
            firstItemVisibility = firstItemVisible,
            maxStack = hostState.maxStack,
            animation = hostState.animation,
            modifier = modifier,
        )
    }
}

/**
 * State holder for a stacked snackbar host, managing the queue of visible snackbars.
 *
 * @param coroutinesScope Scope used for internal delay-based scheduling.
 * @param animation Animation preset applied to all hosted snackbars.
 * @param maxStack Maximum number of snackbars rendered simultaneously.
 */
@Stable
public class StackedSnakbarHostState(
    private val coroutinesScope: CoroutineScope,
    public val animation: StackedSnackbarAnimation,
    public val maxStack: Int = Int.MAX_VALUE,
) {
    internal var currentSnackbarData by mutableStateOf<List<StackedSnackbarData>>(emptyList())
    internal val newSnackbarHosted = MutableStateFlow(false)

    /**
     * Enqueues a normal snackbar with a title, optional description, and optional action button.
     *
     * @param title Primary text displayed in the snackbar.
     * @param description Optional secondary text.
     * @param actionTitle Optional label for the action button.
     * @param action Callback invoked when the action button is tapped.
     * @param duration How long the snackbar remains visible.
     */
    public fun showSnackbar(
        title: String,
        description: String? = null,
        actionTitle: String? = null,
        action: (() -> Unit)? = null,
        duration: StackedSnackbarDuration = StackedSnackbarDuration.Indefinite,
    ) {
        showSnackbar(
            data =
            StackedSnackbarData.Normal(
                title,
                description,
                actionTitle,
                action,
                duration,
            ),
        )
    }

    /**
     * Enqueues a custom snackbar whose content is provided by the caller.
     *
     * @param content Composable content receiving a dismiss callback.
     * @param duration How long the snackbar remains visible.
     */
    public fun showCustomSnackbar(
        content: @Composable (() -> Unit) -> Unit,
        duration: StackedSnackbarDuration = StackedSnackbarDuration.Indefinite,
    ) {
        showSnackbar(
            data = StackedSnackbarData.Custom(content, duration),
        )
    }

    private fun showSnackbar(data: StackedSnackbarData) {
        newSnackbarHosted.value = false
        currentSnackbarData =
            currentSnackbarData.toMutableList().apply {
                if (!contains(data)) {
                    add(data)
                }
            }
        @Suppress("MagicNumber")
        coroutinesScope.launch {
            delay(1_00)
            newSnackbarHosted.value = true
        }
    }
}

@Suppress("MagicNumber")
private fun StackedSnackbarDuration.toMillis(): Long = when (this) {
    StackedSnackbarDuration.Short -> 4000L
    StackedSnackbarDuration.Long -> 10000L
    StackedSnackbarDuration.Indefinite -> Long.MAX_VALUE
}
