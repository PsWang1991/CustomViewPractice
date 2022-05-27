package com.example.customviews.customLayouts

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs

/**
 * Created by PS Wang on 2022/5/27
 */
class DragToUpAndDown(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val dragCallback = InnerDragCallback()
    private val dragHelper = ViewDragHelper.create(this, dragCallback)

    val minFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            postInvalidateOnAnimation()
        }
    }

    private inner class InnerDragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top.coerceIn(0, height - child.height)
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (abs(yvel) > minFlingVelocity) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else {
                    dragHelper.settleCapturedViewAt(0, 0)
                }
            } else {
                if ((releasedChild.top + releasedChild.bottom) < height) {
                    dragHelper.settleCapturedViewAt(0, 0)
                } else {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                }
            }
            postInvalidateOnAnimation()
        }
    }
}