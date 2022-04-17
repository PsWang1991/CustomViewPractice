package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RandomLunchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        canvas.drawText("雞腿飯", (width / 2).toFloat(), (height / 2).toFloat(), paint)
    }
}