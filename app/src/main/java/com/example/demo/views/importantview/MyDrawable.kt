package com.example.demo.views.importantview

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.text.TextUtils
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import com.example.demo.MyApplication
import com.example.demo.utils.DeviceUtils
import kotlin.math.ceil


class MyDrawable() : Drawable() {
    private val mBackgroundPaint = Paint()
    private val mContentPaint = Paint()
    private val mType = 0
    private val rectF = RectF(60F, 60F, 120F, 120F)
    private var mBitmap: Bitmap? = null

    init {
        mBackgroundPaint.color = Color.RED
        mBackgroundPaint.isAntiAlias = true
        mContentPaint.color = Color.WHITE
        mContentPaint.textSize = 40F
        mContentPaint.isAntiAlias = true
    }

    private var bitmapWidth = 0;
    private var bitmapHeight = 0
    var mRestrictWidth = 0
    var mRestrictHeight = 0
    private var mContent: String? = null

    private var mContentRectF: RectF? = null
    constructor(content: String) : this() {
        mContent = content
        val contentRectF = getTextRectF(mContentPaint, content)
        mRestrictWidth = contentRectF.right.toInt() + DeviceUtils.dp2px(26F)
        mRestrictHeight = contentRectF.bottom.toInt() + DeviceUtils.dp2px(15F)
        mContentRectF = contentRectF
    }

    constructor(drawable: Drawable, @ColorInt backgroundColor: Int) : this() {
        mBitmap = drawable.toBitmap().also {
            bitmapWidth = it.width
            bitmapHeight = it.height
        }
        mRestrictWidth = bitmapWidth + DeviceUtils.dp2px(30F)
        mRestrictHeight = bitmapHeight + DeviceUtils.dp2px(30F)
        mBackgroundPaint.color = backgroundColor
    }

    override fun draw(canvas: Canvas) {
        mBitmap?.let {
            canvas.drawCircle(mRestrictWidth.toFloat() / 2F, mRestrictHeight.toFloat() / 2F, mRestrictWidth / 2F, mBackgroundPaint)
            val rectF = RectF((mRestrictWidth - bitmapWidth) / 2F, (mRestrictHeight - bitmapHeight) / 2F,
                bitmapWidth + (mRestrictWidth - bitmapWidth) / 2F,
                bitmapHeight + (mRestrictHeight - bitmapHeight) / 2F)
            canvas.drawBitmap(it, null, rectF, null)
            return
        }
        mContent?.let {
            val backgroundRectF = RectF(0F, 0F, mRestrictWidth.toFloat(), mRestrictHeight.toFloat())
            canvas.drawRoundRect(backgroundRectF, DeviceUtils.dp2px(24F).toFloat(), DeviceUtils.dp2px(24F).toFloat(), mBackgroundPaint)
            canvas.drawText(it, mContentRectF?.left ?: 0F, mContentRectF?.bottom ?: 0F, mContentPaint)
//            canvas.drawRect(contentRectF, mContentPaint)
        }
    }

    private fun getTextRectF(paint: Paint, str: String): RectF {
        val start = DeviceUtils.dp2px(26F).toFloat()
        val top = DeviceUtils.dp2px(15F).toFloat()
        var end = start
        var bottom = top
        if (!TextUtils.isEmpty(str)) {
            val len = str.length
            val widths = FloatArray(len) { 0F }
            paint.getTextWidths(str, widths)
            widths.forEach {
                end += ceil(it.toDouble()).toFloat()
            }
        }
        val rect = Rect()
        paint.getTextBounds(str, 0, str.length, rect)
        bottom = (top + rect.height())
        return RectF(start, top, end, bottom)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

}