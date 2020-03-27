package com.example.demo.utils

import android.content.Context
import android.preference.PreferenceManager
import com.example.demo.MyApplication


object PreferenceUtils {
    private var CONTEXT = MyApplication.getInstance()

    //====================int====================
    @Synchronized
    fun getInt(key: String, defValue: Int): Int {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT)
            .getInt(key, defValue)
    }

    @Synchronized
    fun putInt(key: String, value: Int) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
            .putInt(key, value).apply()
    }

    @Synchronized
    fun putIntSync(key: String, value: Int) {
        val sp = CONTEXT.getSharedPreferences("lock_screen", Context.MODE_MULTI_PROCESS)
        sp.edit().putInt(key, value).commit()
    }

    @Synchronized
    fun getIntSync(key: String, defValue: Int): Int {
        val sp = CONTEXT.getSharedPreferences("lock_screen", Context.MODE_MULTI_PROCESS)
        return sp.getInt(key, defValue)
    }

    @Synchronized
    fun putStringSync(key: String, value: String) {
        val sp = CONTEXT.getSharedPreferences("lock_screen", Context.MODE_MULTI_PROCESS)
        sp.edit().putString(key, value).commit()
    }

    @Synchronized
    fun getStringSync(key: String, defValue: String): String? {
        val sp = CONTEXT.getSharedPreferences("lock_screen", Context.MODE_MULTI_PROCESS)
        return sp.getString(key, defValue)
    }

    //====================float====================
    @Synchronized
    fun getFloat(key: String, defValue: Float): Float {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT)
            .getFloat(key, defValue)
    }

    @Synchronized
    fun putFloat(key: String, value: Float) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
            .putFloat(key, value).apply()
    }

    //====================String====================
    @Synchronized
    fun getString(key: String, defValue: String): String? {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT)
            .getString(key, defValue)
    }

    @Synchronized
    fun putString(key: String, value: String) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
            .putString(key, value).apply()
    }

    //====================long====================
    @Synchronized
    fun getLong(key: String, defValue: Long): Long {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT)
            .getLong(key, defValue)
    }

    @Synchronized
    fun putLong(key: String, value: Long) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
            .putLong(key, value).apply()
    }

    //====================boolean====================
    @Synchronized
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(CONTEXT)
            .getBoolean(key, defValue)
    }

    @Synchronized
    fun putBoolean(key: String, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT).edit()
            .putBoolean(key, value).apply()
    }

    //====================remove====================
    @Synchronized
    fun remove(key: String) {
        PreferenceManager.getDefaultSharedPreferences(CONTEXT)
            .edit().remove(key).apply()
    }

    @Synchronized
    fun contains(key: String): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(CONTEXT).contains(key)
    }


    @Synchronized
    fun getClipString(key: String, defaultValue: String): String? {
        val sp = CONTEXT.getSharedPreferences("com.youdao.hindict.pref_dict", Context.MODE_PRIVATE)
        return sp.getString(key, defaultValue)
    }


    @Synchronized
    fun getClipBoolean(key: String, defaultValue: Boolean): Boolean {
        val sp = CONTEXT.getSharedPreferences("com.youdao.hindict.pref_dict", Context.MODE_PRIVATE)
        val value = sp.getString(key, defaultValue.toString())
        return java.lang.Boolean.parseBoolean(value)
    }

    @Synchronized
    fun getClipInt(key: String, defaultValue: Int): Int {
        val sp = CONTEXT.getSharedPreferences("com.youdao.hindict.pref_dict", Context.MODE_PRIVATE)
        val value = sp.getString(key, defaultValue.toString())
        return Integer.parseInt(value!!)
    }

    @Synchronized
    fun containsClip(key: String): Boolean {
        val sp = CONTEXT.getSharedPreferences("com.youdao.hindict.pref_dict", Context.MODE_PRIVATE)
        return sp.contains(key)
    }


}
