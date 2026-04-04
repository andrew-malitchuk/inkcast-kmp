package dev.yaxca.io.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.Button
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.ButtonStyle
import presentation.core.ui.source.kit.atom.button.IconButton
import presentation.core.ui.source.kit.atom.button.toggle.Toggle
import presentation.core.ui.source.kit.atom.container.IconContainer
import presentation.core.ui.source.kit.atom.divider.HorizontalAnimatedDivider
import presentation.core.ui.source.kit.atom.icon.BookOpen
import presentation.core.ui.source.kit.atom.icon.FileText
import presentation.core.ui.source.kit.atom.icon.Folder
import presentation.core.ui.source.kit.atom.icon.Home
import presentation.core.ui.source.kit.atom.icon.Image
import presentation.core.ui.source.kit.atom.icon.Lock
import presentation.core.ui.source.kit.atom.icon.MoreVertical
import presentation.core.ui.source.kit.atom.icon.Settings
import presentation.core.ui.source.kit.atom.icon.Smartphone
import presentation.core.ui.source.kit.atom.input.Input
import presentation.core.ui.source.kit.atom.progress.WavyProgressIndicator
import presentation.core.ui.source.kit.atom.slider.WavySlider
import presentation.core.ui.source.kit.molecule.bar.tab.TabBar
import presentation.core.ui.source.kit.molecule.bar.tab.TabBarItem
import presentation.core.ui.source.kit.molecule.header.SimpleHeader
import presentation.core.ui.source.kit.molecule.item.ItemCard
import presentation.core.ui.source.kit.molecule.item.ItemCardType

