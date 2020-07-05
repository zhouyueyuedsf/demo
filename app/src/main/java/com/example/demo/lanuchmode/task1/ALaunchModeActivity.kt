package com.example.demo.lanuchmode.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo.R

class ALaunchModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_launch_mode)
    }

    override fun onRestart() {
        Log.d("yyyyyy", "onRestart ALaunchModeActivity")
        super.onRestart()
    }

    override fun onStart() {
        Log.d("yyyyyy", "onStart ALaunchModeActivity")
        super.onStart()
    }

    override fun onResume() {
        Log.d("yyyyyy", "onResume ALaunchModeActivity")
        super.onResume()
    }

    override fun onPause() {
        Log.d("yyyyyy", "onPause ALaunchModeActivity")
        super.onPause()
    }
}