package com.example.demo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_source.*
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
//        tv_with_ell.maxLines = 1
//        tv_with_ell.setText("great / satisfying / positive / acceptable /sa. valueable / bad /", TextView.BufferType.SPANNABLE)
    }
}
