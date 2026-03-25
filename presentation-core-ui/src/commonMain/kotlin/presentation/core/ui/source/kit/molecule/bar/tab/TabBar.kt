package presentation.core.ui.source.kit.molecule.bar.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme

/**
 * Data describing a single tab inside [TabBar].
 *
 * @param icon The [ImageVector] icon displayed for this tab.
 * @param contentDescription Accessibility label for the icon.
 */
public data class TabBarItem(
    val icon: ImageVector,
    val contentDescription: String,
)

/**
 * A pill-shaped bottom tab bar with animated icon tinting.
 *
 * Behaviour contract consumed by the host:
 * - **Tab switch** — [onItemClick] is called when the user taps a tab that is *not* currently
 *   selected. The host should change the visible content.
 * - **Tab re-select** — [onItemReselect] is called when the user taps the *already selected* tab.
 *   The host should scroll the current content to the top; if the content is already at the top
 *   and the tab has its own back-stack, it should pop to the root.
 *
 * @param items Ordered list of tabs to render.
 * @param selectedIndex Zero-based index of the currently active tab.
 * @param onItemClick Called with the index when the user taps an *unselected* tab.
 * @param onItemReselect Called with the index when the user taps the *already selected* tab.
 * @param modifier [Modifier] applied to the root [Row].
 * @param backgroundColor Fill color of the pill container.
 * @param activeColor Icon tint for the selected tab.
 * @param inactiveColor Icon tint for unselected tabs.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun TabBar(
    items: List<TabBarItem>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onItemReselect: (Int) -> Unit = {},
    backgroundColor: Color = Theme.color.surfaceInverse,
    activeColor: Color = Theme.color.canvas,
    inactiveColor: Color = Theme.color.inkSubtle,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(backgroundColor)
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedIndex
            val animatedTint by animateColorAsState(
                targetValue = if (isSelected) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = 250),
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        if (isSelected) {
                            onItemReselect(index)
                        } else {
                            onItemClick(index)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    tint = animatedTint,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}
