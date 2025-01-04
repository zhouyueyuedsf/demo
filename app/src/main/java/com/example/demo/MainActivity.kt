package com.example.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.advanced.ashmem.Client
import com.example.demo.advanced.mmkv.MMKVActivity
import com.example.demo.behaviors.BehaviorActivity
import com.example.demo.binder.client.BinderActivity
import com.example.demo.broadcast.BroadcastActivity
import com.example.demo.event.EventActivity
import com.example.demo.fragment.FragEntranceMainActivity
import com.example.demo.gps.GpsActivity
import com.example.demo.lanuchmode.LaunchModeActivity
import com.example.demo.lifecycle.SettingsActivity
import com.example.demo.media.MediaActivity
import com.example.demo.performance.PerformanceActivity
import com.example.demo.services.ServiceActivity
import com.example.demo.sourcecodelab.SourceActivity
import com.example.demo.utils.PreferenceUtils
import com.example.demo.viewpagers.ViewpagerActivity
import com.example.demo.views.AccessibilityGuideActivity
import com.example.demo.views.ViewActivity
import com.example.demo.views.toolbar.ToolbarActivity
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button10
import kotlinx.android.synthetic.main.activity_main.button11
import kotlinx.android.synthetic.main.activity_main.button12
import kotlinx.android.synthetic.main.activity_main.button13
import kotlinx.android.synthetic.main.activity_main.button14
import kotlinx.android.synthetic.main.activity_main.button15
import kotlinx.android.synthetic.main.activity_main.button16
import kotlinx.android.synthetic.main.activity_main.button17
import kotlinx.android.synthetic.main.activity_main.button18
import kotlinx.android.synthetic.main.activity_main.button19
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.button2_1
import kotlinx.android.synthetic.main.activity_main.button4
import kotlinx.android.synthetic.main.activity_main.button5
import kotlinx.android.synthetic.main.activity_main.button6
import kotlinx.android.synthetic.main.activity_main.button7
import kotlinx.android.synthetic.main.activity_main.button8
import kotlinx.android.synthetic.main.activity_main.button9
import kotlinx.android.synthetic.main.activity_main.etQueryInput
import kotlinx.android.synthetic.main.activity_main.test_hover_image
import kotlinx.android.synthetic.main.activity_main.viewpagerButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ThreadTest {
    fun f() {
        val thread = Looper.getMainLooper().thread
        Log.i("yyyyyyyy", "f: ${Thread.currentThread()} ${thread}")
    }
}
class MainActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        Log.d("yyyyyyyy", "run MainActivity onNewIntent")
        super.onNewIntent(intent)
    }

    fun traverseViewTree(view: View) {
        // 打印当前View的ID和类名
        println("View  ID: " + view.id + ", Class: " + view.javaClass.simpleName)

        // 如果是ViewGroup，则递归遍历其子View
        if (view is ViewGroup) {
            val viewGroup = view
            if (viewGroup.background != null) {
                Log.d("yyyyyyyy", "viewGroup = $view background = ${viewGroup.background.bounds}")
            }
            for (i in 0 until viewGroup.childCount) {
                traverseViewTree(viewGroup.getChildAt(i))
            }
        } else {
            if (view.background != null) {
                Log.d("yyyyyyyy", "view = $view background = ${view.background.bounds}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
//        GlobalScope.launch {
//            val appRes = MyApplication.getInstance().resources
//            val appTheme = MyApplication.getInstance().theme
//            val start = System.currentTimeMillis()
//            appRes.getDrawable(R.drawable.layer_drawble_studay, appTheme)
//            Log.d("yyyyyyyy", "pre load res ${System.currentTimeMillis() - start}")
//            val outValue = TypedValue()
//            appRes.getValue(R.drawable.layer_drawble_studay, outValue, true)
//            Log.d("yyyyyyyy", "onCreate outvalue: ${outValue.assetCookie} ${outValue.data} ${outValue.string}" +
//                    " ${outValue.assetCookie.toLong() shl 32 or outValue.data.toLong()}")
//        }
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        MainScope().launch {
            delay(5000)
            val appRes = MyApplication.getInstance().resources
            val actRes = resources
            val resId = R.drawable.md_corner_shape

            val appTheme = MyApplication.getInstance().theme
            Log.d("yyyyyyyy", "app res = $appRes  act r2 = $actRes app theme = ${appTheme}")
            Log.d("yyyyyyyy", "resid = ${resId}")

            val start = System.currentTimeMillis()
            val appDrawable = appRes.getDrawable(resId, appTheme)
            Log.d("yyyyyyyy", "get resid cost = ${System.currentTimeMillis() - start}")
            val appCs = appDrawable.constantState

            val start2 = System.currentTimeMillis()
            val appCs2 = appRes.getDrawable(resId, appTheme).constantState
            Log.d("yyyyyyyy", "get resid2 cost = ${System.currentTimeMillis() - start2}")

            val appCs3 = appRes.getDrawable(resId, null).constantState
            val actCs = actRes.getDrawable(resId).constantState
            Log.d("yyyyyyyy", "app const state = $appCs app const state2 =${appCs2} app consta state3 = ${appCs3} act const state = $actCs")

            val colorResId = R.color.BGInput

            Log.d("yyyyyyyy", "appRes color = ${appRes.getColor(colorResId, null)} act color = ${actRes.getColor(colorResId, null)}")

            val u1mode = appRes.configuration.uiMode
            val u2mode = actRes.configuration.uiMode
            Log.d("yyyyyyyy", "onCreate: u1mode = $u1mode u2mode = $u2mode")
        }

        MainScope().launch {
            delay(4000)
            traverseViewTree(this@MainActivity.findViewById<View>(R.id.srcollview_act))
        }

        Log.d("joy", Thread.currentThread().toString())
        setContentView(R.layout.activity_main)
        GlobalScope.launch {
            ThreadTest().f()
        }
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
//            val intent = Intent(this, PerformanceActivity::class.java)
//            startActivity(intent)

            val intent2 = Intent(this, ViewActivity::class.java)
            intent2.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent2)


            val intent3 = Intent(this, ViewActivity::class.java)

            Handler().postDelayed({
                intent3.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent3)
                                  },500L)

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

        test_hover_image.setOnHoverListener { v, event ->
            Log.d("yyyyyy", "onCreate: ${event.action}")
            true
        }

        button9.setOnClickListener {
//            {"cv":0.0,"cw":0.0,"h":1.3503447387640795,"hv":0.0,"hw":0.0,"l":Infinity,"r":0.0}
            Log.i("yyyyyyyyyyyyyyyyyyy", "onCreate: onCollect start")
            CtlDataReceiverContract.onCollect(this, 3000, 1000, "{\"cv\":0.0,\"cw\":0.0,\"h\":1.3503447387640795,\"hv\":0.0,\"hw\":0.0,\"l\":Infinity,\"r\":0.0}")
            Log.i("yyyyyyyyyyyyyyyyyyy", "onCreate: onCollect end")
//            DialogUtils.showMagicNoticeDialog(MyApplication.getInstance())
//            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
//            startActivity(intent)
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

        button11.setOnClickListener {
            routerTo(LaunchModeActivity::class.java)
        }

        button12.setOnClickListener {
            routerTo(GpsActivity::class.java)
        }

        button13.setOnClickListener {
            routerTo(BinderActivity::class.java)
        }

        button14.setOnClickListener {
            routerTo(MediaActivity::class.java)
        }

        button15.setOnClickListener {
            routerTo(ToolbarActivity::class.java)
        }

        button16.setOnClickListener {
            routerTo(BroadcastActivity::class.java)
        }

        button17.setOnClickListener {
            routerTo(EventActivity::class.java)
        }

        button18.setOnClickListener {
            routerTo(AccessibilityGuideActivity::class.java)
        }

        button19.setOnClickListener {
            routerTo(FragEntranceMainActivity::class.java)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        PreferenceUtils.contains("123")
        Log.d("yyyyyy", "MainActivity attachBaseContext $newBase")
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("joy", "MainActivity onKeyDown keyCode = $keyCode")
        return super.onKeyDown(keyCode, event)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out)
    }
}
