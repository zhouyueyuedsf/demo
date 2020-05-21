package com.example.demo.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.example.demo.R
import com.example.demo.performance.PerformanceActivity
import com.example.demo.utils.DeviceOrientationEventListener
import com.example.demo.utils.DialogUtils
import com.example.demo.views.fragments.RoundedBottomSheetDialogFragment
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
        button1.setOnClickListener {
            val fragment = RoundedBottomSheetDialogFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.contentFrame, fragment, "").commitAllowingStateLoss()
        }
//        etOcr.setOnEditorActionListener { v, actionId, event ->
//            Log.d("InputConnectionFix", "setOnEditorActionListener: ${event.keyCode}")
//            return@setOnEditorActionListener true
//        }
//        etOcr.setOnKeyListener { v, keyCode, event ->
//            Log.d("InputConnectionFix", "setOnKeyListener: ${event.keyCode}")
//            return@setOnKeyListener true
//        }
        button1.setOnClickListener {
            val heightAnimator = ObjectAnimator.ofInt(etTest, "height", etTest.height, etTest.height + 70)
            heightAnimator.addUpdateListener {
                println("yyyyyy heightAnimator ${it.animatedValue as Int}")
            }
            val paddingAnimator = ObjectAnimator.ofInt(etTest.paddingBottom, etTest.paddingBottom)
//            paddingAnimator.addUpdateListener { value ->
//                etTest.apply {
//                    setPadding(paddingLeft, value.animatedValue as Int, paddingRight, value.animatedValue as Int)
//                }
//            }
            applyPaddingAndHeightAnim(heightAnimator, paddingAnimator)
        }
        button2.setOnClickListener {
            DialogUtils.showMagicNoticeDialog(this)
        }
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
