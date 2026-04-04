package presentation.core.navigation.api.core.composition

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import presentation.core.navigation.api.source.destination.AppNavigator

/**
 * Provides the [AppNavigator] instance to the composition tree for screen-level navigation.
 *
 * @see AppNavigator
 */
public val LocalAppNavigator: ProvidableCompositionLocal<AppNavigator?> =
    compositionLocalOf { error("No App Navigator Provided") }

/**
 * Provides a back-navigation callback, typically bound to [AppNavigator.popBackStack].
 */
public val LocalBackAction: ProvidableCompositionLocal<() -> Unit> =
    compositionLocalOf { error("No Back Action Provided") }

/**
 * Holds a one-shot shared URL received via an external intent (e.g. Android share sheet).
 *
 * Call [consume] to atomically read and clear the URL so that it is handled only once.
 */
@Stable
public class SharedUrlState(initialUrl: String? = null) {
    public var url: String? by mutableStateOf(initialUrl)
        private set

    public fun consume(): String? {
        val current = url
        url = null
        return current
    }
}

/**
 * Provides a [SharedUrlState] for screens to consume one-shot shared URLs from external intents.
 */
public val LocalSharedUrl: ProvidableCompositionLocal<SharedUrlState> =
    compositionLocalOf { SharedUrlState() }
