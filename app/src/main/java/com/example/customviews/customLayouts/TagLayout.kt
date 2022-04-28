package com.example.customviews.customLayouts

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

/**
 * Created by PS Wang on 2022/4/28
 */
class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)

        var usedWidth = 0

        children.forEachIndexed { index, child ->

//            val childLayoutParams = child.layoutParams
//
//            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed)
//
//            child.measure(childWidthSpec, childHeightSpec)
//
//            val childBounds = childrenBounds[index]
//            childBounds.set(?, ?, ?, ?)
        }

        val selfWidth = 0
        val selfHeight = 0

        setMeasuredDimension(selfWidth, selfHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEachIndexed { index, child ->
            val bound = childrenBounds[index]
            child.layout(bound.left, bound.top, bound.right, bound.bottom)
        }
    }
}