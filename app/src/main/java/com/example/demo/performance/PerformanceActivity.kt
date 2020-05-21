package com.example.demo.performance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_performance.*
import kotlinx.android.synthetic.main.activity_scrolling.*

class PerformanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
        setSupportActionBar(toolbar2)
        rv_root.layoutManager = LinearLayoutManager(this)
        rv_root.adapter = object : RecyclerView.Adapter<MyViewHolder>() {
            val strings = Array(10) {
                "$it"
            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val v = LayoutInflater.from(this@PerformanceActivity).inflate(R.layout.item_card_performance, parent, false)
                return MyViewHolder(v)
            }

            override fun getItemCount(): Int {
                return strings.size
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                holder.textView.text = strings[position]
                holder.recyclerView.adapter = object : RecyclerView.Adapter<SubMyViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubMyViewHolder {
                        val v = LayoutInflater.from(this@PerformanceActivity).inflate(R.layout.item_performance, parent, false)
                        return SubMyViewHolder(v)
                    }

                    override fun getItemCount(): Int {
                        return strings.size
                    }

                    override fun onBindViewHolder(holder: SubMyViewHolder, position: Int) {
                        holder.textView.text = strings[position]
                    }

                }
            }

        }
    }
}

class SubMyViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var textView: TextView = view.findViewById(R.id.tv_test)
}
class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var textView : TextView = view.findViewById<TextView>(R.id.tv_test)
    var recyclerView: RecyclerView = view.findViewById(R.id.rv_card)
    init {

    }
}