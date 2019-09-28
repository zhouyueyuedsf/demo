package com.example.demo.views.flowlayout


import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.demo.R

/**
 * Horizontally lay out children until the row is filled and then moved to the next line. Call
 * [MyFlowLayout.setSingleLine] to disable reflow and lay all children out in one line.
 *
 */
open class MyFlowLayout : ViewGroup {
    protected var lineSpacing: Int = 0
    protected var itemSpacing: Int = 0
    /** Returns whether this chip group is single line or reflowed multiline.  */
    /** Sets whether this chip group is single line, or reflowed multiline.  */
    open var isSingleLine: Boolean = false

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        isSingleLine = false
        loadFromAttributes(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        isSingleLine = false
        loadFromAttributes(context, attrs)
    }

    private fun loadFromAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.MyFlowLayout, 0, 0)
        lineSpacing = array.getDimensionPixelSize(R.styleable.MyFlowLayout_line_spacing, 0)
        itemSpacing = array.getDimensionPixelSize(R.styleable.MyFlowLayout_item_spacing, 0)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)

        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val maxWidth = if (widthMode == View.MeasureSpec.AT_MOST || widthMode == View.MeasureSpec.EXACTLY)
            width
        else
            Integer.MAX_VALUE

        var childLeft = paddingLeft
        var childTop = paddingTop
        var childBottom = childTop
        var childRight = childLeft
        var maxChildRight = 0
        val maxRight = maxWidth - paddingRight
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.visibility == View.GONE) {
                continue
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val lp = child.layoutParams
            var leftMargin = 0
            var rightMargin = 0
            if (lp is ViewGroup.MarginLayoutParams) {
                leftMargin += lp.leftMargin
                rightMargin += lp.rightMargin
            }

            childRight = childLeft + leftMargin + child.measuredWidth

            // If the current child's right bound exceeds Flowlayout's max right bound and flowlayout is
            // not confined to a single line, move this child to the next line and reset its left bound to
            // flowlayout's left bound.
            if (childRight > maxRight && !isSingleLine) {
                childLeft = paddingLeft
                childTop = childBottom + lineSpacing
            }

            childRight = childLeft + leftMargin + child.measuredWidth
            childBottom = childTop + child.measuredHeight

            // Updates Flowlayout's max right bound if current child's right bound exceeds it.
            if (childRight > maxChildRight) {
                maxChildRight = childRight
            }

            childLeft += leftMargin + rightMargin + child.measuredWidth + itemSpacing

            // For all preceding children, the child's right margin is taken into account in the next
            // child's left bound (childLeft). However, childLeft is ignored after the last child so the
            // last child's right margin needs to be explicitly added to Flowlayout's max right bound.
            if (i == childCount - 1) {
                maxChildRight += rightMargin
            }
        }

        maxChildRight += paddingRight
        childBottom += paddingBottom

        val finalWidth = getMeasuredDimension(width, widthMode, maxChildRight)
        val finalHeight = getMeasuredDimension(height, heightMode, childBottom)
        setMeasuredDimension(finalWidth, finalHeight)
    }

    private fun getMeasuredDimension(size: Int, mode: Int, childrenEdge: Int): Int {
        when (mode) {
            View.MeasureSpec.EXACTLY -> return size
            View.MeasureSpec.AT_MOST -> return Math.min(childrenEdge, size)
            else // UNSPECIFIED:
            -> return childrenEdge
        }
    }

    override fun onLayout(sizeChanged: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (childCount == 0) {
            // Do not re-layout when there are no children.
            return
        }

        val isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
        val paddingStart = if (isRtl) paddingRight else paddingLeft
        val paddingEnd = if (isRtl) paddingLeft else paddingRight
        var childStart = paddingStart
        var childTop = paddingTop
        var childBottom = childTop
        var childEnd: Int

        val maxChildEnd = right - left - paddingEnd

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.visibility == View.GONE) {
                continue
            }

            val lp = child.layoutParams
            var startMargin = 0
            var endMargin = 0
            if (lp is ViewGroup.MarginLayoutParams) {
                startMargin = MarginLayoutParamsCompat.getMarginStart(lp)
                endMargin = MarginLayoutParamsCompat.getMarginEnd(lp)
            }

            childEnd = childStart + startMargin + child.measuredWidth

            if (!isSingleLine && childEnd > maxChildEnd) {
                childStart = paddingStart
                childTop = childBottom + lineSpacing
            }

            childEnd = childStart + startMargin + child.measuredWidth
            childBottom = childTop + child.measuredHeight

            if (isRtl) {
                child.layout(
                    maxChildEnd - childEnd, childTop, maxChildEnd - childStart - startMargin, childBottom
                )
            } else {
                child.layout(childStart + startMargin, childTop, childEnd, childBottom)
            }

            childStart += startMargin + endMargin + child.measuredWidth + itemSpacing
        }
    }
}
