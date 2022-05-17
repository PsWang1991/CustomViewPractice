package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.util.forEach
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/5/17
 */
class MultiTouchCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 3.5f.dp
        color = Color.parseColor("#000000")
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        style = Paint.Style.STROKE

    }

    private val pathArray = SparseArray<Path>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathArray.forEach { _, path ->
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            ACTION_DOWN, ACTION_POINTER_DOWN -> {
                val path = Path()
                val actionIndex = event.actionIndex
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                pathArray.put(event.getPointerId(actionIndex), path)
                invalidate()
            }
            ACTION_MOVE -> {
                pathArray.forEach { id, path ->
                    val index = event.findPointerIndex(id)
                    path.lineTo(event.getX(index), event.getY(index))
                }
                invalidate()
            }
            ACTION_UP, ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(event.actionIndex)
                pathArray.delete(pointerId)
                invalidate()
            }
        }

        return true
    }
}