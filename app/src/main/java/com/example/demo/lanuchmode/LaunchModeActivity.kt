package com.example.demo.lanuchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
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

    override fun onRestart() {
        Log.d("yyyyyy", "onRestart LaunchModeActivity")
        super.onRestart()
    }

    override fun onStart() {
        Log.d("yyyyyy", "onStart LaunchModeActivity")
        super.onStart()
    }

    override fun onResume() {
        Log.d("yyyyyy", "onResume LaunchModeActivity")
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d("yyyyyy", "onSaveInstanceState LaunchModeActivity")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d("yyyyyy", "onRestoreInstanceState LaunchModeActivity")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onPause() {
        Log.d("yyyyyy", "onPause LaunchModeActivity")
        super.onPause()
    }

    override fun onStop() {
        Log.d("yyyyyy", "onStop LaunchModeActivity")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("yyyyyy", "onDestroy LaunchModeActivity")
        super.onDestroy()
    }

    private fun routerTo(clazz: Class<out Any>, newTask: Boolean = false) {
        val intent = Intent(this, clazz)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

}