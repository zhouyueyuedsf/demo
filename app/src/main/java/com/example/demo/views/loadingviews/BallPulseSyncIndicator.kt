package com.example.demo.views.loadingviews

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.example.demo.utils.DeviceUtils
import java.util.*

class BallPulseSyncIndicator : BaseIndicator() {
    private val translateYFloats = FloatArray(3)
    private val alphaFloats = IntArray(3)
    val circleSpacing = DeviceUtils.dp2px(1.5f)
    override fun draw(canvas: Canvas, paint: Paint) {
        val radius = (width - circleSpacing * 2) / 6
        val x = width / 2f - (radius * 2 + circleSpacing)
        for (i in 0..2) {
            canvas.save()
            paint.alpha = alphaFloats[i]
            val translateX = x + radius * 2 * i + circleSpacing * i
            canvas.translate(translateX, translateYFloats[i])
            canvas.drawCircle(0f, 0f, radius.toFloat(), paint)
            canvas.restore()
        }
    }

    override fun onCreateAnimators(): ArrayList<ValueAnimator> {
        val animators = ArrayList<ValueAnimator>()
        val circleSpacing = DeviceUtils.dp2px(1.5f)
        val radius = (width - circleSpacing * 2) / 6
        val delays = intArrayOf(0, 100, 200)
        for (i in 0..2) {
//            val scaleAnim = ValueAnimator.ofFloat(height / 2f, height / 2f - radius * 2, height / 2f)
            val scaleAnim = ValueAnimator.ofFloat(height / 2f, height / 4f, height / 2f, 3 * height / 4f, height / 2f)
            val alphaAnim = ValueAnimator.ofFloat(1f, 0.6f, 1f, 0.6f, 1f)
            alphaAnim.repeatCount = -1
            alphaAnim.duration = 1200
            alphaAnim.startDelay = delays[i].toLong()
            alphaAnim.interpolator = LinearInterpolator()

            scaleAnim.repeatCount = -1
            scaleAnim.duration = 1200
            scaleAnim.startDelay = delays[i].toLong()
            scaleAnim.interpolator = LinearInterpolator()
            addUpdateListener(scaleAnim, ValueAnimator.AnimatorUpdateListener { animation ->
                translateYFloats[i] = animation.animatedValue as Float
                postInvalidate()
            })
            addUpdateListener(alphaAnim, ValueAnimator.AnimatorUpdateListener { animation ->
                alphaFloats[i] = ((animation.animatedValue as Float) * 255).toInt()
                postInvalidate()
            })
            animators.add(scaleAnim)
            animators.add(alphaAnim)
        }
        return animators
    }
}