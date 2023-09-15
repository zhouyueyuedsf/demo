package com.example.demo.binder.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.bytedance.dali.launcher.aidl.ILauncherPromise
import com.example.demo.R
import com.example.demo.binder.IMyAidlInterface
import com.example.demo.binder.sever.BinderCursor
import kotlinx.android.synthetic.main.activity_binder.*

class BinderActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BinderActivity_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)
//        connect()
        buttonGoToLauncher.setOnClickListener {
            val intent = Intent()
            intent.action = "android.intent.action.MAIN"
            intent.addCategory("android.intent.category.HOME")
            intent.putExtra("launch_flag", "focus_screen")
            startActivity(intent)
        }
        btnBindProvider.setOnClickListener {
            val cursor = contentResolver.query(Uri.parse("content://com.example.demo.binder.sever"), null, null, null, null)
            try {

                val service = IMyAidlInterface.Stub.asInterface(cursor?.extras?.getBinder(
                    "key_binder_count"
                ))
                println("Client: ${service?.count}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }

    }

    private fun connect() {
        val packageInfo = packageManager.getPackageInfo(
            "com.bytedance.dali.launcher",
            PackageManager.GET_SERVICES
        )
        packageInfo.services.forEach {
            Log.d(
                TAG,
                "connect service name = ${it.name} packageName = ${it.packageName} and processName = ${it.processName}"
            )
        }
        val intent = Intent()
//        intent.setPackage("com.bytedance.dali.launcher")
        intent.component = ComponentName(
            "com.bytedance.dali.launcher",
            "com.bytedance.dali.launcher.aidlservices.LauncherPromiseService"
        )
        bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d(TAG, "onServiceConnected: $name service = $service")
                val remote = ILauncherPromise.Stub.asInterface(service)
                remote.promiseToFocusScreen()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d(TAG, "onServiceDisconnected: $name")
            }

            override fun onBindingDied(name: ComponentName?) {
                Log.d(TAG, "onBindingDied: $name")
            }

            override fun onNullBinding(name: ComponentName?) {
                Log.d(TAG, "onNullBinding: $name")
            }
        }, Context.BIND_AUTO_CREATE)
    }
}