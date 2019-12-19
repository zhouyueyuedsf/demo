package com.example.demo.advanced.mmkv

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.example.demo.utils.MMKVUtil
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.mmkv_activity.*

/**
 * create by yueyue_projects on 2019/11/25
 */

class MMKVActivity : AppCompatActivity() {
    lateinit var mKv: MMKV
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mmkv_activity)
        val start = System.currentTimeMillis()
        MMKVUtil.init(this)
        val end = System.currentTimeMillis()
        Log.d("spend time", "${(end - start)}")
        mKv = MMKV.mmkvWithID("InterProcessKV", MMKV.MULTI_PROCESS_MODE)
        buttonStartProcess.setOnClickListener {
            //            routerTo(ProcessActivity::class.java)
        }
        buttonWrite.setOnClickListener {
            mKv.encode("MMKV_PUT_STRING", etWrite.text.toString())
        }

//        buttonRead.setOnClickListener {
//            startService(Intent(this@MMKVActivity, MMKVServer::class.java))
//        }
    }
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    private fun routerTo(clazz: Class<out Any>) {
        startActivity(Intent(this@MMKVActivity, clazz))
    }
}