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
 *
 * A movable image view with multi-pointer touch.
 *
 * The last down pointer controls the moving of image.
 * When the dominant pointer is up,
 * the last dominant pointer take domination back, if it still on the screen
 */
class MultiTouchView1(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val bitmap = getChihuahua(resources, 300.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var downX = 0f
    private var downY = 0f

    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.actionMasked) {
            ACTION_DOWN, ACTION_POINTER_DOWN -> {
                trackingPointerId = event.findPointerIndex(event.actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                true
            }
            ACTION_MOVE -> {
                offsetX =
                    event.getX(event.findPointerIndex(trackingPointerId)) - downX + originalOffsetX
                offsetY =
                    event.getY(event.findPointerIndex(trackingPointerId)) - downY + originalOffsetY
                invalidate()
                true
            }
            ACTION_POINTER_UP -> {
                trackingPointerId = if (event.actionIndex == event.pointerCount - 1) {
                    event.getPointerId(event.actionIndex - 1)
                } else {
                    event.getPointerId(event.pointerCount - 1)
                }
                downX = event.getX(event.findPointerIndex(trackingPointerId))
                downY = event.getY(event.findPointerIndex(trackingPointerId))
                originalOffsetX = offsetX
                originalOffsetY = offsetY
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}