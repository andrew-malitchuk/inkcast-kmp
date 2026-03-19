package presentation.core.ui.source.kit.atom.button.core.draw

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
internal fun DrawIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    backgroundColor: Color,
    iconColor: Color,
    borderColor: Color,
    corner: Dp,
    iconSize: Dp,
    loadingSize: Dp,
    borderSize: Dp,
    minHeight: Dp,
    paddings: PaddingValues,
    isLoading: Boolean,
    horizontalArrangement: Alignment = Alignment.Center,
) {
    DrawBaseButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        corner = corner,
        borderSize = borderSize,
        paddings = paddings,
        isLoading = isLoading,
        loadingSize = loadingSize,
        horizontalArrangement = horizontalArrangement,
    ) {
        Box(
            modifier =
            Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(iconSize),
            )
        }
    }
}
