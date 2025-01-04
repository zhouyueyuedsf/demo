package com.example.demo.advanced.mmkv

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.demo.advanced.ashmem.MemoryService
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.mmkv_activity.*


/**
 * create by yueyue_projects on
 */

class MMKVServer : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {

        MMKV.initialize(this)
        val mmkv = MMKV.mmkvWithID("InterProcessKV", MMKV.MULTI_PROCESS_MODE).decodeString("MMKV_PUT_STRING", "")
//        Log.d("PUT_STRING", mmkv)
        print(mmkv)
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }
}