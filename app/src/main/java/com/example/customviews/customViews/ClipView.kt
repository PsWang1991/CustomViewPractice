package com.example.customviews.customViews

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp2px
import com.example.customviews.utils.getChihuahua

/**
 * Created by PS Wang on 2022/4/15
 */


private val photoOrigin = Point(100.dp2px, 150.dp2px)
private const val cameraRotateX = 30f
private const val canvasRotate = -15f
private const val clipExtension = 200

class ClipView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val chihuahua = getChihuahua(resources, 150.dp2px.toInt())

    private val camera = Camera().apply {
        rotateX(cameraRotateX)
        setLocation(0f, 0f, -5 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {

        canvas.save()
        canvas.translate(
            (photoOrigin.x + chihuahua.width / 2),
            (photoOrigin.y + chihuahua.height / 2)
        )
        canvas.rotate(canvasRotate)
        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -chihuahua.width / 2 - clipExtension,
            0,
            chihuahua.width / 2 + clipExtension,
            chihuahua.height / 2 + clipExtension
        )
        canvas.rotate(-canvasRotate)
        canvas.translate(
            -(photoOrigin.x + chihuahua.width / 2),
            -(photoOrigin.y + chihuahua.height / 2)
        )
        canvas.drawBitmap(chihuahua, photoOrigin.x, photoOrigin.y, paint)
        canvas.restore()


        canvas.save()
        canvas.translate(
            (photoOrigin.x + chihuahua.width / 2),
            (photoOrigin.y + chihuahua.height / 2)
        )
        canvas.rotate(canvasRotate)
//        camera.rotateX(-2 * cameraRotateX)
//        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -chihuahua.width / 2 - clipExtension,
            -chihuahua.height / 2 - clipExtension,
            chihuahua.width / 2 + clipExtension,
            0
        )
        canvas.rotate(-canvasRotate)
        canvas.translate(
            -(photoOrigin.x + chihuahua.width / 2),
            -(photoOrigin.y + chihuahua.height / 2)
        )
        canvas.drawBitmap(chihuahua, photoOrigin.x, photoOrigin.y, paint)
        canvas.restore()
    }
}