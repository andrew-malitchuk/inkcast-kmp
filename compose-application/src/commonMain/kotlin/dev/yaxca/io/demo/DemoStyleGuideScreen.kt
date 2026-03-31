package dev.yaxca.io.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.shape.SquircleShape

/**
 * Renders a scrollable catalogue of all design tokens: colors, typography, spacing, and shapes.
 *
 * Intended for developer use during design-system validation. Not shipped in production builds.
 *
 * @see DemoHost
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DemoStyleGuideScreen() {
    val scrollState = rememberScrollState()

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
            text = "Style Guide",
            style = Theme.typography.display,
            color = Theme.color.inkMain,
        )

        // ── 1. Color Palette ──
        SectionTitle("Colors")

        ColorGroup("Brand") {
            ColorSwatch("brand", Theme.color.brand)
            ColorSwatch("brandVariant", Theme.color.brandVariant)
        }
        ColorGroup("Surface") {
            ColorSwatch("canvas", Theme.color.canvas)
            ColorSwatch("surface", Theme.color.surface)
            ColorSwatch("surfaceVariant", Theme.color.surfaceVariant)
        }
        ColorGroup("Ink") {
            ColorSwatch("inkMain", Theme.color.inkMain)
            ColorSwatch("inkSubtle", Theme.color.inkSubtle)
            ColorSwatch("inkOnBrand", Theme.color.inkOnBrand)
        }
        ColorGroup("Outline") {
            ColorSwatch("outlineLow", Theme.color.outlineLow)
            ColorSwatch("outlineHigh", Theme.color.outlineHigh)
        }
        ColorGroup("Status") {
            ColorSwatch("success", Theme.color.success)
            ColorSwatch("error", Theme.color.error)
            ColorSwatch("warning", Theme.color.warning)
        }
        ColorGroup("Interaction") {
            ColorSwatch("disabled", Theme.color.disabled)
            ColorSwatch("scrim", Theme.color.scrim)
        }

        // ── 2. Typography ──
        SectionTitle("Typography")

        TypographySample("Display") {
            Text(
                text = "The quick brown fox",
                style = Theme.typography.display,
                color = Theme.color.inkMain,
            )
        }
        TypographySample("Title") {
            Text(
                text = "The quick brown fox",
                style = Theme.typography.title,
                color = Theme.color.inkMain,
            )
        }
        TypographySample("Body") {
            Text(
                text = "The quick brown fox jumps over the lazy dog.",
                style = Theme.typography.body,
                color = Theme.color.inkMain,
            )
        }
        TypographySample("Body Emphasis") {
            Text(
                text = "The quick brown fox jumps over the lazy dog.",
                style = Theme.typography.bodyEmphasis,
                color = Theme.color.inkMain,
            )
        }
        TypographySample("Label") {
            Text(
                text = "Label text sample",
                style = Theme.typography.label,
                color = Theme.color.inkMain,
            )
        }
        TypographySample("Caption") {
            Text(
                text = "Caption text sample",
                style = Theme.typography.caption,
                color = Theme.color.inkSubtle,
            )
        }
        TypographySample("Action") {
            Text(
                text = "ACTION TEXT",
                style = Theme.typography.action,
                color = Theme.color.brand,
            )
        }

        // ── 3. Spacing Scale ──
        SectionTitle("Spacing")

        SpacingRow("XXS", Theme.spacing.spacingXXS)
        SpacingRow("XS", Theme.spacing.spacingXS)
        SpacingRow("S", Theme.spacing.spacingS)
        SpacingRow("M", Theme.spacing.spacingM)
        SpacingRow("L", Theme.spacing.spacingL)
        SpacingRow("XL", Theme.spacing.spacingXL)
        SpacingRow("2XL", Theme.spacing.spacing2XL)
        SpacingRow("3XL", Theme.spacing.spacing3XL)
        SpacingRow("4XL", Theme.spacing.spacing4XL)
        SpacingRow("5XL", Theme.spacing.spacing5XL)

        // ── 4. Shapes ──
        SectionTitle("Shapes (Squircle)")

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
        ) {
            listOf(4.dp, 8.dp, 12.dp, 16.dp, 24.dp).forEach { radius ->
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(SquircleShape(radius))
                        .background(Theme.color.brand),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "${radius.value.toInt()}",
                        style = Theme.typography.caption,
                        color = Theme.color.inkOnBrand,
                    )
                }
            }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorGroup(name: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS)) {
        Text(
            text = name,
            style = Theme.typography.bodyEmphasis,
            color = Theme.color.inkMain,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingS),
        ) {
            content()
        }
    }
}

@Composable
private fun ColorSwatch(label: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingXS),
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(SquircleShape(Theme.spacing.spacingS))
                .background(color)
                .border(
                    width = Theme.spacing.spacingXXS,
                    color = Theme.color.outlineLow,
                    shape = SquircleShape(Theme.spacing.spacingS),
                ),
        )
        Text(
            text = label,
            style = Theme.typography.caption,
            color = Theme.color.inkSubtle,
        )
    }
}

@Composable
private fun TypographySample(name: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.spacingXXS)) {
        Text(
            text = name,
            style = Theme.typography.caption,
            color = Theme.color.inkSubtle,
        )
        content()
    }
}

@Composable
private fun SpacingRow(name: String, size: Dp) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.spacingM),
    ) {
        Text(
            text = name,
            style = Theme.typography.caption,
            color = Theme.color.inkSubtle,
            modifier = Modifier.width(32.dp),
        )
        Box(
            modifier = Modifier
                .height(Theme.spacing.spacingL)
                .width(size)
                .clip(SquircleShape(2.dp))
                .background(Theme.color.brand),
        )
        Text(
            text = "${size.value.toInt()}dp",
            style = Theme.typography.caption,
            color = Theme.color.inkSubtle,
        )
    }
}
