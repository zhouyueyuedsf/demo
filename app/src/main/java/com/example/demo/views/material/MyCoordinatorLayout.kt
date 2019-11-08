package com.example.demo.views.material

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MyCoordinatorLayout(context: Context, abbrSet: AttributeSet)
    : CoordinatorLayout(context, abbrSet) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val b = super.onInterceptTouchEvent(ev)
        Log.d("MyCoordinatorLayout", "MyCoordinatorLayout: ${b}")
        return b
    }
}