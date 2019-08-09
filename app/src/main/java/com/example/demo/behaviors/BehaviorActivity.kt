package com.example.demo.behaviors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_behavior.*
import kotlin.math.abs

class BehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior)
        erfl.setOnClickListener{
            erfl.isExpanded = !erfl.isExpanded
        }
        cardViewFrame.setOnClickListener{
//            erfl.isExpanded = false
        }
        var mCardViewPosY: Float = 0f
        dclFrame.setViewDragListener(object : DraggableCoordinatorLayout.ViewDragListener {
            override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {

            }

            override fun onViewCaptured(view: View, i: Int) {
                cardViewFrame.isDragged = true
                Log.d("joy", "initY=${view.y}")
                mCardViewPosY = view.y
            }

            override fun onViewReleased(view: View, v: Float, v1: Float) {
                Log.d("joy", "curY=${view.y}")
                if (abs(mCardViewPosY - view.y) > 100) {
                    erfl.isExpanded = false
                }
            }
        })
        dclFrame.addDraggableChild(erfl)
        dclFrame.addDraggableChild(cardViewFrame)
    }
}
