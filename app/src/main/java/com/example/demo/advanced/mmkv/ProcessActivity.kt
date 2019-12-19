package com.example.demo.advanced.mmkv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_process.*

class ProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)
        buttonRead.setOnClickListener {
            val value = MMKV.defaultMMKV().encode("MMKV_PUT_STRING", "123")
//            etRead.setText(value)
        }
    }
}