/**
 * Renders a scrollable catalogue of all reusable UI components (atoms and molecules).
 *
 * Demonstrates buttons, toggles, inputs, sliders, progress indicators, icons,
 * headers, item cards, and tab bars in various configurations.
 * Intended for developer use during UI-kit validation. Not shipped in production builds.
 *
 * @see DemoHost
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DemoUiKitScreen() {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.canvas)
            .verticalScroll(scrollState)
            .padding(Theme.spacing.spacingL),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingXL),
    ) {
        // ── Header ──
        Text(
            text = "UI Kit",
            style = Theme.typography.display,
            color = Theme.color.inkMain,
        )

        // ══════════════════════════════════════
        // ATOMS
        // ══════════════════════════════════════

        SectionTitle("Atoms")

        // ── Buttons: Primary ──
        SubSectionTitle("Button — Primary")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            ButtonSizeType.entries.forEach { size ->
                Button(
                    text = size.name,
                    onClick = {},
                    style = ButtonStyle.Primary,
                    size = size,
                )
            }
        }
        Button(
            text = "Primary Disabled",
            onClick = {},
            style = ButtonStyle.Primary,
            size = ButtonSizeType.Medium,
            enabled = false,
        )

        // ── Buttons: Secondary ──
        SubSectionTitle("Button — Secondary")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            ButtonSizeType.entries.forEach { size ->
                Button(
                    text = size.name,
                    onClick = {},
                    style = ButtonStyle.Secondary,
                    size = size,
                )
            }
        }

        // ── Buttons: Text ──
        SubSectionTitle("Button — Text")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            Button(
                text = "Text Button",
                onClick = {},
                style = ButtonStyle.Text,
                size = ButtonSizeType.Medium,
            )
            Button(
                text = "Disabled",
                onClick = {},
                style = ButtonStyle.Text,
                size = ButtonSizeType.Medium,
                enabled = false,
            )
        }

        // ── Buttons with icons ──
        SubSectionTitle("Button — With Icons")
        Button(
            text = "Start Icon",
            onClick = {},
            style = ButtonStyle.Secondary,
            size = ButtonSizeType.Large,
            startIcon = BookOpen,
        )
        Button(
            text = "End Icon",
            onClick = {},
            style = ButtonStyle.Secondary,
            size = ButtonSizeType.Large,
            endIcon = FileText,
        )

        // ── IconButton ──
        SubSectionTitle("IconButton")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            IconButton(icon = BookOpen, onClick = {}, size = ButtonSizeType.Small)
            IconButton(icon = FileText, onClick = {}, size = ButtonSizeType.Medium)
            IconButton(icon = Folder, onClick = {}, size = ButtonSizeType.Large)
            IconButton(icon = Image, onClick = {}, size = ButtonSizeType.XLarge)
            IconButton(
                icon = MoreVertical,
                onClick = {},
                size = ButtonSizeType.Medium,
                enabled = false,
            )
        }

        // ── Toggle ──
        SubSectionTitle("Toggle")
        Row(horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingL)) {
            var toggleOn by remember { mutableStateOf(true) }
            var toggleOff by remember { mutableStateOf(false) }

            Toggle(checked = toggleOn, onCheckedChange = { toggleOn = it })
            Toggle(checked = toggleOff, onCheckedChange = { toggleOff = it })
            Toggle(checked = true, onCheckedChange = {}, enabled = false)
            Toggle(checked = false, onCheckedChange = {}, enabled = false)
        }

        // ── Input ──
        SubSectionTitle("Input")
        Input(
            modifier = Modifier.fillMaxWidth(),
            initialText = "",
            onTextChanged = {},
            placeholder = "Placeholder text...",
            clearIcon = MoreVertical,
        )
        Input(
            modifier = Modifier.fillMaxWidth(),
            initialText = "Prefilled text",
            onTextChanged = {},
            placeholder = "",
        )
        Input(
            modifier = Modifier.fillMaxWidth(),
            initialText = "",
            onTextChanged = {},
            placeholder = "Disabled input",
            enabled = false,
        )
        Input(
            modifier = Modifier.fillMaxWidth(),
            initialText = "bad",
            onTextChanged = {},
            placeholder = "",
            validationRegex = Regex("^.{5,}$"),
        )

        // ── WavyProgressIndicator ──
        SubSectionTitle("WavyProgressIndicator")
        WavyProgressIndicator(
            progress = 0.3f,
            modifier = Modifier.fillMaxWidth(),
            activeColor = Theme.color.brand,
            inactiveColor = Theme.color.outlineLow,
        )
        WavyProgressIndicator(
            progress = 0.6f,
            modifier = Modifier.fillMaxWidth(),
            activeColor = Theme.color.success,
            inactiveColor = Theme.color.outlineLow,
        )
        WavyProgressIndicator(
            progress = 1f,
            modifier = Modifier.fillMaxWidth(),
            activeColor = Theme.color.brand,
            inactiveColor = Theme.color.outlineLow,
        )

        // ── WavySlider ──
        SubSectionTitle("WavySlider")
        var sliderValue by remember { mutableFloatStateOf(0.4f) }
        WavySlider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            modifier = Modifier.fillMaxWidth(),
            activeColor = Theme.color.brand,
            inactiveColor = Theme.color.outlineLow,
            thumbColor = Theme.color.brand,
        )
        Text(
            text = "Value: ${((sliderValue * 100).toInt() / 100.0)}",
            style = Theme.typography.caption,
            color = Theme.color.inkSubtle,
        )

        var sliderDisabled by remember { mutableFloatStateOf(0.7f) }
        WavySlider(
            value = sliderDisabled,
            onValueChange = { sliderDisabled = it },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            activeColor = Theme.color.disabled,
            inactiveColor = Theme.color.outlineLow,
            thumbColor = Theme.color.disabled,
        )

        // ── Divider ──
        SubSectionTitle("HorizontalAnimatedDivider")
        HorizontalAnimatedDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Theme.color.outlineLow),
            isVisible = true,
        )

        // ── IconContainer ──
        SubSectionTitle("IconContainer")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
        ) {
            IconContainer(
                modifier = Modifier.size(48.dp),
                icon = BookOpen,
                backgroundColor = Theme.color.surfaceVariant,
                foregroundColor = Theme.color.inkMain,
            )
            IconContainer(
                modifier = Modifier.size(48.dp),
                icon = FileText,
                backgroundColor = Theme.color.brand,
                foregroundColor = Theme.color.inkOnBrand,
            )
            IconContainer(
                modifier = Modifier.size(48.dp),
                icon = Folder,
                backgroundColor = Theme.color.success,
                foregroundColor = Theme.color.inkOnBrand,
            )
            IconContainer(
                modifier = Modifier.size(48.dp),
                icon = Image,
                backgroundColor = Theme.color.warning,
                foregroundColor = Theme.color.inkMain,
            )
        }

        // ── Icons ──
        SubSectionTitle("Custom Icons")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
        ) {
            listOf(
                "BookOpen" to BookOpen,
                "FileText" to FileText,
                "Folder" to Folder,
                "Home" to Home,
                "Image" to Image,
                "Lock" to Lock,
                "MoreVertical" to MoreVertical,
                "Settings" to Settings,
                "Smartphone" to Smartphone,
            ).forEach { (name, icon) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        imageVector = icon,
                        contentDescription = name,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Theme.color.inkMain),
                    )
                    Text(
                        text = name,
                        style = Theme.typography.caption,
                        color = Theme.color.inkSubtle,
                    )
                }
            }
        }

        // ══════════════════════════════════════
        // MOLECULES
        // ══════════════════════════════════════

        SectionTitle("Molecules")

        // ── SimpleHeader ──
        SubSectionTitle("SimpleHeader")
        SimpleHeader(title = "Section Header")

        // ── ItemCard ──
        SubSectionTitle("ItemCard")
        Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS)) {
            ItemCard(
                title = "My Book",
                description = "A short description of the book",
                type = ItemCardType.Book,
                onCallback = {},
            )
            ItemCard(
                title = "Vacation Photos",
                description = "12 items",
                type = ItemCardType.Image,
                onCallback = {},
            )
            ItemCard(
                title = "Documents",
                description = "3 items",
                type = ItemCardType.Folder,
                onCallback = {},
            )
        }

        // ── TabBar ──
        SubSectionTitle("TabBar")
        Text(
            text = "Tap a tab to switch. Re-tap active tab to scroll to top.",
            style = Theme.typography.caption,
            color = Theme.color.inkSubtle,
        )

        var tabIndex by remember { mutableIntStateOf(0) }
        val tabItems = remember {
            listOf(
                TabBarItem(icon = Home, contentDescription = "Home"),
                TabBarItem(icon = FileText, contentDescription = "Files"),
                TabBarItem(icon = Lock, contentDescription = "Privacy"),
                TabBarItem(icon = Smartphone, contentDescription = "Device"),
            )
        }

        TabBar(
            items = tabItems,
            selectedIndex = tabIndex,
            onItemClick = { tabIndex = it },
            onItemReselect = {
                // Demo: scroll the whole page back to top on re-select.
                scope.launch { scrollState.animateScrollTo(0) }
            },
            modifier = Modifier.padding(horizontal = Theme.spacing.spacingL),
        )

        // Floating-style preview inside a dark box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Theme.color.canvas,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                )
                .padding(Theme.spacing.spacingXL),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Active tab: ${tabItems[tabIndex].contentDescription}",
                style = Theme.typography.body,
                color = Theme.color.inkMain,
            )
        }

        Spacer(Modifier.height(Theme.spacing.spacing3XL))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = Theme.typography.title,
        color = Theme.color.inkMain,
    )
}

@Composable
private fun SubSectionTitle(text: String) {
    Text(
        text = text,
        style = Theme.typography.bodyEmphasis,
        color = Theme.color.inkSubtle,
    )
}
