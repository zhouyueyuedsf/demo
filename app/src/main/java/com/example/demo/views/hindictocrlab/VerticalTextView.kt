package com.example.demo.views.hindictocrlab

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.ceil

class VerticalTextView(context: Context, attributeSet: AttributeSet) : AppCompatTextView(context, attributeSet) {
    private val bounds: Rect = Rect()
    private var textPaint: TextPaint? = null
    private var color = 0

    init {
        color = currentTextColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint = paint
        val rectF = getTextRectF(textPaint!!, text.toString())
        setMeasuredDimension((rectF.height().toInt() + textPaint!!.descent().toInt()) + paddingBottom + paddingTop, rectF.width().toInt() + paddingLeft + paddingRight)
    }

    override fun onDraw(canvas: Canvas) {
        textPaint!!.color = color
        canvas.rotate(90F)
        canvas.drawText((text as String), paddingLeft.toFloat(), 0F - textPaint!!.descent() - paddingBottom, textPaint!!)
    }

    private fun getTextRectF(paint: Paint, str: String): RectF {
        val start = 0.toFloat()
        val top = 0.toFloat()
        var end = start
        var bottom = top
        if (!TextUtils.isEmpty(str)) {
            val len = str.length
            val widths = FloatArray(len) { 0F }
            paint.getTextWidths(str, widths)
            widths.forEach {
                end += ceil(it.toDouble()).toFloat()
            }
        }
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        bottom = (top + rect.height())
        return RectF(start, top, end, bottom)
    }
}

