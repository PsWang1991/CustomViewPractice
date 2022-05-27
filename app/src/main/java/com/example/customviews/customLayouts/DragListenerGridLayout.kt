package com.example.customviews.customLayouts

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

/**
 * Created by PS Wang on 2022/5/25
 */

private const val COLUMNS = 2
private const val ROWS = 3

class DragListenerGridLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val orderedChildren = mutableListOf<View>()
    private var draggedView: View? = null
    private val onDragListener = InnerDragListener()

    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        children.forEach { child ->
            orderedChildren.add(child)
            child.setOnLongClickListener {
                draggedView = it
                it.startDrag(null, DragShadowBuilder(it), it, 0)
                false
            }
            child.setOnDragListener(onDragListener)
        }
    }

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
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    private inner class InnerDragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> if (v === event.localState)
                    v.visibility = View.INVISIBLE

                DragEvent.ACTION_DRAG_ENTERED -> if (event.localState !== v) sort(v)

                DragEvent.ACTION_DRAG_ENDED -> if (v === event.localState)
                    v.visibility = View.VISIBLE

            }
            return true
        }
    }

    private fun sort(enteredView: View) {
        var enteredIndex = -1
        var draggedIndex = -1
        orderedChildren.forEachIndexed { index, view ->
            if (enteredView === view) {
                enteredIndex = index
            } else if (draggedView === view) {
                draggedIndex = index
            }
        }
        orderedChildren[draggedIndex] = orderedChildren[enteredIndex]
        orderedChildren[enteredIndex] = draggedView!!
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        orderedChildren.forEachIndexed { index, child ->
            childLeft = index % COLUMNS * childWidth
            childTop = index / COLUMNS * childHeight
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
                .setDuration(100)
        }
    }
}