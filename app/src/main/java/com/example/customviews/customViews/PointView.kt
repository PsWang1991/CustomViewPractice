package com.example.customviews.customViews

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/4/18
 */
class PointView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var position = PointF(0.dp, 0.dp)
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 20.dp
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPoint(position.x, position.y, paint)
    }
}

class PointTypeEvaluator : TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val evaluateX = startValue.x + (endValue.x - startValue.x) * fraction
        val evaluateY = startValue.y + (endValue.y - startValue.y) * fraction
        return PointF(evaluateX, evaluateY)
    }

}