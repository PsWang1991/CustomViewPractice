package com.example.customviews.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp
import com.example.customviews.utils.getChihuahua

/**
 * Created by PS Wang on 2022/4/10
 */

private val pictureHeight = 200f.dp
private val offset = 20f.dp
private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val bounds = RectF(0f, 0f, 400f.dp, 400f.dp)

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bounds, null)
        canvas.drawCircle(offset + pictureHeight / 2f, offset + pictureHeight / 2f, pictureHeight / 2f, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(
            getChihuahua(resources, pictureHeight.toInt()),
            0f,
            0f,
            paint
        )
        paint.xfermode = null
        canvas.restoreToCount(count)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f.dp
        canvas.drawCircle(offset + pictureHeight / 2f, offset + pictureHeight / 2f, pictureHeight / 2f, paint)
    }
}