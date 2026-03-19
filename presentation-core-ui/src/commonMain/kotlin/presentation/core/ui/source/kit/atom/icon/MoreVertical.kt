package presentation.core.ui.source.kit.atom.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val MoreVertical: ImageVector
    get() {
        if (_MoreVertical != null) {
            return _MoreVertical!!
        }
        _MoreVertical = ImageVector.Builder(
            name = "MoreVertical",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // circle cx=12 cy=12 r=1
                moveTo(13f, 12f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 11f, 12f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 13f, 12f)
                close()
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // circle cx=12 cy=5 r=1
                moveTo(13f, 5f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 11f, 5f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 13f, 5f)
                close()
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // circle cx=12 cy=19 r=1
                moveTo(13f, 19f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 11f, 19f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 13f, 19f)
                close()
            }
        }.build()

        return _MoreVertical!!
    }

@Suppress("ObjectPropertyName")
private var _MoreVertical: ImageVector? = null
