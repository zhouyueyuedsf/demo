package com.example.demo.views

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.airbnb.lottie.LottieDrawable
import com.example.demo.R
import com.example.demo.utils.DeviceOrientationEventListener
import com.example.demo.utils.dp
import kotlinx.android.synthetic.main.activity_view.*


class ViewWrapper(val view: View) {
    fun setLayoutHeight(layoutHeight: Int) {
        view.updateLayoutParams<ViewGroup.LayoutParams> {
            height = layoutHeight
        }
    }

    fun getLayoutHeight(): Int {
        return view.layoutParams.height
    }
}
class ViewActivity : AppCompatActivity(), DeviceOrientationEventListener.OnDeviceOrientationChangedListener {

    override fun onNewIntent(intent: Intent?) {
        Log.d("yyyyyyyy", "run onNewIntent")
        super.onNewIntent(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("yyyyyyyy", "run onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        lottieAnimationView.repeatCount = 0
        lottieAnimationView.repeatMode = LottieDrawable.RESTART

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
            lottieAnimationView.playAnimation()
        }
        button1.setOnClickListener {
//            val fragment = RoundedBottomSheetDialogFragment.newInstance()
//            supportFragmentManager.beginTransaction().add(R.id.contentFrame, fragment, "").commitAllowingStateLoss()
            flRoot.removeView(flAnimRoot)
            lottieAnimationView.cancelAnimation()
            val dm = resources.displayMetrics

            val height = dm.heightPixels
            val width = dm.widthPixels
            println("densityDpi = ${Resources.getSystem().displayMetrics.densityDpi}")
            println("density = ${Resources.getSystem().displayMetrics.density}")
            println("height = " + guide_view?.height + "dpHeihgt ${407.dp}")
        }
//        etOcr.setOnEditorActionListener { v, actionId, event ->
//            Log.d("InputConnectionFix", "setOnEditorActionListener: ${event.keyCode}")
//            return@setOnEditorActionListener true
//        }
//        etOcr.setOnKeyListener { v, keyCode, event ->
//            Log.d("InputConnectionFix", "setOnKeyListener: ${event.keyCode}")
//            return@setOnKeyListener true
//        }
//        button1.setOnClickListener {
//            val heightAnimator = ObjectAnimator.ofInt(etTest, "height", etTest.height, etTest.height + 70)
//            heightAnimator.addUpdateListener {
//                println("yyyyyy heightAnimator ${it.animatedValue as Int}")
//            }
//            val paddingAnimator = ObjectAnimator.ofInt(etTest.paddingBottom, etTest.paddingBottom)
////            paddingAnimator.addUpdateListener { value ->
////                etTest.apply {
////                    setPadding(paddingLeft, value.animatedValue as Int, paddingRight, value.animatedValue as Int)
////                }
////            }
//            applyPaddingAndHeightAnim(heightAnimator, paddingAnimator)
//        }
//        button2.setOnClickListener {
//            DialogUtils.showMagicNoticeDialog(this)
//        }
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
