package com.example.customviews.customViews

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.customviews.utils.dp
import com.example.customviews.utils.getChihuahua

private val IMAGE_SIZE = 300.dp.toInt()

private const val EXTRA_SCALE_FACTOR = 1.5f

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val simpleOnGestureListener = SimpleOnGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, simpleOnGestureListener)

    private val scaleGestureListener = ScaleGestureListener()
    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)

    private val innerRunnable = InnerRunnable()

    private val scroller = OverScroller(context)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap = getChihuahua(resources, IMAGE_SIZE)

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var smallerScale = 1f
    private var largerScale = 1f

    private var isLargerScale = false

    private var currentScale = smallerScale
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator =
        ObjectAnimator.ofFloat(this, "currentScale", smallerScale, largerScale)


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (w - IMAGE_SIZE) / 2f
        originalOffsetY = (h - IMAGE_SIZE) / 2f

        if (bitmap.width / bitmap.height.toFloat() > w / h.toFloat()) {
            smallerScale = w / bitmap.width.toFloat()
            largerScale = h / bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        } else {
            smallerScale = h / bitmap.height.toFloat()
            largerScale = w / bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }

        currentScale = smallerScale
        scaleAnimator.setFloatValues(smallerScale, largerScale)
    }

    override fun onDraw(canvas: Canvas) {

        val scaleFraction = (currentScale - smallerScale) / (largerScale - smallerScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)

        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }

    inner class SimpleOnGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent,
            currentEvent: MotionEvent,
            distanceX: Float,
            distanceY: Float,
        ): Boolean {

            if (isLargerScale) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsetByViewBounds()
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

            if (isLargerScale) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    -((bitmap.width * largerScale - width) / 2f).toInt(),
                    ((bitmap.width * largerScale - width) / 2f).toInt(),
                    -((bitmap.height * largerScale - height) / 2f).toInt(),
                    ((bitmap.height * largerScale - height) / 2f).toInt(),
                    40.dp.toInt(),
                    40.dp.toInt()
                )
            }

            ViewCompat.postOnAnimation(this@ScalableImageView, innerRunnable)

            return false
        }

        private fun fixOffsetByViewBounds() {
            offsetX = offsetX.coerceIn(
                -(bitmap.width * largerScale - width) / 2f,
                (bitmap.width * largerScale - width) / 2f
            )
            offsetY = offsetY.coerceIn(
                -(bitmap.height * largerScale - height) / 2f,
                (bitmap.height * largerScale - height) / 2f
            )
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            isLargerScale = !isLargerScale
            if (isLargerScale) {
                offsetX = (e.x - width / 2) * (1 - largerScale / smallerScale)
                offsetY = (e.y - height / 2) * (1 - largerScale / smallerScale)
                fixOffsetByViewBounds()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }

    inner class InnerRunnable : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }

    inner class ScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            val tempCurrentScale = currentScale * detector.scaleFactor
            return if (tempCurrentScale > largerScale || tempCurrentScale < smallerScale) {
                false
            } else {
                currentScale *= detector.scaleFactor
                true
            }

        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2) * (1 - largerScale / smallerScale)
            offsetY = (detector.focusY - height / 2) * (1 - largerScale / smallerScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
        }
    }
}
