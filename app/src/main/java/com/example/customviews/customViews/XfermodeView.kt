package com.example.customviews.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.customviews.R
import com.example.customviews.utils.dp2px

/**
 * Created by PS Wang on 2022/4/10
 */

private val boundSize = 300f.dp2px
private val origin = Point(200f.dp2px, 100f.dp2px)
private val radius = 70f.dp2px
private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

class XfermodeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val bounds = RectF(0f, 0f, boundSize, boundSize)

    private val circleBitmap = Bitmap.createBitmap(boundSize.toInt(), boundSize.toInt(), Bitmap.Config.ARGB_8888)
    private val rectBitmap = Bitmap.createBitmap(boundSize.toInt(), boundSize.toInt(), Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.parseColor("#FF0000")
        canvas.drawCircle(origin.x, origin.y, radius, paint)
        canvas.setBitmap(rectBitmap)
        paint.color = Color.parseColor("#0000FF")
        canvas.drawRect(
            origin.x - 2f * radius,
            origin.y,
            origin.x,
            origin.y + 2f * radius,
            paint
        )
    }

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(bounds, null)

        canvas.drawBitmap(circleBitmap, 0f, 0f, paint)

        paint.xfermode = xfermode

        canvas.drawBitmap(rectBitmap, 0f, 0f, paint)

        paint.xfermode = null
        canvas.restoreToCount(count)
    }

}