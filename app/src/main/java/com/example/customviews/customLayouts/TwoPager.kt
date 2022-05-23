package com.example.customviews.customLayouts

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

/**
 * Created by PS Wang on 2022/5/18
 */
class TwoPager(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val overScroller = OverScroller(context)
    private val viewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()
    private val minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private val maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private val pagingSlope = viewConfiguration.scaledPagingTouchSlop

    private var scrolling = false
    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        children.forEach {
            it.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {

        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        var result = false

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }

            MotionEvent.ACTION_MOVE -> if (!scrolling) {
                val dx = downX - event.x
                if (abs(dx) >= pagingSlope) {
                    scrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    result = true
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }

            MotionEvent.ACTION_MOVE -> {

            }

            MotionEvent.ACTION_UP -> {

            }
        }

        return super.onTouchEvent(event)
    }
}