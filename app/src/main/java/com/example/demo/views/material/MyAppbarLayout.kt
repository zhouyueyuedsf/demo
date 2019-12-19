package com.example.demo.views.material

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

@CoordinatorLayout.DefaultBehavior(MyBehavior::class)
class MyAppbarLayout(context: Context, attributeSet: AttributeSet) : AppBarLayout(context, attributeSet) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val b = super.onInterceptTouchEvent(ev)
        Log.d("MyCoordinatorLayout", "MyAppbarLayout: ${b}")
        return b
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}