package com.example.demo.views.hindictocrlab

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.marginTop
import com.example.demo.R
import com.example.demo.utils.DeviceOrientationEventListener
import com.example.demo.utils.DeviceUtils
import org.w3c.dom.Text
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.log
import kotlin.math.sin

/**
 * author yaodh
 */
class OcrGridLineView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var textMarinBottom = 0f
    private var lineWidth = 0f
    private var lineColor = 0
    private var linePaint: Paint? = null
    private var textPaint: TextPaint? = null
    private var textSize = 0f
    private var textColor = 0
    private var text: CharSequence = ""
    private var textPadding = 0f
    private var staticLayout: TextView? = null
    private fun initFromAttributes(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.OcrGridLineView)
        lineWidth = a.getDimension(R.styleable.OcrGridLineView_linesWidth,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, context.resources.displayMetrics))
        textSize = a.getDimension(R.styleable.OcrGridLineView_tipTextSize,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.resources.displayMetrics))
        textMarinBottom = a.getDimension(R.styleable.OcrGridLineView_textMarginBottom,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11.75f, context.resources.displayMetrics))
        textPadding = a.getDimension(R.styleable.OcrGridLineView_tipTextPadding, 0f)
        textColor = a.getColor(R.styleable.OcrGridLineView_tipTextColor, Color.WHITE)
        lineColor = a.getColor(R.styleable.OcrGridLineView_linesColor, Color.WHITE)
        text = a.getText(R.styleable.OcrGridLineView_tipText)
        a.recycle()
    }

    fun setHint(@StringRes resId: Int) {
        setHint(resources.getString(resId))
    }

    fun setHint(hint: CharSequence) {
        text = hint
        val textWidth = (DeviceUtils.getScreenWidth() - textPadding * 2).toInt()
        staticLayout = TextView(context)
        staticLayout?.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        staticLayout?.text = text
        staticLayout?.setTextColor(textColor)
        addView(staticLayout)
//        (staticLayout?.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER_HORIZONTAL
//        staticLayout = StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1F, 1F, true)
        invalidate()
    }

    val mPath = Path()

    init {

    }

    private fun initPaint() {
        linePaint = Paint()
        linePaint!!.color = lineColor
        linePaint!!.strokeWidth = lineWidth
        linePaint!!.style = Paint.Style.FILL
        linePaint!!.isAntiAlias = true
        textPaint = TextPaint()
        textPaint!!.textSize = textSize
        textPaint!!.color = textColor
        textPaint!!.style = Paint.Style.FILL
        textPaint!!.isAntiAlias = true
    }

    var mCurrOrientation = DeviceOrientationEventListener.DEVICE_TOP_TOP_0
    var mCurDegree = 0F
    var translateS = 0F
    var translateT = 0F
    /**
     * 当前需要旋转的度数，顺时针为正，逆时针为负
     */
    var mCurNeedRotateDegree = 0F

    fun rotate(currOrientation: Int, duration: Long) {
        mCurrOrientation = currOrientation
        staticLayout?.let {
            val translationX = (textMarinBottom + it.height / 2 + width / 6)
            val translationY = (it.height / 2 + textMarinBottom + height / 6)
            when (mCurrOrientation) {
                DeviceOrientationEventListener.DEVICE_RIGHT_TOP_270 -> {
                    it.animate().apply {
                        translationX(translationX)
                        translationY(translationY)
                        rotation(90F)
                        setDuration(300)
                    }

                }
                DeviceOrientationEventListener.DEVICE_TOP_TOP_0 -> {
                    it.animate().apply {
                        translationX(0F)
                        translationY(0F)
                        rotation(0F)
                        setDuration(300)
                    }
                }
                DeviceOrientationEventListener.DEVICE_LEFT_TOP_90 -> {
                    it.animate().apply {
                        translationX(-translationX)
                        translationY(translationY)
                        rotation(-90F)
                        setDuration(300)
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun animStep(i: Int, duration: Long) {

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        staticLayout?.let {
            (it.layoutParams as FrameLayout.LayoutParams).topMargin = (height / 3 - it.measuredHeight - textMarinBottom).toInt()
        }
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        val deltaX = width / 3f
        val deltaY = height / 3f
        // 画网格线
        for (i in 0..1) { // 水平线
            canvas.drawLine(0f, deltaY * (i + 1), width.toFloat(), deltaY * (i + 1), linePaint!!)
            // 竖直线
            canvas.drawLine(deltaX * (i + 1), 0f, deltaX * (i + 1), height.toFloat(), linePaint!!)
        }

        // 画文本
//        if (!TextUtils.isEmpty(text)) {
//            canvas.save()
//            canvas.translate(textPadding, staticLayoutY)
//            staticLayout?.draw(canvas)
//            canvas.restore()
//        }
        // 坐标变换
        when (mCurrOrientation) {
            DeviceOrientationEventListener.DEVICE_RIGHT_TOP_270 -> {
//                canvas.save()
//                val staticLayoutY = deltaY - staticLayout!!.height - textMarinBottom
//                val staticWidth = staticLayout!!.width.toFloat() ?: 0F
//                val staticLayoutX = (width.toFloat() - staticWidth) / 2
//                animStepIntern(canvas, staticLayoutX, staticLayoutY, mCurNeedRotateDegree)
//                staticLayout?.draw(canvas)
//                canvas.restore()
            }
            DeviceOrientationEventListener.DEVICE_TOP_TOP_0 -> {
//                canvas.save()
//                val staticLayoutX = deltaX - staticLayout?.height!! - textMarinBottom
//                val staticLayoutWidth = staticLayout?.width?.toFloat() ?: 0F
//                val staticLayoutY = (height - staticLayoutWidth) / 2
//                    Log.d("rotateFix", "0 mCurNeedRotateDegree: ${mCurNeedRotateDegree}")
//                inverseAnimStepIntern(canvas, staticLayoutX, staticLayoutY, mCurNeedRotateDegree)
//                staticLayout?.draw(canvas)
//                canvas.restore()
            }
            DeviceOrientationEventListener.DEVICE_LEFT_TOP_90 -> {

            }
            else -> {

            }
        }
//        if (mCurrOrientation == DeviceOrientationEventListener.DEVICE_RIGHT_TOP_270) {
//            canvas.save()
//            val radians = Math.toRadians(mCurDegree.toDouble()).toFloat()
//            canvas.rotate(mCurDegree)
//            val staticWidth = staticLayout?.getLineWidth(0) ?: 0F
//            val staticLayoutX = (measuredWidth - staticWidth) / 2
//            val otherPivotX = staticLayoutX - measuredWidth / 2
//            val otherPivotY = measuredHeight / 2 - staticLayoutY
//            var s = (otherPivotX) * cos(radians) + (otherPivotY) * sin(radians)
//            var t = (otherPivotY) * cos(radians) - (otherPivotX) * sin(radians)
//            s += measuredWidth / 2
//            t = measuredHeight / 2 - t
//            canvas.translate(s * cos(radians) + t * sin(radians) - staticLayoutX + textPadding,
//                t * cos(radians) - s * sin(radians))
//            staticLayout?.draw(canvas)
//        }
    }

    private fun inverseAnimStepIntern(canvas: Canvas, staticLayoutX: Float, staticLayoutY: Float, degree: Float) {
        val radians = Math.toRadians(degree.toDouble()).toFloat()
//        val (s, t) = calTranslateSAndT(staticLayoutX, staticLayoutY, radians)
//        Log.d("translateFix", "inverseAnimStepIntern translateS ${s} translateT: ${t}")
//        canvas.translate(s, t)
        canvas.rotate(30F)
    }

    private fun animStepIntern(canvas: Canvas, staticLayoutX: Float, staticLayoutY: Float, degree: Float) {
        val radians = Math.toRadians(degree.toDouble()).toFloat()
        canvas.rotate(degree)
        val (s, t) = calTranslateSAndT(staticLayoutX, staticLayoutY, radians)
        canvas.translate(s.also { translateS = it },
            t.also { translateT = it })
        Log.d("translateFix", "animStepIntern translateS ${translateS} translateT: ${translateT}")
    }

    private fun calTranslateSAndT(staticLayoutX: Float, staticLayoutY: Float, radians: Float): Pair<Float, Float> {
        val otherPivotX = staticLayoutX - measuredWidth / 2
        val otherPivotY = measuredHeight / 2 - staticLayoutY
        var s = (otherPivotX) * cos(radians) + (otherPivotY) * sin(radians)
        var t = (otherPivotY) * cos(radians) - (otherPivotX) * sin(radians)
        s += measuredWidth / 2
        t = measuredHeight / 2 - t
        return Pair((s * cos(radians) + t * sin(radians) - staticLayoutX + textPadding),
            (t * cos(radians) - s * sin(radians)))
    }

    companion object {
        const val TAG = "OcrGridLineView"
    }

    init {
        initFromAttributes(context, attrs)
        initPaint()
        setHint(text)
        setWillNotDraw(false)
    }
}