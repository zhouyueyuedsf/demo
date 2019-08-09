package com.example.demo.views.nextediitextcardview

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.demo.R
import com.google.android.material.textfield.TextInputEditText

typealias DeleteCallBack = (DeletedEditText) -> Unit
class DeletedEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {
    private val mRightDrawable: Drawable = context.resources.getDrawable(R.drawable.ic_clear_black_24dp)
    private lateinit var mDeleteCallBack: DeleteCallBack

    init {
//        compoundDrawables
        setCompoundDrawablesWithIntrinsicBounds(null, null, mRightDrawable, null)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setOnTouchListener { view, ev ->
            // 得到手点击的坐标
            val pointerX = ev?.x?.toInt()
            val pointerY = ev?.y?.toInt()
            // 得到drawable所在的坐标

            val mRightDrawableX = right - (mRightDrawable.intrinsicWidth * 2)
            val mRightDrawableY = bottom - (mRightDrawable.intrinsicHeight * 2)
            // 检测是否在drawable的范围内
            if(pointerX in (mRightDrawableX .. right) && pointerY in (mRightDrawableY .. bottom)) {
                if (view.parent is View) {
                    // 直接移除view
                    (view.parent as ViewGroup).removeView(view)
                    mDeleteCallBack.invoke(this@DeletedEditText)
                }
            }
            false
        }
    }

    fun hideRightDrawable(){
        mRightDrawable.setVisible(false, true)
    }

    fun setDeleteCallBack(callback: DeleteCallBack){
        mDeleteCallBack = callback
    }

}