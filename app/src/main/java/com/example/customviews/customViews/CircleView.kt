package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp2px

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var radius = 50.dp2px
    set(value) {
        field = value
        invalidate()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.CYAN
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radius, paint)
    }
}