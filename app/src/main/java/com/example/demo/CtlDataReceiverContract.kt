package com.example.demo

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.GsonUtils

/**
 * 接收FitService传输过来的数据，交给FitDataReceiver处理
 * 使用ContentProvider实现，可以很方便地切换收集数据的进程
 */
object CtlDataReceiverContract {
    private const val TAG = "DataReceiveProvider"
    const val CALL_ON_CREATE = "call_on_create"
    const val CALL_ON_DESTROY = "call_on_destroy"
    const val CALL_ON_COLLECT = "call_on_collect"

    const val KEY_HEAD_LOCAL = "key_head_local"
    const val KEY_HEAD_GLOBAL = "key_head_global"
    const val KEY_LEFT_LOCAL = "key_left_local"
    const val KEY_LEFT_GLOBAL = "key_left_global"
    const val KEY_RIGHT_LOCAL = "key_right_local"
    const val KEY_RIGHT_GLOBAL = "key_right_global"
    const val KEY_HMD = "key_hmd"
    const val KEY_SWIFT = "key_swift"
    const val KEY_MINOR_TIME_MILLS = "minor_time_mills"
    const val KEY_MAJOR_TIME_MILLS = "major_time_mills"

    const val JSON_KEY = "CtlDataContract"
    private const val APP_PROVIDER_AUTHORITIES = "content://com.pvr.pvrfit.DataReceiveProvider"

    fun onCreate(context: Context) {
        kotlin.runCatching {
            context.contentResolver.call(
                Uri.parse(APP_PROVIDER_AUTHORITIES),
                CALL_ON_CREATE, null, null
            )
        }
        GsonUtils.setGson("CtlDataContract", GsonUtils.getGson().newBuilder().serializeSpecialFloatingPointValues().create())
    }

    fun onDestroy(context: Context) {
        kotlin.runCatching {
            context.contentResolver.call(
                Uri.parse(APP_PROVIDER_AUTHORITIES),
                CALL_ON_DESTROY, null, null
            )
        }
    }

    fun onCollect(
        context: Context,
        majorTimeMills: Long,
        minorTimeMills: Long,
        hmdControllerData: String,
        swiftControllerData: String? = null,
    ) {
        kotlin.runCatching {
            val params =
                bundleOf(KEY_MINOR_TIME_MILLS to minorTimeMills,
                    KEY_MAJOR_TIME_MILLS to majorTimeMills,
                    KEY_HMD to hmdControllerData,
                    KEY_SWIFT to swiftControllerData)
            Log.i(TAG, "onCollect: $params ${Thread.currentThread()}")
            context.contentResolver.call(
                Uri.parse(APP_PROVIDER_AUTHORITIES),
                CALL_ON_COLLECT, null, params
            )
            Log.i(TAG, "onCollect call end: $params")
        }.getOrElse {
        }
    }

}