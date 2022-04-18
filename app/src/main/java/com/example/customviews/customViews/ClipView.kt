package com.example.customviews.customViews

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.example.customviews.utils.dp
import com.example.customviews.utils.getChihuahua

/**
 * Created by PS Wang on 2022/4/15
 */

private val photoOrigin = PointF(100.dp, 150.dp)
private const val clipExtension = 200

class ClipView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var cameraRotateXUpper = -0f
        set(value) {
            field = value
            invalidate()
        }
    var cameraRotateXDowner = 0f
        set(value) {
            field = value
            invalidate()
        }
    var canvasRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val chihuahua = getChihuahua(resources, 150.dp.toInt())

    private val camera = Camera().apply {
        setLocation(0f, 0f, -5 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {

        // down side
        canvas.withSave {
            translate(
                (photoOrigin.x + chihuahua.width / 2),
                (photoOrigin.y + chihuahua.height / 2)
            )
            rotate(canvasRotate)
            camera.save()
            camera.rotateX(cameraRotateXDowner)
            camera.applyToCanvas(canvas)
            camera.restore()
            clipRect(
                -chihuahua.width / 2 - clipExtension,
                0,
                chihuahua.width / 2 + clipExtension,
                chihuahua.height / 2 + clipExtension
            )
            rotate(-canvasRotate)
            translate(
                -(photoOrigin.x + chihuahua.width / 2),
                -(photoOrigin.y + chihuahua.height / 2)
            )
            drawBitmap(chihuahua, photoOrigin.x, photoOrigin.y, paint)
        }

        // upper side
        canvas.withSave {
            translate(
                (photoOrigin.x + chihuahua.width / 2),
                (photoOrigin.y + chihuahua.height / 2)
            )
            rotate(canvasRotate)
            camera.save()
            camera.rotateX(cameraRotateXUpper)
            camera.applyToCanvas(canvas)
            camera.restore()
            clipRect(
                -chihuahua.width / 2 - clipExtension,
                -chihuahua.height / 2 - clipExtension,
                chihuahua.width / 2 + clipExtension,
                0
            )
            rotate(-canvasRotate)
            translate(
                -(photoOrigin.x + chihuahua.width / 2),
                -(photoOrigin.y + chihuahua.height / 2)
            )
            drawBitmap(chihuahua, photoOrigin.x, photoOrigin.y, paint)
        }
    }
}