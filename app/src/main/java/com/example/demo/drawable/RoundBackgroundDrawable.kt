package com.youdao.hindict.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.graphics.toRectF
import com.example.demo.utils.dp

/**
 * Create by Administrator on 2020/3/29
 */

class RoundBackgroundDrawable : Drawable() {
    private val mMainPaint: Paint = Paint()
    private val mOverLayPaint: Paint = Paint()

    private var mMainPath: Path = Path()
    private var mOverlayPath: Path = Path()
    private var mRadius = floatArrayOf(10.dp.toFloat(), 10.dp.toFloat(), 10.dp.toFloat(), 10.dp.toFloat(), 0f, 0f, 0f, 0f)

    init {
        setColor()
//        setAlpha()
    }

    fun setColor(@ColorInt mainColor: Int = Color.WHITE, @ColorInt overlayColor: Int = Color.parseColor("#999999")) {
        mMainPaint.color = mainColor
        mOverLayPaint.color = overlayColor
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
//        mMainPath.addRoundRect(bounds.toRectF(), mRadius, Path.Direction.CCW)
//        mOverlayPath.addRect(bounds.toRectF(), Path.Direction.CCW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mOverlayPath.op(mMainPath, Path.Op.DIFFERENCE)
        }
    }

    fun setRadius(radius: FloatArray) {
        mRadius = radius
//        mMainPath.addRoundRect(bounds.toRectF(), mRadius, Path.Direction.CCW)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mMainPath, mMainPaint)
        canvas.drawPath(mOverlayPath, mOverLayPaint)
    }

    fun setAlpha(mainAlpha: Int = 255, overlayAlpha: Int = 102) {
        mMainPaint.alpha = mainAlpha
        mOverLayPaint.alpha = overlayAlpha
    }

    override fun setAlpha(alpha: Int) {
        mMainPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

}