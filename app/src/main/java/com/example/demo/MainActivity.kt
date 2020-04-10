package com.example.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.demo.advanced.ashmem.Client
import com.example.demo.advanced.mmkv.MMKVActivity
import com.example.demo.behaviors.BehaviorActivity
import com.example.demo.lifecycle.SettingsActivity
import com.example.demo.performance.PerformanceActivity
import com.example.demo.services.ServiceActivity
import com.example.demo.sourcecodelab.SourceActivity
import com.example.demo.utils.PreferenceUtils
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
        button7.setOnClickListener {
            routerTo(Client::class.java)
        }
        button8.setOnClickListener {
            routerTo(MMKVActivity::class.java)
        }
        button9.setOnClickListener {
//            AlertDialog.Builder(this).setTitle("测试 dialog状态").setPositiveButton("Router to ViewActivity") { dialog, which ->
//                routerTo(ViewActivity::class.java)
//            }.create().show()
        }
        etQueryInput.setOnClickListener {
            // 共享元素
            val intent = Intent(this, ViewActivity::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in_half, R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }
        button10.setOnClickListener {
            routerTo(ServiceActivity::class.java)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        PreferenceUtils.contains("123")
        super.attachBaseContext(newBase)
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
