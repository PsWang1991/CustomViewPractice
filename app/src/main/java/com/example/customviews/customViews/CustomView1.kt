package com.example.customviews.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp2px
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by PS Wang on 2022/4/9
 */

private val radius = 100f.dp2px
private val origin = Point(200f.dp2px, 200f.dp2px)
private const val openAngle = 90f
private const val scaleLineWidth = 3f
private val scaleLineLength = 15f.dp2px
private const val divider = 20f
private val pointerLength = radius - scaleLineLength - 15f
private const val pointerAt = 14f
private val pointerAngle = Math.toRadians((90f + openAngle / 2f + pointerAt * (360f - openAngle) / divider).toDouble())

class CustomView1(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val dash = Path()
    private val arcPath = Path()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        dash.apply {
            reset()
            addRect(0f, 0f, scaleLineWidth, scaleLineLength, Path.Direction.CW)
        }
        paint.apply {
            strokeWidth = 2f.dp2px
            style = Paint.Style.STROKE
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        arcPath.reset()
        arcPath.addArc(origin.x - radius,
            origin.y - radius,
            origin.x + radius,
            origin.y + radius,
            90f + openAngle / 2f,
            360f - openAngle)
        val pathMeasure = PathMeasure(arcPath, false)
        paint.pathEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - scaleLineWidth) / divider,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(arcPath, paint)
        paint.pathEffect = null
        canvas.drawPath(arcPath, paint)
        paint.color = Color.RED
        canvas.drawLine(
            origin.x,
            origin.y,
            origin.x + pointerLength * cos(pointerAngle).toFloat(),
            origin.y + pointerLength * sin(pointerAngle).toFloat(),
            paint
        )
    }
}