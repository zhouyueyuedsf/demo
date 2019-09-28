package com.example.demo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.HorizontalScrollView
import com.example.demo.R
import com.example.demo.views.nextediitextcardview.NextEditTextCardView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        fl_test.postDelayed({
            for (i in 0..10) {
                (LayoutInflater.from(this@ViewActivity).inflate(R.layout.stub_chip, fl_test, false) as Chip).let {
                    it.text = "test$i"
                    fl_test.addView(it)

//                    it.setOnCheckedChangeListener { buttonView, isChecked ->
//                        val j = 0
//                        if (isChecked) {
//                            if (fl_test.parent is HorizontalScrollView) {
//                                it.postDelayed({
//                                    (fl_test.parent as HorizontalScrollView).smoothScrollTo(buttonView.left, buttonView.top)
//                                }, 1000)
//                            }
//                        }
//                    }
                    if (i == 0) {
                        it.isChecked = true
                    }
                }
            }
        }, 300)


//        val adapter = object
//        necv_test.setAdapter(object : NextEditTextCardView.Adapter() {
//            override fun getDatas(): List<String> {
//                return arrayListOf("123","234")
//            }
//        })
    }
}
