package com.example.customviews.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp2px
import com.example.customviews.utils.getChihuahua

/**
 * Created by PS Wang on 2022/4/13
 */

private const val text =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer ultrices volutpat augue eget ultricies. Mauris aliquam erat quis sapien placerat molestie. Vestibulum non libero varius dui mollis tempor. Vestibulum consectetur hendrerit malesuada. In hac habitasse platea dictumst. Fusce sed ante quis dui malesuada volutpat quis pellentesque est. Sed posuere magna eget libero venenatis maximus. Nunc dolor ipsum, mattis aliquet urna vitae, iaculis lobortis arcu. Nunc efficitur risus porttitor nulla laoreet aliquet. Nunc nulla diam, venenatis sit amet tempus a, vestibulum eu elit. Mauris ac arcu at enim aliquet accumsan eget vitae arcu. Pellentesque pellentesque mi eros, quis aliquam quam sodales sed."
private val topPadding = 105.dp2px
private val photoHeight = 120.dp2px

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val fontMetrics = Paint.FontMetrics()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20.dp2px
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val chihuahua = getChihuahua(resources, photoHeight.toInt())
        canvas.drawBitmap(chihuahua, (width - chihuahua.width).toFloat(), topPadding, paint)

        paint.getFontMetrics(fontMetrics)

        var count = 0
        var start = 0
        var textPositionY = -fontMetrics.top
        var maxTextWidth: Float
        while (start < text.length) {
            maxTextWidth =
                if (textPositionY + fontMetrics.bottom < topPadding
                    || textPositionY + fontMetrics.top > topPadding + chihuahua.height
                ) {
                    width.toFloat()
                } else {
                    width.toFloat() - chihuahua.width
                }
            count = paint.breakText(
                text,
                start, text.length,
                true,
                maxTextWidth,
                null
            )
            canvas.drawText(text, start, start + count, 0f, textPositionY, paint)
            start += count
            textPositionY += paint.fontSpacing
        }
    }
}