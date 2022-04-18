package com.example.customviews.customViews

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.customviews.utils.dp

/**
 * Created by PS Wang on 2022/4/18
 */

private val foodList = listOf(
    "雞腿飯",
    "排骨飯",
    "雞排飯",
    "香腸飯",
    "叉燒飯",
    "油雞飯",
    "燒肉飯",
    "天津飯",
    "蛋炒飯",
    "什錦炒飯",
    "牛丼",
    "親子丼",
    "豬排丼",
    "牛肉麵",
    "乾麵",
    "麻辣火鍋",
    "奶油蛤蠣義大利麵",
    "田園蔬菜青醬麵",
    "沙威瑪",
    "骰子牛",
    "佛跳牆",
    "牛肉餡餅",
    "鹽焗泰國蝦",
    "四色蛋",
    "蝦仁羹麵",
    "蝦仁肉圓",
    "濃厚豚骨拉麵",
    "豆皮蕎麥冷面",
    "刺身",
)

class FoodView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var food: String = "雞腿飯"
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 48.dp
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(food, (width / 2).toFloat(), (height / 2).toFloat(), paint)
    }
}

class FoodEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String, endValue: String): String {
        val startIndex = foodList.indexOf(startValue)
        val endIndex = foodList.indexOf(endValue)
        val index =
            startIndex + ((endIndex - startIndex) * fraction).toInt()
        return foodList[index]
    }
}