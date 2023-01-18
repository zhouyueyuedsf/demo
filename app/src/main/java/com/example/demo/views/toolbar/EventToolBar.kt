package com.example.demo.views.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.demo.R
import android.widget.TextView

class EventToolBar(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private var tvTitle: TextView
    private var buttonSure: Button
    init {
        val toolbar = LayoutInflater.from(context).inflate(R.layout.event_toolbar, this, false)
        addView(toolbar)
        tvTitle = findViewById(R.id.tv_title)
        buttonSure = findViewById(R.id.button_sure)
    }

    fun setAdapter(toolBarAdapter: EventDetailToolBarAdapter) {
        val scrolledView = toolBarAdapter.getScrolledView()
        val externTvTitle = toolBarAdapter.getBindedTitle()
        bindTitle(externTvTitle)
        val externGlobalLoc = IntArray(2)
        val internGlobalLoc = IntArray(2)
        scrolledView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            externTvTitle.getLocationInWindow(externGlobalLoc)
            this.getLocationInWindow(internGlobalLoc)
            val guideline = internGlobalLoc[1] + height
            val percent = ((guideline - externGlobalLoc[1]) * 1f / height).coerceIn(0f, 1f)
            toolBarAdapter.onPercentUpdate(percent, tvTitle, buttonSure)
        }
    }


    private fun bindTitle(externTvTitle: TextView) {
        tvTitle.text = externTvTitle.text
        tvTitle.textSize = externTvTitle.textSize
    }

    interface EventDetailToolBarAdapter {
        fun getScrolledView(): ViewGroup
        fun getBindedTitle(): TextView
        fun onPercentUpdate(percent: Float, tvTitle: TextView, buttonSure: Button)
    }
}