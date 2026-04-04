package presentation.core.ui.source.kit.molecule.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import presentation.core.styling.core.Theme

/**
 * A single-choice segmented button group following Material 3 guidelines.
 *
 * Wraps [SingleChoiceSegmentedButtonRow] with project design-system tokens
 * for consistent theming across the app.
 *
 * This is classified as a **molecule** because it composes multiple
 * [SegmentedButton] atoms into a coordinated group with shared selection state.
 *
 * @param items List of segment labels.
 * @param selectedIndex Index of the currently selected segment.
 * @param onSelect Callback with the tapped segment index.
 * @param modifier Modifier applied to the row.
 *
 * @see <a href="https://m3.material.io/components/button-groups/overview">Material 3 Button Groups</a>
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun SegmentedButtonGroup(
    items: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        items.forEachIndexed { index, label ->
            val borderColor = if (index == selectedIndex) {
                Theme.color.inkMain
            } else {
                Theme.color.outlineLow
            }
            SegmentedButton(
                selected = index == selectedIndex,
                onClick = { onSelect(index) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = items.size,
                ),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = Theme.color.inkMain,
                    activeContentColor = Theme.color.surface,
                    inactiveContainerColor = Theme.color.surface,
                    inactiveContentColor = Theme.color.inkMain,
                    activeBorderColor = Theme.color.inkMain,
                    inactiveBorderColor = Theme.color.outlineLow,
                ),
                border = BorderStroke(
                    width = Theme.spacing.spacingXXS,
                    color = borderColor,
                ),
                icon = {},
            ) {
                Text(
                    text = label,
                    style = Theme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
