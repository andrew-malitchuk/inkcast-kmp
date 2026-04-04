package presentation.core.ui.source.kit.atom.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.core.ext.has
import presentation.core.ui.source.kit.atom.button.core.model.ButtonColor
import presentation.core.ui.source.kit.atom.button.core.model.ButtonInteractionState

/**
 * Sealed hierarchy of button visual styles, each providing its own color scheme and corner radius.
 *
 * - [Primary] -- outlined with an ink-colored border.
 * - [Secondary] -- filled surface-variant background.
 * - [Text] -- transparent background, brand-colored label.
 */
public sealed class ButtonStyle {

    /** Resolves the color scheme for this style. */
    @Composable
    internal abstract fun colors(): ButtonColor

    /** Resolves the corner radius based on the button's minimum height. */
    @Composable
    internal abstract fun corner(minHeight: Dp): Dp

    /** Outlined primary style with a pill-shaped corner. */
    public data object Primary : ButtonStyle() {
        @Composable
        override fun colors(): ButtonColor = PrimaryButtonColors

        @Composable
        override fun corner(minHeight: Dp): Dp = minHeight / 2
    }

    /** Filled secondary style with a pill-shaped corner. */
    public data object Secondary : ButtonStyle() {
        @Composable
        override fun colors(): ButtonColor = SecondaryButtonColors

        @Composable
        override fun corner(minHeight: Dp): Dp = minHeight / 2
    }

    /** Text-only style with no corner rounding. */
    public data object Text : ButtonStyle() {
        @Composable
        override fun colors(): ButtonColor = TextButtonColors

        @Composable
        override fun corner(minHeight: Dp): Dp = 0.dp
    }
}

/** Color scheme for [ButtonStyle.Primary]: transparent background, ink foreground, ink border. */
private object PrimaryButtonColors : ButtonColor {
    @Composable
    override fun borderColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> Color.Transparent
                interactionState has ButtonInteractionState.PRESSED -> Theme.color.brand
                else -> Theme.color.inkMain
            },
        )

    @Composable
    override fun foregroundColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> Theme.color.inkSubtle
                interactionState has ButtonInteractionState.PRESSED -> Theme.color.brand
                else -> Theme.color.inkMain
            },
        )

    @Composable
    override fun backgroundColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(Color.Transparent)
}

/** Color scheme for [ButtonStyle.Secondary]: surface-variant background, brand foreground. */
private object SecondaryButtonColors : ButtonColor {
    @Composable
    override fun borderColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(Color.Transparent)

    @Composable
    override fun foregroundColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> Theme.color.disabled
                interactionState has ButtonInteractionState.PRESSED -> Theme.color.brandVariant
                else -> Theme.color.brand
            },
        )

    @Composable
    override fun backgroundColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> Theme.color.surfaceVariant.copy(alpha = 0.5f)
                interactionState has ButtonInteractionState.PRESSED -> Theme.color.outlineLow
                else -> Theme.color.surfaceVariant
            },
        )
}

/** Color scheme for [ButtonStyle.Text]: transparent background, brand foreground. */
private object TextButtonColors : ButtonColor {
    @Composable
    override fun borderColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(Color.Transparent)

    @Composable
    override fun foregroundColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> Theme.color.disabled
                interactionState has ButtonInteractionState.PRESSED -> Theme.color.brandVariant
                else -> Theme.color.brand
            },
        )

    @Composable
    override fun backgroundColor(
        interactionState: Int,
        enabled: Boolean,
        loading: Boolean
    ): State<Color> =
        rememberUpdatedState(
            when {
                !enabled -> Color.Transparent
                interactionState has ButtonInteractionState.PRESSED -> Theme.color.outlineLow
                else -> Color.Transparent
            },
        )
}
