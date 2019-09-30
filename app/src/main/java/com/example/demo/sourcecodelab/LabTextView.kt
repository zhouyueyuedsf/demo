package com.example.demo.sourcecodelab

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView

class LabTextView(context: Context, attributeSet: AttributeSet) : TextView(context, attributeSet) {
    private val mPaint = Paint()
    init {
        mPaint.color = Color.BLACK
        mPaint.textSize = 70f
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until layout.lineCount) {
            // layout中得到的属性省略了ascent与top的offset
            Log.d("zyy", "第${i + 1}行属性\n" +
                    "lineTop = ${layout.getLineTop(i)}\n" +
                    "lineAscent = ${layout.getLineAscent(i)}\n" +
                    "baseLine = ${layout.getLineBaseline(i)}\n" +
                    "descent = ${layout.getLineDescent(i)}\n" +
                    "lineBottom = ${layout.getLineBottom(i)}\n")

            Log.d("zyy", "top:" + paint.fontMetricsInt.top +
                    " \nascent:" + paint.fontMetricsInt.ascent +
                    " \nbaseline:" + baseline +
                    "  \ndescent:" + paint.fontMetricsInt.descent +
                    " \nbottom:" + paint.fontMetricsInt.bottom
            )
        }
//        canvas?.drawText("1234", 0, 4, 0f, 80f, mPaint)

    }
}