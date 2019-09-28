package com.example.demo.viewpagers

import android.view.View
import androidx.viewpager.widget.PagerAdapter




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_viewpager.*

class ViewpagerActivity : AppCompatActivity() {

    val datas = arrayListOf<String>("1", "2" ,"3")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        val textviews = arrayListOf<TextView>()
        for (data in datas) {
            val tv = TextView(this)
            tv.text = data
            textviews.add(tv)
        }
        viewpager_main.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun getCount(): Int {
                return datas.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val tv = textviews[position]
                container.addView(tv)
                return tv
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as TextView)
            }
        }
        viewpager_main.post {
            viewpager_main.currentItem = 1
        }

    }
}
