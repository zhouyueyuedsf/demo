package com.example.demo.views.material

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

@CoordinatorLayout.DefaultBehavior(MyBehavior::class)
class MyAppbarLayout(context: Context, attributeSet: AttributeSet) : AppBarLayout(context, attributeSet) {

}