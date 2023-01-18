package com.example.demo.views

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Choreographer
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.airbnb.lottie.LottieDrawable
import com.example.demo.R
import com.example.demo.utils.DeviceOrientationEventListener
import com.example.demo.utils.dp
import com.example.demo.viewpagers.ViewpagerActivity
import com.example.demo.views.importantview.ImportantViewActivity
import kotlinx.android.synthetic.main.activity_view.*


class ViewWrapper(val view: View) {
    fun setLayoutHeight(layoutHeight: Int) {
//        view.updateLayoutParams<ViewGroup.LayoutParams> {
//            height = layoutHeight
//        }
    }

    fun getLayoutHeight(): Int {
        return view.layoutParams.height
    }
}

class ViewActivity : AppCompatActivity(), DeviceOrientationEventListener.OnDeviceOrientationChangedListener {
    val choreographer = Choreographer.getInstance()
    val TAG = this.javaClass.name
    override fun attachBaseContext(newBase: Context?) {
        Log.d("yyyyyy", "ViewActivity attachBaseContext $newBase")
        super.attachBaseContext(newBase)
    }

    override fun onNewIntent(intent: Intent?) {
        Log.d("yyyyyy", "ViewActivity run onNewIntent")
        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("yyyyyy", "ViewActivity run onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        lottieAnimationView.repeatCount = 0
        lottieAnimationView.repeatMode = LottieDrawable.RESTART
        choreographer.postFrameCallback {
            // 第一帧返回，还没测量
            Handler().post {
                Log.d(TAG, "post after postFrameCallBack width = ${lottieAnimationView.width}, height = ${lottieAnimationView.height}")
            }
        }

        Handler().post {
            Log.d(TAG, "directly post width = ${lottieAnimationView.width}, height = ${lottieAnimationView.height}")
        }

        Handler().post {
            Handler().post {
                Log.d(TAG, "directly two post width = ${lottieAnimationView.width}, height = ${lottieAnimationView.height}")
            }
        }

//        showAlphaAnim()
        lottieAnimationView?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                Log.d("yyyyyy", "onAnimationRepeat")
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                Log.d("yyyyyy", "onAnimationEnd isReverse")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Log.d("yyyyyy", "onAnimationEnd")
            }

            override fun onAnimationCancel(animation: Animator?) {
                Log.d("yyyyyy", "onAnimationCancel")
            }

            override fun onAnimationStart(animation: Animator?) {
                Log.d("yyyyyy", "animation start")
                showGuideTvAlphaAnim()
            }
        })
        lottieAnimationView.post {
            Log.d("yyyyyy", "lottieAnimationView start ")
            lottieAnimationView.playAnimation()
        }
        viewpagerEnter.setOnClickListener {
            routerTo(ViewpagerActivity::class.java)
        }
        button1.setOnClickListener {
//            routerTo(ImportantViewActivity::class.java)
//            cardView.animate().setDuration(500).translationX(400f)
            ValueAnimator.ofFloat(1f, 0.2f).apply {
                duration = 500
                addUpdateListener {
                    val v = it.animatedValue as Float
                    cardView.scaleX = v
                    cardView.scaleY = v
                }
            }.start()
        }

        cardView.setOnClickListener {
            Toast.makeText(this, "click cardView", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("yyyyyy", "onResume ${this.intent.hashCode()}")
    }

    private fun showGuideTvAlphaAnim() {
        val alphaAnim = ObjectAnimator.ofFloat(tvTips, "alpha", 0f, 1.0f, 0f)
        alphaAnim.repeatMode = ObjectAnimator.RESTART
        alphaAnim.repeatCount = ObjectAnimator.INFINITE
        alphaAnim.interpolator = LinearInterpolator()
        alphaAnim.duration = 3000
        alphaAnim.start()
    }

    private fun applyPaddingAndHeightAnim(firstAnimator: ValueAnimator, secondAnimator: ValueAnimator) {
        val animSet = AnimatorSet()
        animSet.play(secondAnimator).after(firstAnimator)
        animSet.interpolator = LinearInterpolator()
        animSet.duration = 200L
        animSet.start()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        Log.d("InputConnectionFix", "dispatchKeyEvent: ${event?.keyCode}")
        return super.dispatchKeyEvent(event)
    }

    private fun routerTo(clazz: Class<out Any>) {
        startActivity(Intent(this@ViewActivity, clazz))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onDeviceOrientationChanged(oldOri: Int, currOri: Int, isForce: Boolean) {
        Log.d("OrientationFix", "onDeviceOrientationChanged")
//        view_ocr_grid_line?.rotate(currOri, 300)
//        if (currOri == DeviceOrientationEventListener.DEVICE_RIGHT_TOP_270) {
//            val animator = ObjectAnimator.ofFloat(view_ocr_grid_line, "rotation", 0F, 90F)
//            animator.duration = 300
//            animator.start()
//        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out)
    }

}
