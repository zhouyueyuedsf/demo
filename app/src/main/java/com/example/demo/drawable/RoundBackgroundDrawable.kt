package com.example.demo.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.graphics.toRectF
import com.example.demo.utils.DeviceUtils

/**
 * Create by Administrator on 2020/3/29
 */

class RoundBackgroundDrawable : Drawable() {
    private val mMainPaint: Paint = Paint()
    private val mOverLayPaint: Paint = Paint()

    private var mMainPath: Path = Path()
    private var mOverlayPath: Path = Path()
    private val radius = floatArrayOf(30f, 30f, 30f, 30f, 0f, 0f, 0f, 0f)
    init {
        mMainPaint.color = Color.WHITE
        mOverLayPaint.color = Color.parseColor("#999999")
    }
    fun setColor(@ColorInt mainColor: Int = Color.WHITE, @ColorInt overlayColor: Int = Color.parseColor("#999999")) {
        mMainPaint.color = mainColor
        mOverLayPaint.color = overlayColor
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        mMainPath.addRoundRect(bounds.toRectF(), radius, Path.Direction.CCW)
        mOverlayPath.addRect(bounds.toRectF(), Path.Direction.CCW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mOverlayPath.op(mMainPath, Path.Op.DIFFERENCE)
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mMainPath, mMainPaint)
        canvas.drawPath(mOverlayPath, mOverLayPaint)
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