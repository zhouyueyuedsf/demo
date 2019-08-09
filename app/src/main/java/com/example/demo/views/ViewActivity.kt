package com.example.demo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.R
import com.example.demo.views.nextediitextcardview.NextEditTextCardView
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

//        val adapter = object
        necv_test.setAdapter(object : NextEditTextCardView.Adapter() {
            override fun getData(): List<String> {
                return arrayListOf("123","234")
            }
        })
    }
}
