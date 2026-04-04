package presentation.core.ui.source.kit.atom.container

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Renders one of two content slots depending on whether the available width is less
 * than the available height (portrait-like) or not (landscape-like).
 *
 * Useful for adaptive layouts that need to switch orientation of their children.
 *
 * @param modifier Modifier applied to the outer [BoxWithConstraints].
 * @param byHeight Content displayed when the container is taller than it is wide.
 * @param byWidth Content displayed when the container is wider than it is tall.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun MaxSideDetector(
    modifier: Modifier = Modifier,
    byHeight: @Composable () -> Unit,
    byWidth: @Composable () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (maxWidth < maxHeight) {
            true -> byHeight()
            false -> byWidth()
        }
    }
}
