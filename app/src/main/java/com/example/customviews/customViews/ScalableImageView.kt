package com.example.customviews.customViews

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.customviews.utils.dp
import com.example.customviews.utils.getChihuahua
import kotlin.math.max
import kotlin.math.min

/**
 * Created by PS Wang on 2022/5/12
 */

private val IMAGE_SIZE = 250.dp.toInt()
private const val EXTRA_BIG_SCALE = 2.0f

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getChihuahua(resources, IMAGE_SIZE)

    private val gestureListener = InnerGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, gestureListener)

    private val scroller = OverScroller(context)
    private val flingRunnable = FlingRunnable()

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var smallScale = 1f
    private var bigScale = 1f

    private var isBigScale = false

    private var currentScale = smallScale
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (w - bitmap.width) / 2f
        originalOffsetY = (h - bitmap.height) / 2f
        smallScale = min(w / bitmap.width.toFloat(), h / bitmap.height.toFloat())
        bigScale = max(w / bitmap.width.toFloat(), h / bitmap.height.toFloat()) * EXTRA_BIG_SCALE
        scaleAnimator.setFloatValues(smallScale, bigScale)
        currentScale = smallScale
    }

    override fun onDraw(canvas: Canvas) {

        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)

        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }


    inner class InnerGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent,
            currentEvent: MotionEvent,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {
            if (isBigScale) {
                offsetX -= distanceX
                offsetY -= distanceY
                boundOffsetInView()
                invalidate()
            }
            return false
        }

        override fun onFling(
            downEvent: MotionEvent,
            currentEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            if (isBigScale) {
                scroller.fling(
                    offsetX.toInt(), offsetY.toInt(),
                    velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt(),
                    40.dp.toInt(),
                    40.dp.toInt()
                )

                ViewCompat.postOnAnimation(this@ScalableImageView, flingRunnable)
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isBigScale = !isBigScale
            if (isBigScale) {
                offsetX = (e.x - width / 2f) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2f) * (1 - bigScale / smallScale)
                boundOffsetInView()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }

    inner class FlingRunnable : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()

                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }

    private fun boundOffsetInView() {
        offsetX = offsetX.coerceIn(-(bitmap.width * bigScale - width) / 2f,
            (bitmap.width * bigScale - width) / 2f)
        offsetY = offsetY.coerceIn(-(bitmap.height * bigScale - height) / 2f,
            (bitmap.height * bigScale - height) / 2f)
    }
}