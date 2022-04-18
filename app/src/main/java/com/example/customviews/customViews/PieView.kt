package com.example.customviews.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by PS Wang on 2022/4/10
 */

private val radius = 100f.dp
private val origin = PointF(200f.dp, 200f.dp)
private val arcs = listOf(45f, 30f, 80f, 120f, 25f, 60f)
private val colors = listOf(Color.BLUE,
    Color.GREEN,
    Color.RED,
    Color.CYAN,
    Color.YELLOW,
    Color.parseColor("#B09422"))
private val offSet = 20f.dp
private const val witchToOffset = 4

class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val pie = RectF(
        origin.x - radius,
        origin.y - radius,
        origin.x + radius,
        origin.y + radius,
    )

    init {
        paint.apply {
            color = Color.BLUE
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }

    override fun onDraw(canvas: Canvas) {

        var startAngle = 0f
        arcs.forEachIndexed { index, sweepAngle ->
            paint.color = colors[index]

            if (index == witchToOffset) {
                canvas.save()
                canvas.translate(
                    offSet * cos(Math.toRadians((startAngle + 0.5f * sweepAngle).toDouble())).toFloat(),
                    offSet * sin(Math.toRadians((startAngle + 0.5f * sweepAngle).toDouble())).toFloat(),
                )
            }

            canvas.drawArc(pie, startAngle, sweepAngle, true, paint)
            startAngle += sweepAngle

            if (index == witchToOffset) {
                canvas.restore()
            }
        }
    }
}