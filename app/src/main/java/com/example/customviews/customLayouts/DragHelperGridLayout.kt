package com.example.customviews.customLayouts

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

/**
 * Created by PS Wang on 2022/5/25
 */

private const val COLUMNS = 2
private const val ROWS = 3

class DragHelperGridLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    var capturedLeft = 0
    var capturedTop = 0

    private val dragHelper = ViewDragHelper.create(this, DragCallback())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS
        measureChildren(
            MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        )
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        children.forEachIndexed { index, child ->
            childLeft = index % COLUMNS * childWidth
            childTop = index / COLUMNS * childHeight
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!dragHelper.continueSettling(false)) {
            dragHelper.processTouchEvent(event)
        }
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private inner class DragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation = elevation + 1
            capturedLeft = capturedChild.left
            capturedTop = capturedChild.top
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                val capturedView = dragHelper.capturedView
                if (capturedView != null) capturedView.elevation--
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            dragHelper.settleCapturedViewAt(capturedLeft, capturedTop)
            postInvalidateOnAnimation()
        }
    }
}