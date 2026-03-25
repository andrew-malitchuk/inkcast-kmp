package presentation.core.ui.source.kit.atom.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.core.model.ButtonSizeValues

/**
 * Available size presets for buttons, controlling padding, icon size, and minimum height.
 */
public enum class ButtonSizeType {
    Small,
    Medium,
    Large,
    XLarge,
    XXLarge,
}

/**
 * Resolves this [ButtonSizeType] into concrete [ButtonSizeValues] for a standard text button.
 *
 * @param hasBorder Whether the button style includes a visible border (e.g. [ButtonStyle.Primary]).
 */
@Composable
internal fun ButtonSizeType.resolve(hasBorder: Boolean): ButtonSizeValues {
    val borderSize = if (hasBorder) Theme.spacing.spacingXXS else 0.dp
    return when (this) {
        ButtonSizeType.Small -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingL,
            borderSize = borderSize,
            contentPadding = PaddingValues(
                horizontal = Theme.spacing.spacingXL,
                vertical = Theme.spacing.spacingS,
            ),
            spacing = Theme.spacing.spacingS,
            minHeight = Theme.spacing.spacing2XL,
            loadingSize = Theme.spacing.spacingL,
        )

        ButtonSizeType.Medium -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingL,
            borderSize = borderSize,
            contentPadding = PaddingValues(
                horizontal = Theme.spacing.spacingXL,
                vertical = Theme.spacing.spacingM,
            ),
            spacing = Theme.spacing.spacingS,
            minHeight = Theme.spacing.spacing2XL + Theme.spacing.spacingS,
            loadingSize = Theme.spacing.spacingL,
        )

        ButtonSizeType.Large -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingXL,
            borderSize = borderSize,
            contentPadding = PaddingValues(
                horizontal = Theme.spacing.spacingXL,
                vertical = Theme.spacing.spacingM,
            ),
            spacing = Theme.spacing.spacingS,
            minHeight = Theme.spacing.spacing3XL,
            loadingSize = Theme.spacing.spacingXL,
        )

        ButtonSizeType.XLarge -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingXL,
            borderSize = borderSize,
            contentPadding = PaddingValues(
                horizontal = Theme.spacing.spacingXL,
                vertical = Theme.spacing.spacingL,
            ),
            spacing = Theme.spacing.spacingS,
            minHeight = Theme.spacing.spacing3XL + Theme.spacing.spacingS,
            loadingSize = Theme.spacing.spacingXL,
        )

        ButtonSizeType.XXLarge -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingXL,
            borderSize = borderSize,
            contentPadding = PaddingValues(
                horizontal = Theme.spacing.spacingXL,
                vertical = Theme.spacing.spacingXL,
            ),
            spacing = Theme.spacing.spacingS,
            minHeight = Theme.spacing.spacing4XL,
            loadingSize = Theme.spacing.spacingXL,
        )
    }
}

/**
 * Resolves this [ButtonSizeType] into concrete [ButtonSizeValues] for an icon-only button.
 */
@Composable
internal fun ButtonSizeType.resolveIcon(): ButtonSizeValues {
    return when (this) {
        ButtonSizeType.Small -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingL,
            borderSize = 0.dp,
            contentPadding = PaddingValues(all = Theme.spacing.spacingS),
            spacing = 0.dp,
            minHeight = Theme.spacing.spacing2XL,
            loadingSize = Theme.spacing.spacingL,
        )

        ButtonSizeType.Medium -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingL,
            borderSize = 0.dp,
            contentPadding = PaddingValues(all = Theme.spacing.spacingM),
            spacing = 0.dp,
            minHeight = Theme.spacing.spacing2XL + Theme.spacing.spacingS,
            loadingSize = Theme.spacing.spacingL,
        )

        ButtonSizeType.Large -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingXL,
            borderSize = 0.dp,
            contentPadding = PaddingValues(all = Theme.spacing.spacingM),
            spacing = 0.dp,
            minHeight = Theme.spacing.spacing3XL,
            loadingSize = Theme.spacing.spacingXL,
        )

        ButtonSizeType.XLarge -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingXL,
            borderSize = 0.dp,
            contentPadding = PaddingValues(all = Theme.spacing.spacingL),
            spacing = 0.dp,
            minHeight = Theme.spacing.spacing3XL + Theme.spacing.spacingS,
            loadingSize = Theme.spacing.spacingXL,
        )

        ButtonSizeType.XXLarge -> ButtonSizeValues(
            iconSize = Theme.spacing.spacingXL,
            borderSize = 0.dp,
            contentPadding = PaddingValues(all = Theme.spacing.spacingL),
            spacing = 0.dp,
            minHeight = Theme.spacing.spacing4XL,
            loadingSize = Theme.spacing.spacingXL,
        )
    }
}
