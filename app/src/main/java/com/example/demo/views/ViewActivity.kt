package com.example.demo.views

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.utils.DeviceOrientationEventListener
import com.example.demo.utils.DeviceUtils
import com.example.demo.views.hindictocrlab.HindictLabActivity
import com.example.demo.views.importantview.MyDrawable
import com.example.demo.views.utils.ResourceUtils
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity(), DeviceOrientationEventListener.OnDeviceOrientationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        button1.setOnClickListener {
//            view_ocr_grid_line?.rotate90(4, 300)
//            routerTo(ImportantViewActivity::class.java)
//            scannerBarView.start()
            routerTo(HindictLabActivity::class.java)
//            val animator = ObjectAnimator.ofFloat(view_ocr_grid_line, "rotation", 0F, 90F)
//            animator.duration = 300
//            animator.start()
        }
        val drawable = ResourceUtils.getDrawable(R.drawable.ic_ocr_camera)
        val contentStr = "Translate"
        val background = MyDrawable(drawable, ResourceUtils.getColor(R.color.colorAccent))
        roundSmoothAngleImageView.layoutParams.width = background.mRestrictWidth
        roundSmoothAngleImageView.layoutParams.height = background.mRestrictHeight
        roundSmoothAngleImageView.background = background
        val orientationEventListener = DeviceOrientationEventListener(this)
        orientationEventListener.enable()
        orientationEventListener.setOnDeviceOrientationChangedListener(this)
    }

    private fun routerTo(clazz: Class<out Any>) {
        startActivity(Intent(this@ViewActivity, clazz))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onDeviceOrientationChanged(oldOri: Int, currOri: Int, isForce: Boolean) {
        Log.d("OrientationFix", "onDeviceOrientationChanged")
        view_ocr_grid_line?.rotate(currOri, 300)
//        if (currOri == DeviceOrientationEventListener.DEVICE_RIGHT_TOP_270) {
//            val animator = ObjectAnimator.ofFloat(view_ocr_grid_line, "rotation", 0F, 90F)
//            animator.duration = 300
//            animator.start()
//        }
    }

}
