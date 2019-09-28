package com.example.demo.views

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView

class MyHorizontalScrollView(context: Context, attributeSet: AttributeSet) : HorizontalScrollView(context, attributeSet) {


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        super.onLayout(changed, l, t, r, b)
    }
}