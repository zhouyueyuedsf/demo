package com.example.demo.views.flowlayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.RenderNode
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.material.chip.Chip

class MyChip(context: Context, attributeSet: AttributeSet) : Chip(context, attributeSet) {
    @RequiresApi(Build.VERSION_CODES.Q)
    val renderNode = RenderNode("")
    override fun toggle() {
        super.toggle()
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d(TAG, "onDraw: $this ${canvas?.width} ${canvas?.height}")
        super.onDraw(canvas)
    }

    companion object {
        const val TAG = "MyChip"
    }
}