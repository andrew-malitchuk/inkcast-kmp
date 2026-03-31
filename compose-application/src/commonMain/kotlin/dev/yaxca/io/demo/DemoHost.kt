package dev.yaxca.io.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.core.styling.core.Theme

/**
 * Available tabs within the demo mode host.
 *
 * @property label Human-readable tab title displayed in the tab bar.
 */
private enum class DemoTab(val label: String) {
    /** Showcases design tokens: colors, typography, spacing, shapes. */
    StyleGuide("Style Guide"),
    /** Showcases reusable UI components: atoms, molecules, organisms. */
    UiKit("UI Kit"),
}

/**
 * Hosts the demo/style-guide screens behind a tab bar.
 *
 * Used only when `DEMO_MODE` is enabled in [App]. Manages its own
 * tab selection state internally for simplicity.
 *
 * @see DemoStyleGuideScreen
 * @see DemoUiKitScreen
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
internal fun DemoHost() {
    var selectedTab by remember { mutableStateOf(DemoTab.StyleGuide) }

    Column(modifier = Modifier.fillMaxSize().background(Theme.color.canvas)) {
        // Tab bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.surface)
                .padding(horizontal = Theme.spacing.spacingL, vertical = Theme.spacing.spacingM),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingXL),
        ) {
            DemoTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .clickable { selectedTab = tab }
                        .padding(vertical = Theme.spacing.spacingS),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = tab.label,
                        style = if (isSelected) Theme.typography.bodyEmphasis else Theme.typography.body,
                        color = if (isSelected) Theme.color.brand else Theme.color.inkSubtle,
                    )
                }
            }
        }

        // Content
        when (selectedTab) {
            DemoTab.StyleGuide -> DemoStyleGuideScreen()
            DemoTab.UiKit -> DemoUiKitScreen()
        }
    }
}
