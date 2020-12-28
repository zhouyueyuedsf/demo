package com.example.demo.viewpagers

import android.view.View
import androidx.viewpager.widget.PagerAdapter




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.R
import com.google.android.material.tabs.TabLayoutMediator
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

        viewpagerMain.post {
            viewpagerMain.currentItem = 1
        }
        viewpagerMain.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return datas.size
            }

            override fun createFragment(position: Int): Fragment = SimpleFragment()
        }
        TabLayoutMediator(tabs, viewpagerMain) { tab, position ->
            tab.text = datas[position]
        }.attach()
    }
}
