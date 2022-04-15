package com.example.customviews.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.customviews.R

/**
 * Created by PS Wang on 2022/4/9
 */

val Float.dp2px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp2px: Float
    get() = this.toFloat().dp2px

fun getChihuahua(resources: Resources, height: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.chihuahua, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outHeight
    options.inTargetDensity = height
    return BitmapFactory.decodeResource(resources, R.drawable.chihuahua, options)
}