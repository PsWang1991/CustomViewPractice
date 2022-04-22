package com.example.customviews.customViews

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.customviews.R
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/4/22
 */

private val FLOATING_LABEL_HEIGHT = 20.dp
private val FLOATING_LABEL_OFFSET_X = 6.dp
private val FLOATING_LABEL_OFFSET_Y = 20.dp
private val FLOATING_LABEL_SHIFT = 20.dp

class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    var useFloatingLabel = false
        set(value) {
            if (value == field) return
            field = value
            if (field) {
                setPadding(
                    paddingLeft,
                    paddingTop + FLOATING_LABEL_HEIGHT.toInt(),
                    paddingRight,
                    paddingBottom
                )
            } else {
                setPadding(
                    paddingLeft,
                    paddingTop - FLOATING_LABEL_HEIGHT.toInt(),
                    paddingRight,
                    paddingBottom
                )
            }
        }

    var animationFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var floatingLabelShown = false

    private val floatingLabelAnimator by lazy {
        ObjectAnimator.ofFloat(this, "animationFraction", 1f, 0f)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }

    init {

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel = typeArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true)
        typeArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (floatingLabelShown && text.isNullOrEmpty()) {
            floatingLabelShown = false
            floatingLabelAnimator.start()
        } else if (!floatingLabelShown && !text.isNullOrEmpty()) {
            floatingLabelShown = true
            floatingLabelAnimator.reverse()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.alpha = (0xff * animationFraction).toInt()
        canvas.drawText(
            hint.toString(),
            FLOATING_LABEL_OFFSET_X,
            FLOATING_LABEL_OFFSET_Y + FLOATING_LABEL_SHIFT * (1 - animationFraction),
            paint
        )
    }
}