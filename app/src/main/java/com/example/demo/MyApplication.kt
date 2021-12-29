package com.example.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.demo.utils.PreferenceUtils

class MyApplication : Application() {
    companion object {
        private lateinit var instance: Application
        fun getInstance(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
//        Log.d("joy", Thread.currentThread().toString())
        instance = this
        if (PreferenceUtils.getLong("user_use_time_stream", -1) >= 0) {
            Log.d("joy", "${PreferenceUtils.getLong("user_use_time_stream", -1)}")
        }
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                PreferenceUtils.putLong("user_foreground_time_stream", System.currentTimeMillis())
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
                val lastForegroundStartTime = PreferenceUtils.getLong("user_foreground_time_stream", -1)
                if (lastForegroundStartTime >= 0) {
                    val lastUserUseTime = PreferenceUtils.getLong("user_use_time_stream", 0)
                    PreferenceUtils.putLong("user_use_time_stream", lastUserUseTime + System.currentTimeMillis() - lastForegroundStartTime)
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }



}