package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo.behaviors.BehaviorActivity
import com.example.demo.lifecycle.SettingsActivity
import com.example.demo.performance.PerformanceActivity
import com.example.demo.sourcecodelab.SourceActivity
import com.example.demo.viewpagers.ViewpagerActivity
import com.example.demo.views.ViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("joy", Thread.currentThread().toString())
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            routerTo(BehaviorActivity::class.java)
        }
        button2.setOnClickListener {
            routerTo(ViewActivity::class.java)
        }
        viewpagerButton.setOnClickListener {
            routerTo(ViewpagerActivity::class.java)
        }
        button4.setOnClickListener {
            routerTo(SettingsActivity::class.java)
        }
        button5.setOnClickListener {
            routerTo(PerformanceActivity::class.java)
        }
        button6.setOnClickListener {
            routerTo(SourceActivity::class.java)
        }
    }

    private fun routerTo(clazz: Class<out Any>) {
        startActivity(Intent(this@MainActivity, clazz))
    }

    override fun onResume() {
        super.onResume()
        Log.d("joy", "MainActivity onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("joy", "MainActivity onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("joy", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("joy", "MainActivity onDestroy")
    }
}
