package com.example.customviews.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/4/10
 */

private val circleBackgroundColor = Color.parseColor("#C4C4C4")
private val circleColor = Color.parseColor("#6489FF")
private val textColor = Color.parseColor("#64FF89")

private val radius = 140.dp
private val circleLineWidth = 25.dp
private val yOffset = 0.dp

private const val text2Draw = "abab"

private const val progressRate = 0.64f

class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var origin: PointF
    private lateinit var circularProgress: RectF

    private val bounds = Rect()
    private val fontMetrics = Paint.FontMetrics()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp
        textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        origin = PointF((w / 2).toFloat(), (h / 2).toFloat() - yOffset)
        circularProgress = RectF(
            origin.x - radius,
            origin.y - radius,
            origin.x + radius,
            origin.y + radius,
        )
    }

    override fun onDraw(canvas: Canvas) {

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleLineWidth

        paint.color = circleBackgroundColor
        canvas.drawCircle(origin.x, origin.y, radius, paint)

        paint.strokeCap = Paint.Cap.ROUND
        paint.color = circleColor
        canvas.drawArc(circularProgress, -90f, 360f * progressRate, false, paint)

        paint.color = textColor
        paint.style = Paint.Style.FILL
        paint.getTextBounds(text2Draw, 0, text2Draw.length, bounds)
//        val textCenter = (bounds.bottom + bounds.top) / 2
        paint.getFontMetrics(fontMetrics)
        val textCenter = (fontMetrics.ascent + fontMetrics.descent) / 2
        canvas.drawText(text2Draw, origin.x, origin.y - textCenter, paint)
//        canvas.drawText(text2Draw, origin.x, origin.y, paint)

        paint.textAlign = Paint.Align.LEFT
        canvas.drawText(text2Draw, 0f, 0f - fontMetrics.top, paint)
        canvas.drawText(text2Draw, 0f - bounds.left, height - fontMetrics.descent, paint)

//        canvas.drawLine(0f, origin.y, width.toFloat(), origin.y, paint)
//        canvas.drawLine(0f, origin.y + fontMetrics.ascent, width.toFloat(), origin.y + fontMetrics.ascent, paint)
//        canvas.drawLine(0f, origin.y + fontMetrics.descent, width.toFloat(), origin.y + fontMetrics.descent, paint)

    }
}