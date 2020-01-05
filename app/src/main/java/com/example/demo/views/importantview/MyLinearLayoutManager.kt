package com.example.demo.views.importantview

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun onMeasure(recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    override fun computeVerticalScrollOffset(state: RecyclerView.State): Int {
        val scrollY = super.computeVerticalScrollOffset(state)
        Log.d("scrollStudy", "MyRecyclerView offset: $scrollY")
        return scrollY
    }

    override fun computeHorizontalScrollExtent(state: RecyclerView.State): Int {
        val extent = super.computeHorizontalScrollExtent(state)
        Log.d("scrollStudy", "MyRecyclerView extent: $extent")
        return extent
    }

    override fun computeVerticalScrollRange(state: RecyclerView.State): Int {
        val range = super.computeVerticalScrollRange(state)
        Log.d("scrollStudy", "MyRecyclerView Range: $range")
        return range
    }
}