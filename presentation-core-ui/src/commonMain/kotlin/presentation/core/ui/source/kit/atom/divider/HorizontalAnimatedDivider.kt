package presentation.core.ui.source.kit.atom.divider

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import presentation.core.styling.core.Theme

/**
 * A full-width horizontal divider that fades in and out with an alpha animation.
 *
 * @param modifier Modifier applied to the divider [Box].
 * @param isVisible Controls whether the divider is visible; the transition is animated.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun HorizontalAnimatedDivider(modifier: Modifier = Modifier, isVisible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
    )

    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .alpha(alpha),

    )
}
