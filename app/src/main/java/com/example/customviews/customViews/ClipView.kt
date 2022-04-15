package com.example.customviews.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp2px
import com.example.customviews.utils.getChihuahua
import kotlin.math.min

/**
 * Created by PS Wang on 2022/4/15
 */


private val photoOrigin = Point(100.dp2px, 150.dp2px)
private val circleXShift = 35.dp2px

class ClipView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path()

    private val chihuahua = getChihuahua(resources, 150.dp2px.toInt())

    private val radius = min(chihuahua.width, chihuahua.height).toFloat() / 2f
    private val origin = Point(photoOrigin.x + radius + circleXShift, photoOrigin.y + radius)

    override fun onDraw(canvas: Canvas) {

        paint.alpha = 125
        canvas.drawBitmap(chihuahua, photoOrigin.x, photoOrigin.y, paint)

        clipPath.addCircle(origin.x, origin.y, radius, Path.Direction.CW)
        canvas.clipPath(clipPath)
        paint.alpha = 255
        canvas.drawBitmap(chihuahua, photoOrigin.x, photoOrigin.y, paint)
    }
}