package presentation.core.ui.source.kit.atom.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme

public val AppIcon: ImageVector
    @Composable
    get() {
        if (_AppIcon != null) {
            return _AppIcon!!
        }
        _AppIcon = ImageVector.Builder(
            name = "AppIcon",
            defaultWidth = 2048.dp,
            defaultHeight = 2048.dp,
            viewportWidth = 2048f,
            viewportHeight = 2048f
        ).apply {
            path(fill = SolidColor(Theme.color.inkMain)) {
                moveTo(570.2f, 910.9f)
                lineToRelative(0.3f, 250.2f)
                lineToRelative(24.2f, 0.3f)
                curveToRelative(13.2f, 0.2f, 24.5f, 0.6f, 25f, 0.9f)
                curveToRelative(0.6f, 0.4f, 8.4f, 0.7f, 17.4f, 0.8f)
                curveToRelative(21.9f, 0.3f, 52.3f, 2.4f, 70.9f, 4.9f)
                curveToRelative(3.6f, 0.5f, 8.3f, 1.1f, 10.5f, 1.4f)
                curveToRelative(7.8f, 0.9f, 19.1f, 2.6f, 24f, 3.6f)
                curveToRelative(25.3f, 5f, 32.4f, 6.5f, 42f, 9f)
                curveToRelative(16.6f, 4.4f, 17.9f, 4.8f, 32.5f, 10.2f)
                curveToRelative(25.8f, 9.6f, 48.1f, 21.1f, 66.2f, 33.9f)
                curveToRelative(11.8f, 8.5f, 30.2f, 25.7f, 39.7f, 37.4f)
                curveToRelative(6.8f, 8.2f, 15.6f, 21.3f, 14.9f, 22f)
                curveToRelative(-0.3f, 0.3f, -83f, 0.6f, -183.9f, 0.8f)
                lineToRelative(-183.4f, 0.2f)
                lineToRelative(-0.3f, 50.2f)
                lineToRelative(-0.2f, 50.3f)
                lineToRelative(454f, -0f)
                lineToRelative(454f, -0f)
                lineToRelative(0.2f, -49.5f)
                curveToRelative(0.2f, -27.2f, 0.2f, -50f, 0f, -50.5f)
                curveToRelative(-0.3f, -0.7f, -62.9f, -1f, -184.3f, -1f)
                lineToRelative(-183.9f, -0f)
                lineToRelative(2f, -4f)
                curveToRelative(3.4f, -6.7f, 21.8f, -28.7f, 31.4f, -37.6f)
                curveToRelative(23f, -21.2f, 48.9f, -36.8f, 82.1f, -49.4f)
                curveToRelative(6.1f, -2.3f, 11.9f, -4.6f, 13f, -5.1f)
                curveToRelative(2.3f, -1.1f, 15.9f, -5.2f, 26f, -8f)
                curveToRelative(13.2f, -3.7f, 30.9f, -7.3f, 48f, -9.9f)
                curveToRelative(3.3f, -0.5f, 8.7f, -1.3f, 12f, -1.9f)
                curveToRelative(14.1f, -2.3f, 43.3f, -5.2f, 61.4f, -6.1f)
                curveToRelative(10.8f, -0.5f, 25.5f, -1.2f, 32.6f, -1.6f)
                curveToRelative(7.2f, -0.3f, 23.6f, -0.8f, 36.5f, -0.9f)
                curveToRelative(16.4f, -0.2f, 23.5f, -0.6f, 23.4f, -1.4f)
                curveToRelative(-0.1f, -0.6f, -0.2f, -113.2f, -0.2f, -250.3f)
                lineToRelative(-0.2f, -249.1f)
                lineToRelative(-49.7f, 0.6f)
                curveToRelative(-27.4f, 0.4f, -54.7f, 1f, -60.8f, 1.2f)
                curveToRelative(-30.3f, 1.5f, -74.9f, 6f, -89f, 9f)
                curveToRelative(-1.6f, 0.3f, -7.9f, 1.5f, -14f, 2.6f)
                curveToRelative(-60.4f, 11.2f, -106.7f, 31.7f, -152.5f, 67.5f)
                curveToRelative(-10.9f, 8.5f, -34.3f, 31.8f, -44.7f, 44.6f)
                curveToRelative(-13.3f, 16.2f, -29.3f, 40f, -39.4f, 58.5f)
                curveToRelative(-1.9f, 3.5f, -3.6f, 6.3f, -3.9f, 6.3f)
                curveToRelative(-0.3f, -0f, -3.5f, -5.3f, -7f, -11.7f)
                curveToRelative(-8.1f, -14.8f, -24.8f, -39.9f, -33.1f, -49.7f)
                curveToRelative(-11.7f, -14f, -21f, -23.9f, -32.7f, -34.8f)
                curveToRelative(-24.4f, -22.9f, -51.5f, -40.4f, -87.7f, -56.8f)
                curveToRelative(-3.8f, -1.8f, -8.8f, -3.9f, -11f, -4.7f)
                curveToRelative(-2.2f, -0.8f, -5.4f, -2.1f, -7.2f, -2.9f)
                curveToRelative(-1.7f, -0.8f, -3.6f, -1.4f, -4.2f, -1.4f)
                curveToRelative(-0.6f, -0f, -2.5f, -0.6f, -4.3f, -1.4f)
                curveToRelative(-7.2f, -3.2f, -28.8f, -8.7f, -50.8f, -13f)
                curveToRelative(-25.8f, -5f, -38f, -6.9f, -53f, -8.2f)
                curveToRelative(-2.5f, -0.2f, -8.3f, -0.8f, -13f, -1.3f)
                curveToRelative(-4.7f, -0.6f, -15.7f, -1.3f, -24.5f, -1.7f)
                curveToRelative(-8.8f, -0.3f, -18.2f, -0.7f, -21f, -0.9f)
                curveToRelative(-2.7f, -0.2f, -27.4f, -0.7f, -54.8f, -1.1f)
                lineToRelative(-49.7f, -0.7f)
                lineToRelative(0.2f, 250.2f)
                close()
                moveTo(705.5f, 763.6f)
                curveToRelative(12.4f, 0.7f, 37.8f, 3.8f, 44.8f, 5.3f)
                curveToRelative(2.3f, 0.5f, 8f, 1.7f, 12.7f, 2.6f)
                curveToRelative(14.3f, 2.8f, 40.6f, 10.2f, 44.9f, 12.6f)
                curveToRelative(0.9f, 0.4f, 4.5f, 2f, 8.1f, 3.5f)
                curveToRelative(8.4f, 3.4f, 27.5f, 12.9f, 31f, 15.4f)
                curveToRelative(1.6f, 1.1f, 3.1f, 2f, 3.4f, 2f)
                curveToRelative(1.3f, -0f, 19.2f, 12.4f, 26.6f, 18.4f)
                curveToRelative(31.8f, 25.8f, 58.9f, 63.2f, 71.9f, 99.1f)
                curveToRelative(1.2f, 3.3f, 2.6f, 6.9f, 3.1f, 8f)
                curveToRelative(0.6f, 1.1f, 1.7f, 4.2f, 2.4f, 7f)
                curveToRelative(2.2f, 7.8f, 5.2f, 17.8f, 5.8f, 19.5f)
                curveToRelative(2.5f, 6.8f, 8.2f, 39.8f, 10.9f, 63.5f)
                curveToRelative(1.2f, 9.9f, 2.6f, 158.5f, 1.5f, 158.5f)
                curveToRelative(-0.3f, -0f, -6f, -5.4f, -12.7f, -12f)
                curveToRelative(-22.4f, -22.2f, -41.7f, -37.1f, -73.4f, -56.5f)
                curveToRelative(-6.9f, -4.2f, -32.5f, -16.4f, -41.5f, -19.7f)
                curveToRelative(-32.6f, -11.9f, -38.4f, -13.5f, -74f, -20.7f)
                curveToRelative(-19.6f, -4f, -37.5f, -6.4f, -57f, -7.7f)
                curveToRelative(-5.8f, -0.4f, -11.6f, -0.8f, -13f, -1f)
                curveToRelative(-1.4f, -0.1f, -8.9f, -0.3f, -16.7f, -0.3f)
                lineToRelative(-14.3f, -0.1f)
                lineToRelative(0f, -149.6f)
                lineToRelative(0f, -149.6f)
                lineToRelative(14.3f, 0.7f)
                curveToRelative(7.8f, 0.3f, 17.4f, 0.8f, 21.2f, 1.1f)
                close()
                moveTo(1378f, 911.3f)
                lineToRelative(0f, 149.4f)
                lineToRelative(-20.2f, 0.7f)
                curveToRelative(-11.2f, 0.3f, -24.1f, 0.9f, -28.8f, 1.2f)
                curveToRelative(-12.8f, 0.9f, -49.9f, 6.2f, -58.5f, 8.4f)
                curveToRelative(-2.2f, 0.5f, -10.7f, 2.6f, -19f, 4.6f)
                curveToRelative(-29.8f, 7.2f, -59.2f, 18.4f, -85f, 32.2f)
                curveToRelative(-8.9f, 4.8f, -26.6f, 16.1f, -33.5f, 21.4f)
                curveToRelative(-1.4f, 1.1f, -5.9f, 4.4f, -10f, 7.4f)
                curveToRelative(-8.8f, 6.5f, -30.7f, 26f, -39.9f, 35.6f)
                curveToRelative(-3.5f, 3.8f, -6.8f, 6.8f, -7.3f, 6.8f)
                curveToRelative(-1.3f, -0f, -0.4f, -146.3f, 1f, -156f)
                curveToRelative(0.5f, -4.1f, 1.6f, -12.5f, 2.2f, -18.5f)
                curveToRelative(4.4f, -40.4f, 19.2f, -86.9f, 37.3f, -117f)
                curveToRelative(13f, -21.7f, 21.7f, -32.9f, 38.1f, -48.9f)
                curveToRelative(33.3f, -32.5f, 74.4f, -54.1f, 127.1f, -66.8f)
                curveToRelative(2.2f, -0.5f, 5.4f, -1.1f, 7f, -1.4f)
                curveToRelative(1.7f, -0.3f, 6.6f, -1.1f, 11f, -1.9f)
                curveToRelative(16f, -2.8f, 30.1f, -4.3f, 48.5f, -5.1f)
                curveToRelative(7.4f, -0.4f, 13.6f, -0.7f, 13.7f, -0.8f)
                curveToRelative(0.1f, -0.1f, 3.8f, -0.2f, 8.2f, -0.4f)
                lineToRelative(8.1f, -0.2f)
                lineToRelative(0f, 149.3f)
                close()
            }
        }.build()

        return _AppIcon!!
    }

@Suppress("ObjectPropertyName")
private var _AppIcon: ImageVector? = null
