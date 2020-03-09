package com.example.demo.views.hindictocrlab

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_hindict_lab.*

class HindictLabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hindict_lab)
        rotate.setOnClickListener {
//            val displayWidth = llRotated.width
//            val displayHeight = llRotated.height
//            val overHeight = (displayHeight - displayWidth) / 2
//            llRotated.rotation = 90F
//            llRotated.translationX = -overHeight.toFloat()
//            llRotated.translationY = overHeight.toFloat()
////            llRotated.translationX = (-overHeight).toFloat();
//            val horizonOffset = DeviceUtils.dp2px(100F)
//
//            //得到水平放置的宽度
////            //得到水平放置的宽度
//            val layoutParams = tvRotated.layoutParams as FrameLayout.LayoutParams
//            layoutParams.width = displayHeight - 2 * horizonOffset
//
//            tvRotated.layoutParams = layoutParams
//
//            val rootLayoutParams = llRotated.layoutParams as FrameLayout.LayoutParams
//            rootLayoutParams.width = displayHeight
//            rootLayoutParams.height = displayWidth
//            llRotated.layoutParams = rootLayoutParams
        }
    }
}
