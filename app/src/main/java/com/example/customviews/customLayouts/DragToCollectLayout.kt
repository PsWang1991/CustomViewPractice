package com.example.customviews.customLayouts

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.fragment_drag_to_collect.view.*

/**
 * Created by PS Wang on 2022/5/27
 */
class DragToCollectLayout(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private val onLongClickListener = OnLongClickListener { view ->
        val clipData = ClipData.newPlainText("name", view.contentDescription)
        ViewCompat.startDragAndDrop(view, clipData, DragShadowBuilder(view), null, 0)
    }

    private val onDragListener = InnerDragListener()

    override fun onFinishInflate() {
        super.onFinishInflate()
        chihuahua.setOnLongClickListener(onLongClickListener)
        android.setOnLongClickListener(onLongClickListener)
        collector.setOnDragListener(onDragListener)
    }

    private inner class InnerDragListener : OnDragListener {
        @SuppressLint("SetTextI18n")
        override fun onDrag(v: View, event: DragEvent): Boolean {

            if (event.action == DragEvent.ACTION_DROP && v is TextView) {
                v.text = v.text.toString() + event.clipData.getItemAt(0).text.toString()
            }
            return true
        }
    }
}