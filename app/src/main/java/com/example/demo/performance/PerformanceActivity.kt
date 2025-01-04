package com.example.demo.performance

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Trace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.ss.android.ugc.aweme.im.sdk.chat.preload.FramePerfCollector
import com.ss.android.ugc.aweme.im.sdk.chat.preload.GapScheduler
import com.ss.android.ugc.aweme.im.sdk.chat.preload.ITask
import kotlinx.android.synthetic.main.activity_performance.*
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PerformanceActivity : AppCompatActivity() {
    companion object {
        const val TAG = "PerformanceActivity"
    }
    private var gapSchedulerModel = true
    private var oncreateTs = 0L
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: cost = ${System.currentTimeMillis() - oncreateTs} ")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        oncreateTs = System.currentTimeMillis()
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
        setSupportActionBar(toolbar2)

        rv_root.layoutManager = LinearLayoutManager(this)
        (rv_root.layoutManager as LinearLayoutManager).isItemPrefetchEnabled = false
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
        val gapScheduler = GapScheduler(rv_root)
        rv_root.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("yyyyyyyyyyy", "onScrolled: thread active num ${Thread.activeCount()}")
                if (gapSchedulerModel) {
                    gapScheduler.schedule(listOf(object : ITask {
                        override fun call() {
                            mockSlowFun()
                        }

                        override fun name(): String {
                            return "pt"
                        }
                    }))
                } else {
                    GlobalScope.launch {
                        mockSlowFun()
                    }
                }
            }
        })
        change_model.setOnClickListener {
            gapSchedulerModel = !gapSchedulerModel
            Log.d(FramePerfCollector.TAG, "gapSchedulerModel = $gapSchedulerModel")
        }
        start_scroll.setOnClickListener {
            FramePerfCollector.start()

            MainScope().launch {
                var dir = true
                while (true) {
                    (1..100).forEach {
                        rv_root.smoothScrollBy(0, if (dir) 100 else -100)
                        delay(100L)
                    }
                    dir = !dir
                    FramePerfCollector.stop()
                }
            }

        }
    }
}

fun mockSlowFun() {
//    val randomTimeMs = (5L..10L).random()
    Thread.sleep(4L)
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