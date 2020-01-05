package com.example.demo.views.importantview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Attributes

class MyRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {


    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        Log.d("scrollStudy", "MyRecyclerView height: $measuredHeight")
        Log.d("scrollStudy", "MyRecyclerView offset: ${computeVerticalScrollOffset()}")
        super.onMeasure(widthSpec, heightSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }
}