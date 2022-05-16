package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import com.example.customviews.utils.dp
import com.example.customviews.utils.getChihuahua

/**
 * Created by PS Wang on 2022/5/16
 */
class MultiTouchView2(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val bitmap = getChihuahua(resources, 300.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var downX = 0f
    private var downY = 0f

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val isPointerUp = event.actionMasked == ACTION_POINTER_UP

        var sumX = 0f
        var sumY = 0f

        for (i in 0 until event.pointerCount) {
            if (isPointerUp && event.actionIndex == i) continue
            sumX += event.getX(i)
            sumY += event.getY(i)
        }

        val pointerCount = if (isPointerUp) event.pointerCount - 1 else event.pointerCount

        val focusX = sumX / pointerCount
        val focusY = sumY / pointerCount

        return when (event.actionMasked) {
            ACTION_DOWN, ACTION_POINTER_DOWN, ACTION_POINTER_UP -> {
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                true
            }
            ACTION_MOVE -> {
                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
                invalidate()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}