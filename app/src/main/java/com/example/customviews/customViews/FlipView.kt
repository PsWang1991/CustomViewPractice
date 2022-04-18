package com.example.customviews.customViews

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.example.customviews.utils.dp
import com.example.customviews.utils.getChihuahua

/**
 * Created by PS Wang on 2022/4/18
 */

private val offsetX = 100.dp
private val offsetY = 180.dp

class FlipView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera().apply {
        setLocation(0f, 0f, -5 * resources.displayMetrics.density)
    }

    var upperFlipAngle = 0f
        set(value) {
            field = value
            invalidate()
        }
    var downerFlipAngle = 0f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotationAngle = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val chihuahua = getChihuahua(resources, 400)

    override fun onDraw(canvas: Canvas) {

        // Upper
        canvas.withSave {
            translate(offsetX + chihuahua.width / 2, offsetY + chihuahua.height / 2)
            rotate(-flipRotationAngle)
            camera.apply {
                save()
                rotateX(upperFlipAngle)
                applyToCanvas(canvas)
                restore()
            }
            clipRect(-chihuahua.width, -chihuahua.height, chihuahua.width, 0)
            rotate(flipRotationAngle)
            translate(-(offsetX + chihuahua.width / 2), -(offsetY + chihuahua.height / 2))
            drawBitmap(chihuahua, offsetX, offsetY, paint)
        }

        // Downer
        canvas.withSave {
            translate(offsetX + chihuahua.width / 2, offsetY + chihuahua.height / 2)
            rotate(-flipRotationAngle)
            camera.apply {
                save()
                rotateX(downerFlipAngle)
                applyToCanvas(canvas)
                restore()
            }
            clipRect(-chihuahua.width, 0, chihuahua.width, chihuahua.height)
            rotate(flipRotationAngle)
            translate(-(offsetX + chihuahua.width / 2), -(offsetY + chihuahua.height / 2))
            drawBitmap(chihuahua, offsetX, offsetY, paint)
        }
    }
}