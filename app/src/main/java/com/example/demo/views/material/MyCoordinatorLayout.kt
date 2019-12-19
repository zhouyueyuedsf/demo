package com.example.demo.views.material

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MyCoordinatorLayout(context: Context, abbrSet: AttributeSet)
    : CoordinatorLayout(context, abbrSet) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val b = super.onInterceptTouchEvent(ev)
        Log.d("MyCoordinatorLayout", "MyCoordinatorLayout: ${b}")
        return b
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(ev)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {

        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        Log.d("MyCoordinatorLayout", "MyCoordinatorLayout onNestedScroll's dyUnconsumed: ${dyUnconsumed}")
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.d("MyCoordinatorLayout", "MyCoordinatorLayout onNestedPreScroll's dyUnconsumed: ${dy}")
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.d("MyCoordinatorLayout", "MyCoordinatorLayout onNestedPreScroll's dyUnconsumed: ${dy}")
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

}