package com.example.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.advanced.ashmem.Client
import com.example.demo.advanced.mmkv.MMKVActivity
import com.example.demo.behaviors.BehaviorActivity
import com.example.demo.lifecycle.SettingsActivity
import com.example.demo.performance.PerformanceActivity
import com.example.demo.performance.ScrollingActivity
import com.example.demo.services.ServiceActivity
import com.example.demo.sourcecodelab.SourceActivity
import com.example.demo.utils.DialogUtils
import com.example.demo.utils.PreferenceUtils
import com.example.demo.viewpagers.ViewpagerActivity
import com.example.demo.views.ViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        Log.d("yyyyyyyy", "run MainActivity onNewIntent")
        super.onNewIntent(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        Log.d("joy", Thread.currentThread().toString())
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            routerTo(BehaviorActivity::class.java)
        }
        button2.setOnClickListener {
//            val intent = Intent(this, ViewActivity::class.java)
////            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
//            start Activity(intent)
            routerTo(ViewActivity::class.java)
        }

        button2_1.setOnClickListener {
            val intent = Intent(this, PerformanceActivity::class.java)
            startActivity(intent)

            val intent2 = Intent(this, ViewActivity::class.java)
//            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent2)

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
//            DialogUtils.showMagicNoticeDialog(MyApplication.getInstance())
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
//            if (context is Activity) {
//                (context as Activity).overridePendingTransition(0, 0)
//            }
//            android.app.AlertDialog.Builder(this)
        }
        etQueryInput.setOnClickListener {
            val intent = Intent(this, ViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
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

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out)
    }
}
