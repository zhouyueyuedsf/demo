package com.example.demo.views.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_toolbar.*
import com.example.demo.utils.dp

class ToolbarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        toolbar.setAdapter(object : EventToolBar.EventDetailToolBarAdapter {
            override fun getScrolledView(): ViewGroup {
                return scroll_view
            }

            override fun getBindedTitle(): TextView {
                return tv_big_title
            }

            override fun onPercentUpdate(percent: Float, tvTitle: TextView, buttonSure: Button) {
                tvTitle.alpha = percent

                val guidePercent = (48.dp - (48.dp - buttonSure.height) / 2f) / 48.dp
                if (percent >= guidePercent) {
                    buttonSure.visibility = View.VISIBLE
                    scrollViewButton.visibility = View.GONE
                    toolbar.addIntoLast()
                } else {
                    buttonSure.visibility = View.INVISIBLE
                    scrollViewButton.visibility = View.VISIBLE
                    if (percent <= 0) {
                        toolbar.addIntoFirst()
                    }
                }


            }
        })
    }

    fun View.addIntoLast() {
        (parent as ViewGroup).apply {
            val index = childCount - 1
            removeView(this@addIntoLast)
            addView(this@addIntoLast, index)
        }
    }

    fun View.addIntoFirst() {
        (parent as ViewGroup).apply {
            removeView(this@addIntoFirst)
            addView(this@addIntoFirst, 0)
        }
    }

}