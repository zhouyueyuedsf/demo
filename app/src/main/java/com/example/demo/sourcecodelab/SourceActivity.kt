package com.example.demo.sourcecodelab
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_source.*

class SourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)
        tv_lab.setText("112312412342341234123414123412414123112312412342341234123414123412414123", TextView.BufferType.SPANNABLE)
    }
}
