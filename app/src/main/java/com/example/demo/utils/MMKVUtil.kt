package com.example.demo.utils

import android.content.Context
import com.getkeepsafe.relinker.ReLinker
import com.tencent.mmkv.MMKV

/**
 * create by yueyue_projects on 2019/11/26
 */

object MMKVUtil {
    //    const val Ashmem_SHARE_FILE_NAME = "com.youdao.hindict.pref_clip"
    const val MMAP_ID = "com.youdao.hindict.pref_clip"

    fun putString(key: String, value: String?) {
        value?.let {
            MMKV.mmkvWithID(MMAP_ID, MMKV.MULTI_PROCESS_MODE).encode(key, it)
        }
    }

    fun getString(key: String, default: String): String {
        return MMKV.mmkvWithID(MMAP_ID, MMKV.MULTI_PROCESS_MODE).decodeString(key)
    }

    fun containKey(key: String): Boolean {
        return MMKV.mmkvWithID(MMAP_ID, MMKV.MULTI_PROCESS_MODE).containsKey(key)
    }

    /**
     * 一些 Android 设备（API level 19）在安装/更新 APK 时可能出错, 导致 libmmkv.so 找不到, 故用ReLinker加载
     */
    @JvmStatic
    fun init(context: Context) {
        val dir = "${context.filesDir.absoluteFile}/mmkv"
        MMKV.initialize(dir) {
            ReLinker.loadLibrary(context, it)
        }
    }
}