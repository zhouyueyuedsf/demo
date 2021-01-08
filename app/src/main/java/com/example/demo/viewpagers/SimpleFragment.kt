package com.example.demo.viewpagers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.fragment.app.Fragment
import com.example.demo.R
import kotlinx.android.synthetic.main.content_scrolling.*

class SimpleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
        View = inflater.inflate(R.layout.content_scrolling, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.postDelayed({
            Log.d("yyyyyy", "SimpleFragment context = $context")
        }, 3000)
    }
}