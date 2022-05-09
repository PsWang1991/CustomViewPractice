package com.example.customviews.customLayouts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 * Created by PS Wang on 2022/4/28
 */
class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val childrenBounds = mutableListOf<Rect>()

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val layoutSpecWidth = MeasureSpec.getSize(widthMeasureSpec)
        val layoutSpecMode = MeasureSpec.getMode(widthMeasureSpec)

        var widthUsed = 0
        var heightUsed = 0

        var maxLineWidth = 0
        var maxLineHeight = 0

        children.forEachIndexed { index, child ->

            measureChildWithMargins(child,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                heightUsed)

            if (layoutSpecMode != MeasureSpec.UNSPECIFIED && widthUsed + child.measuredWidth > layoutSpecWidth) {
                widthUsed = 0
                heightUsed += maxLineHeight
                maxLineHeight = 0

                measureChildWithMargins(child,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    heightUsed)
            }

            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(
                widthUsed,
                heightUsed,
                widthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight)

            widthUsed += child.measuredWidth
            maxLineWidth = max(widthUsed, maxLineWidth)
            maxLineHeight = max(maxLineHeight, child.measuredHeight)

        }

        val selfWidth = maxLineWidth
        val selfHeight = heightUsed + maxLineHeight

        setMeasuredDimension(selfWidth, selfHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEachIndexed { index, child ->
            val bound = childrenBounds[index]
            child.layout(bound.left, bound.top, bound.right, bound.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}