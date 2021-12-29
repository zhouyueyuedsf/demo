package com.example.demo.views.importantview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R


class MyAdapter() : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var context: Context? = null
    lateinit var data: Array<String>

    constructor(context: Context?, data: Array<String>) : this() {
        this.context = context
        this.data = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.simple_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.layoutItem.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layoutItem: View = itemView.findViewById(R.id.layout_item)
    }
}