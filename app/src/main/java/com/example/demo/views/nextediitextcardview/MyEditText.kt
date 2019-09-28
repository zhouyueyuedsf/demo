package com.example.demo.views.nextediitextcardview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.example.demo.R
import com.google.android.material.textfield.TextInputEditText

class MyEditText(context: Context, attributeSet: AttributeSet) : EditText(context, attributeSet) {

    val mLeftDrawable = context.resources.getDrawable(R.drawable.ic_clear_black_24dp)
    var mPaint = Paint(ANTI_ALIAS_FLAG)
    init{
        mPaint.setColor(context.resources.getColor(R.color.colorPrimary))
        mPaint.strokeWidth = 10f
        setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, null, null, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight + 10)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
    override fun onDraw(canvas: Canvas?) {
        Log.d("joy", "myeditText onDraw run")

        super.onDraw(canvas)
        canvas?.drawLine(mLeftDrawable.intrinsicWidth.toFloat(), height.toFloat() - 10, width.toFloat(), height - 10f, mPaint)
    }
}