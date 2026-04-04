package presentation.core.ui.source.kit.atom.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme

public val Wizard: ImageVector
    @Composable
    get() {
        if (_Wizard != null) {
            return _Wizard!!
        }
        _Wizard = ImageVector.Builder(
            name = "Wizard",
            defaultWidth = 256.dp,
            defaultHeight = 256.dp,
            viewportWidth = 256f,
            viewportHeight = 256f
        ).apply {
            path(
                fill = SolidColor(Theme.color.canvas),
                fillAlpha = 0.5f,
                strokeAlpha = 0.5f
            ) {
                moveTo(128f, 12.8f)
                lineTo(128f, 12.8f)
                arcTo(115.2f, 115.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 243.2f, 128f)
                lineTo(243.2f, 128f)
                arcTo(115.2f, 115.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 128f, 243.2f)
                lineTo(128f, 243.2f)
                arcTo(115.2f, 115.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12.8f, 128f)
                lineTo(12.8f, 128f)
                arcTo(115.2f, 115.2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 128f, 12.8f)
                close()
            }
            path(fill = SolidColor(Theme.color.canvas)) {
                moveTo(44.5f, 65f)
                lineTo(76.5f, 65f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 91.5f, 80f)
                lineTo(91.5f, 176f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 76.5f, 191f)
                lineTo(44.5f, 191f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 29.5f, 176f)
                lineTo(29.5f, 80f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 44.5f, 65f)
                close()
            }
            path(
                stroke = SolidColor(Theme.color.inkMain),
                strokeLineWidth = 2f
            ) {
                moveTo(44.5f, 65f)
                lineTo(76.5f, 65f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 91.5f, 80f)
                lineTo(91.5f, 176f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 76.5f, 191f)
                lineTo(44.5f, 191f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 29.5f, 176f)
                lineTo(29.5f, 80f)
                arcTo(15f, 15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 44.5f, 65f)
                close()
            }
            path(fill = SolidColor(Theme.color.inkMain)) {
                moveTo(54.5f, 74f)
                lineTo(66.5f, 74f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 68.5f, 76f)
                lineTo(68.5f, 76f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 66.5f, 78f)
                lineTo(54.5f, 78f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 52.5f, 76f)
                lineTo(52.5f, 76f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 54.5f, 74f)
                close()
            }
            path(
                stroke = SolidColor(Theme.color.inkMain),
                strokeAlpha = 0.1f,
                strokeLineWidth = 1f
            ) {
                moveTo(46.5f, 86.5f)
                lineTo(74.5f, 86.5f)
                arcTo(7.5f, 7.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 82f, 94f)
                lineTo(82f, 174f)
                arcTo(7.5f, 7.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 74.5f, 181.5f)
                lineTo(46.5f, 181.5f)
                arcTo(7.5f, 7.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 39f, 174f)
                lineTo(39f, 94f)
                arcTo(7.5f, 7.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 46.5f, 86.5f)
                close()
            }
            path(
                fill = SolidColor(Theme.color.inkMain),
                fillAlpha = 0.05f
            ) {
                moveTo(45.5f, 91f)
                lineTo(75.5f, 91f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 77.5f, 93f)
                lineTo(77.5f, 93f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 75.5f, 95f)
                lineTo(45.5f, 95f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 43.5f, 93f)
                lineTo(43.5f, 93f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 45.5f, 91f)
                close()
            }
            path(
                fill = SolidColor(Theme.color.inkMain),
                fillAlpha = 0.05f
            ) {
                moveTo(45.5f, 99f)
                lineTo(67f, 99f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 69f, 101f)
                lineTo(69f, 101f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 67f, 103f)
                lineTo(45.5f, 103f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 43.5f, 101f)
                lineTo(43.5f, 101f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 45.5f, 99f)
                close()
            }
            path(fill = SolidColor(Theme.color.inkMain)) {
                moveTo(108.5f, 138f)
                verticalLineTo(135.5f)
                horizontalLineTo(111.94f)
                lineTo(111.44f, 135.06f)
                curveTo(110.35f, 134.1f, 109.59f, 133.01f, 109.16f, 131.78f)
                curveTo(108.72f, 130.55f, 108.5f, 129.31f, 108.5f, 128.06f)
                curveTo(108.5f, 125.75f, 109.19f, 123.69f, 110.58f, 121.89f)
                curveTo(111.96f, 120.09f, 113.77f, 118.9f, 116f, 118.31f)
                verticalLineTo(120.94f)
                curveTo(114.5f, 121.48f, 113.29f, 122.4f, 112.38f, 123.7f)
                curveTo(111.46f, 125f, 111f, 126.46f, 111f, 128.06f)
                curveTo(111f, 129f, 111.18f, 129.91f, 111.53f, 130.8f)
                curveTo(111.89f, 131.68f, 112.44f, 132.5f, 113.19f, 133.25f)
                lineTo(113.5f, 133.56f)
                verticalLineTo(130.5f)
                horizontalLineTo(116f)
                verticalLineTo(138f)
                horizontalLineTo(108.5f)
                close()
                moveTo(121f, 137.69f)
                verticalLineTo(135.06f)
                curveTo(122.5f, 134.52f, 123.71f, 133.6f, 124.63f, 132.3f)
                curveTo(125.54f, 130.99f, 126f, 129.54f, 126f, 127.94f)
                curveTo(126f, 127f, 125.82f, 126.09f, 125.47f, 125.2f)
                curveTo(125.11f, 124.32f, 124.56f, 123.5f, 123.81f, 122.75f)
                lineTo(123.5f, 122.44f)
                verticalLineTo(125.5f)
                horizontalLineTo(121f)
                verticalLineTo(118f)
                horizontalLineTo(128.5f)
                verticalLineTo(120.5f)
                horizontalLineTo(125.06f)
                lineTo(125.56f, 120.94f)
                curveTo(126.58f, 121.96f, 127.33f, 123.07f, 127.8f, 124.27f)
                curveTo(128.27f, 125.46f, 128.5f, 126.69f, 128.5f, 127.94f)
                curveTo(128.5f, 130.25f, 127.81f, 132.31f, 126.42f, 134.11f)
                curveTo(125.04f, 135.91f, 123.23f, 137.1f, 121f, 137.69f)
                close()
            }
            path(
                fill = SolidColor(Theme.color.canvas),
                stroke = SolidColor(Theme.color.inkMain),
                strokeLineWidth = 2f
            ) {
                moveTo(150.5f, 65f)
                lineTo(221.5f, 65f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 226.5f, 70f)
                lineTo(226.5f, 186f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 221.5f, 191f)
                lineTo(150.5f, 191f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 145.5f, 186f)
                lineTo(145.5f, 70f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 150.5f, 65f)
                close()
            }
            path(fill = SolidColor(Theme.color.canvas)) {
                moveTo(156.5f, 75f)
                lineTo(215.5f, 75f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 217.5f, 77f)
                lineTo(217.5f, 178f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 215.5f, 180f)
                lineTo(156.5f, 180f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 154.5f, 178f)
                lineTo(154.5f, 77f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 156.5f, 75f)
                close()
            }
            path(
                stroke = SolidColor(Theme.color.inkMain),
                strokeAlpha = 0.1f,
                strokeLineWidth = 1f
            ) {
                moveTo(156.5f, 75.5f)
                lineTo(215.5f, 75.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 217f, 77f)
                lineTo(217f, 178f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 215.5f, 179.5f)
                lineTo(156.5f, 179.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 155f, 178f)
                lineTo(155f, 77f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 156.5f, 75.5f)
                close()
            }
            path(fill = SolidColor(Theme.color.inkMain)) {
                moveTo(159f, 187f)
                lineTo(181f, 187f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 182.5f, 185.5f)
                lineTo(182.5f, 185.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 181f, 184f)
                lineTo(159f, 184f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 157.5f, 185.5f)
                lineTo(157.5f, 185.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 159f, 187f)
                close()
            }
            path(fill = SolidColor(Theme.color.inkMain)) {
                moveTo(191f, 187f)
                lineTo(213f, 187f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 214.5f, 185.5f)
                lineTo(214.5f, 185.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 213f, 184f)
                lineTo(191f, 184f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 189.5f, 185.5f)
                lineTo(189.5f, 185.5f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 191f, 187f)
                close()
            }
            path(
                fill = SolidColor(Theme.color.inkMain),
                fillAlpha = 0.2f
            ) {
                moveTo(160.5f, 80f)
                lineTo(201.5f, 80f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 203.5f, 82f)
                lineTo(203.5f, 84f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 201.5f, 86f)
                lineTo(160.5f, 86f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 158.5f, 84f)
                lineTo(158.5f, 82f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 160.5f, 80f)
                close()
            }
            path(
                fill = SolidColor(Theme.color.inkMain),
                fillAlpha = 0.2f
            ) {
                moveTo(160.5f, 92f)
                lineTo(201.5f, 92f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 203.5f, 94f)
                lineTo(203.5f, 96f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 201.5f, 98f)
                lineTo(160.5f, 98f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 158.5f, 96f)
                lineTo(158.5f, 94f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 160.5f, 92f)
                close()
            }
            path(
                fill = SolidColor(Theme.color.inkMain),
                fillAlpha = 0.2f
            ) {
                moveTo(160.5f, 104f)
                lineTo(195.16f, 104f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 197.16f, 106f)
                lineTo(197.16f, 108f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 195.16f, 110f)
                lineTo(160.5f, 110f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 158.5f, 108f)
                lineTo(158.5f, 106f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 160.5f, 104f)
                close()
            }
        }.build()

        return _Wizard!!
    }

@Suppress("ObjectPropertyName")
private var _Wizard: ImageVector? = null
