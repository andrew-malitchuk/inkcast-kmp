package presentation.core.ui.source.kit.atom.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Wifi: ImageVector
    get() {
        if (_Wifi != null) {
            return _Wifi!!
        }
        _Wifi = ImageVector.Builder(
            name = "Wifi",
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
                // path d="M5 12.55a11 11 0 0 1 14.08 0"
                moveTo(5f, 12.55f)
                arcTo(11f, 11f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.08f, 12.55f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // path d="M1.42 9a16 16 0 0 1 21.16 0"
                moveTo(1.42f, 9f)
                arcTo(16f, 16f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22.58f, 9f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // path d="M8.53 16.11a6 6 0 0 1 6.95 0"
                moveTo(8.53f, 16.11f)
                arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.48f, 16.11f)
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(12f, 20f)
                lineTo(12.01f, 20f)
            }
        }.build()

        return _Wifi!!
    }

@Suppress("ObjectPropertyName")
private var _Wifi: ImageVector? = null
