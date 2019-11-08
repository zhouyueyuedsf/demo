package com.example.demo.views.material

import android.util.Log
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class MyBehavior : AppBarLayout.Behavior() {
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: AppBarLayout, ev: MotionEvent): Boolean {
        val b =  super.onInterceptTouchEvent(parent, child, ev)
        Log.d("MyCoordinatorLayout", "Behavior: ${b}")
        return b
    }
}