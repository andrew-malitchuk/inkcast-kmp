package presentation.core.ui.source.kit.atom.button.core.draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

@Composable
internal fun DrawButton(
    modifier: Modifier = Modifier,
    text: String,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    backgroundColor: Color,
    foregroundColor: Color,
    borderColor: Color,
    corner: Dp,
    iconSize: Dp,
    loadingSize: Dp,
    borderSize: Dp,
    spacing: Dp,
    minHeight: Dp,
    paddings: PaddingValues,
    textStyle: TextStyle,
    isLoading: Boolean,
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
    ) {
        Row(
            modifier =
            Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (startIcon != null) {
                Icon(
                    imageVector = startIcon,
                    contentDescription = null,
                    tint = foregroundColor,
                    modifier = Modifier.size(iconSize),
                )
                Spacer(modifier = Modifier.width(spacing))
            }
            Text(
                modifier =
                Modifier
                    .defaultMinSize(minHeight = loadingSize)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = text,
                color = foregroundColor,
                style = textStyle,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
            if (endIcon != null) {
                Spacer(modifier = Modifier.width(spacing))
                Icon(
                    imageVector = endIcon,
                    contentDescription = null,
                    tint = foregroundColor,
                    modifier = Modifier.size(iconSize),
                )
            }
        }
    }
}
