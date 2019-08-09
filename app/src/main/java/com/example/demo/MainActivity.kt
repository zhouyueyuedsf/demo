package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.behaviors.BehaviorActivity
import com.example.demo.views.ViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            routerTo(BehaviorActivity::class.java)
        }
        button2.setOnClickListener {
            routerTo(ViewActivity::class.java)
        }
    }

    private fun routerTo(clazz: Class<out Any>) {
        startActivity(Intent(this@MainActivity, clazz))
    }
}
