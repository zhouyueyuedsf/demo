package com.example.demo.views.importantview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat

/**
 * Create by Administrator on 2020/5/21
 */
class MyLinearLayout(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet), NestedScrollingParent2, NestedScrollingChild2 {
    var mLastTouchY = 0f
    override fun onFinishInflate() {
        super.onFinishInflate()
        isNestedScrollingEnabled = true
    }
    private val mChildHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.action
//        if (action == MotionEvent.ACTION_MOVE) {
//            return true
//        }
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastTouchY = ev.y
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("eventStudy", "MyLinearLayout onInterceptTouchEvent ACTION_MOVE ${mLastTouchY - ev.y}")
                mLastTouchY = ev.y
            }
            MotionEvent.ACTION_UP -> {
                stopNestedScroll(ViewCompat.TYPE_TOUCH)
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("eventStudy", "MyLinearLayout onTouchEvent action: ${event?.action}")
        return super.onTouchEvent(event)
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.d("eventStudy", "MyLinearLayout onNestedScrollAccepted")
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        Log.d("eventStudy", "MyLinearLayout onStartNestedScroll")
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        dispatchNestedPreScroll(dx, dy, consumed, null, type)
        Log.d("eventStudy", "MyLinearLayout onNestedPreScroll dy ${dy}")
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        Log.d("eventStudy", "MyLinearLayout onNestedScroll dxUnconsumed ${dxUnconsumed}")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        Log.d("eventStudy", "MyLinearLayout onStopNestedScroll")
        mChildHelper.onStopNestedScroll(target)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
            offsetInWindow, type)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?, type: Int): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun stopNestedScroll(type: Int) {
        mChildHelper.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mChildHelper.hasNestedScrollingParent();
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return startNestedScroll(axes, ViewCompat.TYPE_TOUCH)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mChildHelper.startNestedScroll(axes, ViewCompat.TYPE_TOUCH);
    }
}