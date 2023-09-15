package com.example.demo.broadcast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_broadcast.btn_send_broadcast

class BroadcastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)
        btn_send_broadcast.setOnClickListener {
            Log.i("yyyyyyyyy", "send cast")
            sendBroadcast(Intent().apply {
                `package` = "com.example.demo"
                action = "my_action"
            })
        }
    }
}