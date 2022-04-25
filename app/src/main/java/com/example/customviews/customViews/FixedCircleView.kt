package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/4/25
 */

private val PADDING = 30.dp
private val RADIUS = 50.dp

class FixedCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#777777")
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }
}