package com.example.demo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
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

        val text = "122222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222333333333333333333333333" +
                "444444444444444444444444444444444444444444444445555555555555555555555555555"

        tv_test.post {
            tv_test.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            tv_test.setLines(1)
            tv_test.maxLines = 5
            tv_test.setText(text)
        }

        button1.setOnClickListener {
            tv_test.setLines(1)
//            tv_test.clearFocus()
//            tv_test.isEnabled = false
//            tv_test.setText("")
//            tv_test.setSelection(text.length)
//            tv_test.height = tv_test.measuredHeight
        }

    }
}
