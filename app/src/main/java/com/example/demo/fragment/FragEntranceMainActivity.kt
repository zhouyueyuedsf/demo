package com.example.demo.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.example.demo.R

val TAG = "FragEntranceAct"
class FragEntranceMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag_entrance_main)
        val fragmentManager = supportFragmentManager
        Log.i(TAG, "onCreate: act = $this fragManager = $fragmentManager")
        if (fragmentManager.findFragmentByTag("test1") == null) {
            val tf = Test1Fragment.newInstance("1", "2")
            fragmentManager.beginTransaction().replace(R.id.frag_entrance_main_container, tf).commit()
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }
}