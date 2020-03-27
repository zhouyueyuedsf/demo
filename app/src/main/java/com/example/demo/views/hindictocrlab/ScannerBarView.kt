package com.example.demo.views.hindictocrlab

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.demo.R


/**
 * ScannerBarView
 * <br></br>Email:1006368252@qq.com
 * <br></br>QQ:1006368252
 * <br></br>[https://github.com/JustinRoom/CameraMaskDemo](https://github.com/JustinRoom/CameraMaskDemo)
 *
 * @author jiangshicheng
 */
class ScannerBarView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {
    private var scannerBar: ImageView? = null
    private var animator: ObjectAnimator? = null

    constructor(context: Context) : this(context, null, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    //
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
//        initView(context)
//        init(context, attrs, 0)
//    }
//
    init {
        initView(context)
        val a = context.obtainStyledAttributes(attrs, R.styleable.ScannerBarView, defStyleAttr, 0)
        scannerBar?.setImageResource(a.getResourceId(R.styleable.ScannerBarView_sbvSrc, R.mipmap.camera_mask_scanner_bar))
        a.recycle()


    }

    private fun initView(context: Context) {
        scannerBar = ImageView(context)
        scannerBar?.scaleType = ImageView.ScaleType.FIT_XY
        addView(scannerBar, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount > 0) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.layout(0, 0 - child.measuredHeight, child.measuredWidth, 0)
            }
        }
    }

    fun setScannerBarImageResource(@DrawableRes drawable: Int) {
        scannerBar!!.setImageResource(drawable)
        executeRequestLayout()
    }

    fun setScannerBarImageBitmap(bitmap: Bitmap?) {
        scannerBar!!.setImageBitmap(bitmap)
        executeRequestLayout()
    }

    fun start() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(scannerBar, View.TRANSLATION_Y, 0f, height + scannerBar!!.height.toFloat())
            animator?.interpolator = LinearInterpolator()
            animator?.duration = 2000
            animator?.repeatCount = Animation.INFINITE
            animator?.start()
        }
    }

    fun pause() {
        if (animator != null && animator!!.isRunning) animator!!.pause()
    }

    fun resume() {
        if (animator != null && animator!!.isPaused) animator!!.resume()
    }

    fun stop() {
        scannerBar!!.translationY = 0f
        if (animator != null && animator!!.isStarted) {
            animator!!.cancel()
            animator = null
        }
    }

    val isRunning: Boolean
        get() = animator != null && animator!!.isRunning

    private fun executeRequestLayout() {
        val needRestart = isRunning
        stop()
        requestLayout()
        if (needRestart) start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        executeRequestLayout()
    }

    override fun onDetachedFromWindow() {
        stop()
        super.onDetachedFromWindow()
    }
}
