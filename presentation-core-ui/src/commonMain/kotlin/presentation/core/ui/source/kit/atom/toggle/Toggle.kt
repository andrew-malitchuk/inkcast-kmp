package presentation.core.ui.source.kit.atom.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme

/**
 * A lightweight toggle switch with animated thumb and track color transition.
 *
 * For the full design-system toggle with drag gestures, custom colors, and
 * Cupertino-style animation, use
 * [presentation.core.ui.source.kit.atom.button.toggle.Toggle] instead.
 *
 * @param checked Current toggle state.
 * @param onCheckedChange Callback for toggle state changes.
 * @param modifier Modifier to be applied to the root [Box].
 * @param enabled When `false`, the toggle ignores input and renders at reduced opacity.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun SimpleToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val trackWidth = 48.dp
    val trackHeight = 28.dp
    val thumbSize = 22.dp
    val thumbPadding = 3.dp

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbSize - thumbPadding else thumbPadding,
        animationSpec = tween(durationMillis = 200),
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) Theme.color.inkMain else Theme.color.outlineLow,
        animationSpec = tween(durationMillis = 200),
    )

    val thumbColor = if (checked) Theme.color.surface else Theme.color.surface

    Box(
        modifier = modifier
            .width(trackWidth)
            .height(trackHeight)
            .clip(CircleShape)
            .background(if (enabled) trackColor else Theme.color.disabled)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = enabled,
            ) { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(thumbSize)
                .clip(CircleShape)
                .background(thumbColor),
        )
    }
}
