package com.example.demo.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_event.fl1

class EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportFragmentManager.beginTransaction()
            .add(R.id.fl1, Event1Fragment.newInstance("1", "2"), "frag1").commitAllowingStateLoss()

    }

    companion object {
    }
}