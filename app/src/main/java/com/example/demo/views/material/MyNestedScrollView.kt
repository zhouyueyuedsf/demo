package com.example.demo.views.material

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView

class MyNestedScrollView(context: Context, attributeSet: AttributeSet) : NestedScrollView(context, attributeSet) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("eventStudy", "MyNestedScrollView dispatchTouchEvent action: ${ev?.action}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        Log.d("eventStudy", "MyNestedScrollView onInterceptTouchEvent action: ${ev?.action}")
        val b = super.onInterceptTouchEvent(ev)
        return b
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("scrollViewStudy", "height: ${getChildAt(0).measuredHeight}")
        Log.d("scrollViewStudy", "height: ${measuredHeight}")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        Log.d("eventStudy", "MyNestedScrollView onTouchEvent action: ${ev?.action}")
        return super.onTouchEvent(ev)
    }
    override fun scrollBy(x: Int, y: Int) {
        super.scrollBy(x, y)
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.d("scrollViewStudy", "dy: ${dy}")
        super.onNestedPreScroll(target, dx, dy, consumed)
        Log.d("scrollViewStudy", "dy: ${consumed[1]}")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        Log.d("scrollViewStudy", "dxConsumed: ${dxConsumed}")
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }
}