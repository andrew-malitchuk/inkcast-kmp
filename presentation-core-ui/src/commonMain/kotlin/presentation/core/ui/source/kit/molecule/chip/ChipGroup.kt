package presentation.core.ui.source.kit.molecule.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.chip.Chip

/**
 * A horizontally-wrapping group of selectable [Chip]s where only one can be active.
 *
 * This is classified as a **molecule** because it composes multiple [Chip] atoms
 * into a coordinated group with shared single-selection state.
 *
 * @param items List of chip labels.
 * @param selectedIndex Index of the currently selected chip.
 * @param onSelect Callback with the tapped chip index.
 * @param modifier Modifier to be applied to the [FlowRow].
 *
 * @see Chip
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun ChipGroup(
    items: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
    ) {
        items.forEachIndexed { index, label ->
            Chip(
                label = label,
                selected = index == selectedIndex,
                onClick = { onSelect(index) },
            )
        }
    }
}
