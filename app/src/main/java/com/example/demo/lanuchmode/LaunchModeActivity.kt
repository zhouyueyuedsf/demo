package com.example.demo.lanuchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.demo.R
import com.example.demo.lanuchmode.task1.ALaunchModeActivity
import com.example.demo.lanuchmode.task1.BLaunchModeActivity
import com.example.demo.lanuchmode.task2.CLaunchModeActivity
import com.example.demo.lanuchmode.task2.DLaunchModeActivity
import kotlinx.android.synthetic.main.activity_launch_mode.*
import kotlinx.android.synthetic.main.activity_main.*

class LaunchModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_mode)
        startAB.setOnClickListener {
            routerTo(ALaunchModeActivity::class.java)
            Handler().postDelayed({
                routerTo(BLaunchModeActivity::class.java, true)
            }, 1000)
        }

        startCD.setOnClickListener {
            routerTo(CLaunchModeActivity::class.java)
            Handler().postDelayed({
                routerTo(DLaunchModeActivity::class.java, true)
            }, 1000)
        }
    }

    private fun routerTo(clazz: Class<out Any>, newTask: Boolean = false) {
        val intent = Intent(this, clazz)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

}