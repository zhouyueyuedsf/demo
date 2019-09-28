package com.example.demo.views.nextediitextcardview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ComponentActivity
import com.example.demo.R
import com.google.android.material.card.MaterialCardView
import com.example.demo.views.utils.ViewUtils.inflate
import com.google.android.material.textfield.TextInputEditText

/**
 * 需要在xml里面使用
 */
class NextEditTextCardView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    lateinit var mLastEditText: DeletedEditText
    val mCommonEditTexts: ArrayList<DeletedEditText> = arrayListOf()
    lateinit var mAdapter: Adapter

    init {
        // 初始化加载lastEditText
        mLastEditText = inflate(R.layout.layout_text_input, this)
//        mLastEditText.hint = "下一步"
//        mLastEditText.hideRightDrawable()
//        mLastEditText.clearFocus()
//        addView(mLastEditText)
//        mLastEditText.setOnEditorActionListener { textview, actionId, event ->
//            when(actionId) {
//                EditorInfo.IME_ACTION_UNSPECIFIED -> {
//                    // append一个DeletedEditText
//                    appendEditText(mLastEditText.text.toString())
//                    mLastEditText.setText("")
//                }
//            }
//            false
//        }
    }


    private fun appendEditText(data: String) {
        val commonEditText = inflate<DeletedEditText>(R.layout.layout_text_input, this)
        commonEditText.setText(data)
        commonEditText.clearFocus()
        commonEditText.setDeleteCallBack {
            mCommonEditTexts.remove(it)
        }
        mCommonEditTexts.add(commonEditText)
        addView(commonEditText, mCommonEditTexts.size - 1)
    }

    private fun removeEditText(editText: TextInputEditText){

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val childNum = childCount
//        var totalHeight = 0
//        var totalWidth = 0
//        for (i in (0 until childNum)) {
//            val child = getChildAt(i)
//            measureChild(child, widthMeasureSpec, heightMeasureSpec)
//            totalHeight += child.measuredHeight
//            totalWidth = child.measuredWidth
//        }
//        setMeasuredDimension(totalWidth, totalHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
//        val childNum = childCount
//        var top = 0
//        val right = measuredWidth
//        for (i in (0 until childNum)) {
//            val child = getChildAt(i)
//            child.layout(0, top, right, top + child.measuredHeight)
//            top += child.measuredHeight
//        }
    }

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//    }

    fun setAdapter(adapter: Adapter){
        mAdapter = adapter
        // 加载带有数据的commonEditTexts
        val datas = mAdapter.getData()
        for (i in datas.indices){
            appendEditText(datas[i])
        }
//        requestLayout()
    }

    abstract class Adapter{
        abstract fun getData(): List<String>
    }
}