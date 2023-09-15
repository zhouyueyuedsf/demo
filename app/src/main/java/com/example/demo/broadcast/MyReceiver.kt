package com.example.demo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("yyyyyyyyy", "onReceive: ${intent?.component} ${intent?.`package`}")
    }
}