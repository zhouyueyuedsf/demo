package com.example.demo.views.importantview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_important_view.*

class ImportantViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_important_view)
        val data = Array<String>(60) {
            "item $it"
        }
        rvMain.layoutManager = MyLinearLayoutManager(this)
        rvMain.adapter = MyAdapter(this, data)
    }
}
