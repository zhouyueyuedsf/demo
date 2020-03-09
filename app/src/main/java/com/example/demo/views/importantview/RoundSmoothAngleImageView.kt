package com.example.demo.views.importantview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.demo.R


class RoundSmoothAngleImageView(context: Context, attributes: AttributeSet) : AppCompatImageView(context, attributes) {
    private var mPath: Path = Path()
    private val srcPath: Path = Path()
    private var mPaint: Paint = Paint()
    private var mSrcRectF: RectF = RectF()

    private val defaultRadius = 0f
    private var radius = 0f
    private var leftTopRadius = 0f
    private var rightTopRadius = 0f
    private var rightBottomRadius = 0f
    private var leftBottomRadius = 0f

    private var xfermode: Xfermode = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
        PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    } else {
        PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }
    private val srcRadii: FloatArray

    init {
        val ta = context.obtainStyledAttributes(attributes, R.styleable.RoundSmoothAngleImageView)
        radius = ta.getDimension(R.styleable.RoundSmoothAngleImageView_globalRadius, defaultRadius)
        leftTopRadius = ta.getDimension(R.styleable.RoundSmoothAngleImageView_leftTopRadius, defaultRadius)
        rightTopRadius = ta.getDimension(R.styleable.RoundSmoothAngleImageView_rightTopRadius, defaultRadius)
        rightBottomRadius = ta.getDimension(R.styleable.RoundSmoothAngleImageView_rightBottomRadius, defaultRadius)
        leftBottomRadius = ta.getDimension(R.styleable.RoundSmoothAngleImageView_LeftBottomRadius, defaultRadius)
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius
        }
        srcRadii = floatArrayOf(leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius)
        ta.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setSrcRectF(w, h)
    }

    private fun setSrcRectF(w: Int, h: Int) {
        mSrcRectF.set(0f + paddingLeft, 0f + paddingTop, w.toFloat() - paddingRight - paddingLeft, h.toFloat() - paddingTop - paddingBottom)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.saveLayer(mSrcRectF, null, Canvas.ALL_SAVE_FLAG)
        // ImageView自身的绘制流程，即绘制图片
        super.onDraw(canvas)
        mPaint.reset()
        mPath.reset()
        // 给path添加一个圆角矩形
        mPath.addRoundRect(mSrcRectF, srcRadii, Path.Direction.CCW)
        mPaint.isAntiAlias = true
        // 画笔为填充模式
        mPaint.style = Paint.Style.FILL
        // 设置混合模式
        mPaint.xfermode = xfermode
        // 绘制path
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            canvas?.drawPath(mPath, mPaint)
        } else {
            srcPath.addRect(mSrcRectF, Path.Direction.CCW)
            // 计算tempPath和path的差集
            srcPath.op(mPath, Path.Op.DIFFERENCE)
            canvas?.drawPath(srcPath, mPaint)
        }
        // 清除Xfermode
        mPaint.xfermode = null
        // 恢复画布状态
        canvas?.restore()
    }
}