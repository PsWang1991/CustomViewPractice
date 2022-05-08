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

private val imageSize = 300.dp.toInt()

private const val EXTRA_SCALE_FACTOR = 1.5f

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    private val gestureDetector = GestureDetectorCompat(context, this)

    private val scroller = OverScroller(context)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap = getChihuahua(resources, imageSize)

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var smallerScale = 1f
    private var largerScale = 1f

    private var isLargerScale = false

    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (w - imageSize) / 2f
        originalOffsetY = (h - imageSize) / 2f

        if (bitmap.width / bitmap.height.toFloat() > w / h.toFloat()) {
            smallerScale = w / bitmap.width.toFloat()
            largerScale = h / bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        } else {
            smallerScale = h / bitmap.height.toFloat()
            largerScale = w / bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }
    }

    override fun onDraw(canvas: Canvas) {

        canvas.translate(offsetX, offsetY)

        val scale = smallerScale + (largerScale - smallerScale) * scaleFraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        downEvent: MotionEvent,
        currentEvent: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {

        if (isLargerScale) {
            offsetX -= distanceX
            offsetX = min(offsetX, (bitmap.width * largerScale - width) / 2f)
            offsetX = max(offsetX, -(bitmap.width * largerScale - width) / 2f)
            offsetY -= distanceY
            offsetY = min(offsetY, (bitmap.height * largerScale - height) / 2f)
            offsetY = max(offsetY, -(bitmap.height * largerScale - height) / 2f)
            invalidate()
        }

        return false
    }

    override fun onFling(
        downEvent: MotionEvent,
        currentEvent: MotionEvent,
        velocityX: Float,
        velocityY: Float
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

        ViewCompat.postOnAnimation(this, this)

        return false
    }

    override fun run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            ViewCompat.postOnAnimation(this, this)
        }
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isLargerScale = !isLargerScale
        if (isLargerScale) {
            scaleAnimator.start()
        } else {
            offsetX = 0f
            offsetY = 0f
            scaleAnimator.reverse()
        }
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }
}
