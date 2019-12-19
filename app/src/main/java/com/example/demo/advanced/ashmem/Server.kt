package com.example.demo.advanced.ashmem

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


/**
 * create by yueyue_projects on
 */

class Server : Service() {

    private var memoryService: MemoryService? = null

    override fun onBind(intent: Intent): IBinder? {
        if (memoryService != null) {
            return memoryService
        }
        return MemoryService().also { memoryService = it }
    }

    override fun onCreate() {
        Log.i(LOG_TAG, "Create Memory Service...")
        try {
//            ServiceManager.addService("AnonymousSharedMemory", memoryService)
            Log.i(LOG_TAG, "Succeed to add memory service.")
        } catch (ex: RuntimeException) {
            Log.i(LOG_TAG, "Failed to add Memory Service.")
            ex.printStackTrace()
        }

    }

    override fun onStart(intent: Intent, startId: Int) {
        Log.i(Companion.LOG_TAG, "Start Memory Service.")
    }

    override fun onDestroy() {
        Log.i(Companion.LOG_TAG, "Destroy Memory Service.")
    }

    companion object {
        private val LOG_TAG = "MemoryService"
    }
}