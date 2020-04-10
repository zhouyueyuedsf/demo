package com.example.demo.services

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.MyApplication
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        buttonStart.setOnClickListener {
            val intent = Intent(MyApplication.getInstance(), MyService::class.java)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(intent)
//            } else {
                startService(intent)
//            }
        }
    }
}