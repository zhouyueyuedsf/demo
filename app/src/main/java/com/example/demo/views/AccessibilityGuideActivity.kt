package com.example.demo.views

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_accessibility_guide.*

class AccessibilityGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_accessibility_guide)
        mask.setOnTouchListener { _, _ ->
            finish()
            true
        }
    }
}