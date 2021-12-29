package com.example.demo.views.importantview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

class MyButton(context: Context, attributeSet: AttributeSet) : AppCompatButton(context, attributeSet) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("eventStudy", "MyButton onTouchEvent action: ${event?.action}")
        return super.onTouchEvent(event)
    }
}