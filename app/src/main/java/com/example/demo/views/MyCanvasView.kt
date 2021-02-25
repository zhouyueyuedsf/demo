package com.example.demo.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.demo.utils.dp

class MyCanvasView(mContext: Context, attributeSet: AttributeSet): View(mContext, attributeSet) {
    private val mPaint = Paint().also { it.color = Color.RED }
    private val mTextPaint = Paint().also {
        it.color = Color.WHITE
        it.textSize = 15.dp.toFloat()
    }
    override fun onDraw(canvas: Canvas?) {
        // area will be covered child views
        // draw child view
        super.onDraw(canvas)
        // area will overlay child views
        val localCanvas = canvas ?: return
        localCanvas.drawRect(0f, 0f, 100f, 300f, mPaint)
        mPaint.color = Color.BLUE
        localCanvas.drawRect(100f, 100f, 200f, 400f, mPaint)
        mPaint.color = Color.GREEN
        localCanvas.translate(150f, 150f)
        // 将蓝色矩形旋转，形成绿色矩形
        localCanvas.save()
        // 对画布操作
        localCanvas.rotate(90f, 150f, 250f)
        // 旋转之后的坐标系
        localCanvas.drawRect(100f, 100f, 200f, 400f, mPaint)
        // 恢复旋转之前的坐标系
        localCanvas.restore()
        localCanvas.drawText("蓝色矩形旋转之后的矩形", 0f, 250f, mTextPaint)
        // 基于save之前的坐标系
        mPaint.color = Color.YELLOW
        localCanvas.drawRect(300f, 300f, 400f, 600f, mPaint)

    }
}