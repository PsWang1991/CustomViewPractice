package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/4/19
 */
class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val drawable = MeshDrawable().apply {
        setBounds(0, 0, 180.dp.toInt(), 180.dp.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.draw(canvas)
    }
}