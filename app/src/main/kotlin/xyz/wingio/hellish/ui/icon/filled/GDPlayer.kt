@file:Suppress("ObjectPropertyName")

package xyz.wingio.hellish.ui.icon.filled

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.GDPlayer: ImageVector
    get() {
        if (_gDPlayer != null) {
            return _gDPlayer!!
        }
        _gDPlayer = ImageVector.Builder(
            name = "GDPlayer",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 48.0F,
            viewportHeight = 48.0F,
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            fillAlpha = 1.0F,
            strokeAlpha = 1.0F,
            strokeLineWidth = 0.0F,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 4.0F,
            pathFillType = PathFillType.EvenOdd,
        ) {
            moveTo(15.867F, 12.621F)
            lineTo(20.01F, 12.621F)
            curveTo(20.908F, 12.621F, 21.637F, 13.35F, 21.637F, 14.248F)
            lineTo(21.637F, 18.391F)
            curveTo(21.637F, 19.289F, 20.908F, 20.018F, 20.01F, 20.018F)
            lineTo(15.867F, 20.018F)
            curveTo(14.969F, 20.018F, 14.24F, 19.289F, 14.24F, 18.391F)
            lineTo(14.24F, 14.248F)
            curveTo(14.24F, 13.35F, 14.969F, 12.621F, 15.867F, 12.621F)
            lineTo(15.867F, 12.621F)
            lineTo(15.867F, 12.621F)

            moveTo(27.99F, 12.621F)
            lineTo(32.133F, 12.621F)
            curveTo(33.031F, 12.621F, 33.76F, 13.35F, 33.76F, 14.248F)
            lineTo(33.76F, 18.391F)
            curveTo(33.76F, 19.289F, 33.031F, 20.018F, 32.133F, 20.018F)
            lineTo(27.99F, 20.018F)
            curveTo(27.092F, 20.018F, 26.363F, 19.289F, 26.363F, 18.391F)
            lineTo(26.363F, 14.248F)
            curveTo(26.363F, 13.35F, 27.092F, 12.621F, 27.99F, 12.621F)
            lineTo(27.99F, 12.621F)

            moveTo(11.312F, 5.875F)
            lineTo(36.688F, 5.875F)
            curveTo(39.689F, 5.875F, 42.125F, 8.311F, 42.125F, 11.312F)
            lineTo(42.125F, 36.688F)
            curveTo(42.125F, 39.689F, 39.689F, 42.125F, 36.688F, 42.125F)
            lineTo(11.312F, 42.125F)
            curveTo(8.311F, 42.125F, 5.875F, 39.689F, 5.875F, 36.688F)
            lineTo(5.875F, 11.312F)
            curveTo(5.875F, 8.311F, 8.311F, 5.875F, 11.312F, 5.875F)
            lineTo(11.312F, 5.875F)
            lineTo(11.312F, 5.875F)

            moveTo(12.577F, 24.714F)
            lineTo(35.423F, 24.714F)
            curveTo(36.516F, 24.714F, 37.403F, 25.601F, 37.403F, 26.694F)
            lineTo(37.403F, 28.444F)
            curveTo(37.403F, 29.537F, 36.516F, 30.424F, 35.423F, 30.424F)
            lineTo(12.577F, 30.424F)
            curveTo(11.484F, 30.424F, 10.597F, 29.537F, 10.597F, 28.444F)
            lineTo(10.597F, 26.694F)
            curveTo(10.597F, 25.601F, 11.484F, 24.714F, 12.577F, 24.714F)
            close()
        }.build()
        return _gDPlayer!!
    }

private var _gDPlayer: ImageVector? = null