package com.example.demo.views.material

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class MyScrollBehavior(context: Context, attributeSet: AttributeSet) : AppBarLayout.ScrollingViewBehavior(context, attributeSet) {
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        val b =  super.onInterceptTouchEvent(parent, child, ev)
        Log.d("MyCoordinatorLayout", "MyScrollBehavior: ${b}")
        return b
    }
}