package com.example.demo.views

import android.content.Context
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.CharacterStyle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.example.demo.R


import java.util.ArrayList
import java.util.Collections
import kotlin.math.max

class EllipsizeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : TextView(context, attrs) {

    private var mEllipsizeText: CharSequence? = null
    private var mOriginText: CharSequence? = null

    private var mEllipsizeIndex: Int = 0
    private var mMaxLines: Int = 0

    private var mIsExactlyMode: Boolean = false
    private var mEnableUpdateOriginText = true

    companion object {
        private const val DEFAULT_ELLIPSIZE_TEXT = "..."
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.EllipsizeTextView)
        mEllipsizeIndex = ta.getInt(R.styleable.EllipsizeTextView_ellipsize_index, 0)
        mEllipsizeText = ta.getText(R.styleable.EllipsizeTextView_ellipsize_text)
        mOriginText = text
        if (mEllipsizeText == null) {
            mEllipsizeText = DEFAULT_ELLIPSIZE_TEXT
        }
        ta.recycle()
    }


    override fun setMaxLines(maxLines: Int) {
        if (mMaxLines != maxLines) {
            super.setMaxLines(maxLines)
            this.mMaxLines = maxLines
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        text = mOriginText
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        try {
            mIsExactlyMode = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
            val layout = layout
            if (layout != null) {
                if (isExceedMaxLine(layout) || isOutOfBounds(layout)) {
                    adjustEllipsizeEndText(layout)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        if (mEnableUpdateOriginText) {
            mOriginText = text
        }

        super.setText(text, type)

        if (mIsExactlyMode) {
            requestLayout()
        }
    }

    private fun isExceedMaxLine(layout: Layout): Boolean {
        return layout.lineCount > mMaxLines && mMaxLines > 0
    }

    private fun isOutOfBounds(layout: Layout): Boolean {
        return layout.height > measuredHeight - paddingBottom - paddingTop
    }

    private fun adjustEllipsizeEndText(layout: Layout) {
        val originText = mOriginText
        val restSuffixText = originText!!.subSequence(
                originText.length - mEllipsizeIndex, originText.length)

        val width = layout.width - paddingLeft - paddingRight
        val maxLineCount = max(1, computeMaxLineCount(layout))
        val lastLineWidth = layout.getLineWidth(maxLineCount - 1).toInt()
        val mLastCharacterIndex = layout.getLineEnd(maxLineCount - 1)

        val suffixWidth = (Layout.getDesiredWidth(mEllipsizeText, paint) + Layout.getDesiredWidth(restSuffixText, paint)).toInt() + 1

        mEnableUpdateOriginText = false
        if (lastLineWidth + suffixWidth > width) {
            val widthDiff = lastLineWidth + suffixWidth - width

            val removedCharacterCount = computeRemovedEllipsizeEndCharacterCount(widthDiff,
                    originText.subSequence(0, mLastCharacterIndex))

            text = originText.subSequence(0, mLastCharacterIndex - removedCharacterCount)
            append(mEllipsizeText)
            append(restSuffixText)
        } else {
            text = originText.subSequence(0, mLastCharacterIndex)
            append(mEllipsizeText)
            append(restSuffixText)
        }

        mEnableUpdateOriginText = true
    }

    private fun computeMaxLineCount(layout: Layout): Int {
        val availableHeight = measuredHeight - paddingTop - paddingBottom
        for (i in 0 until layout.lineCount) {
            if (availableHeight < layout.getLineBottom(i)) {
                return i
            }
        }

        return layout.lineCount
    }

    private fun computeRemovedEllipsizeEndCharacterCount(widthDiff: Int, text: CharSequence): Int {
        if (TextUtils.isEmpty(text)) {
            return 0
        }

        val characterStyleRanges = computeCharacterStyleRanges(text)
        val textStr = text.toString()

        // prevent the subString from containing messy code when the given string contains emotion
        var characterIndex = text.length
        var codePointIndex = textStr.codePointCount(0, text.length)
        var currentRemovedWidth = 0

        while (codePointIndex > 0 && widthDiff > currentRemovedWidth) {
            codePointIndex--
            characterIndex = textStr.offsetByCodePoints(0, codePointIndex)

            // prevent the subString from containing messy code when the given string contains CharacterStyle
            val characterStyleRange = computeCharacterStyleRange(characterStyleRanges, characterIndex)
            if (characterStyleRange != null) {
                characterIndex = characterStyleRange.lower
                codePointIndex = textStr.codePointCount(0, characterIndex)
            }

            currentRemovedWidth = Layout.getDesiredWidth(
                    text.subSequence(characterIndex, text.length),
                    paint).toInt()
        }

        return text.length - textStr.offsetByCodePoints(0, codePointIndex)
    }

    private fun computeCharacterStyleRange(characterStyleRanges: List<Range<Int>>?, index: Int): Range<Int>? {
        if (characterStyleRanges == null || characterStyleRanges.isEmpty()) {
            return null
        }

        for (characterStyleRange in characterStyleRanges) {
            if (characterStyleRange.contains(index)) {
                return characterStyleRange
            }
        }

        return null
    }

    private fun computeCharacterStyleRanges(text: CharSequence): List<Range<Int>> {
        val ssb = SpannableStringBuilder.valueOf(text)
        val characterStyles = ssb.getSpans(0, ssb.length, CharacterStyle::class.java)

        if (characterStyles == null || characterStyles.isEmpty()) {
            return Collections.emptyList()
        }

        val ranges = ArrayList<Range<Int>>()
        for (characterStyle in characterStyles) {
            ranges.add(Range(ssb.getSpanStart(characterStyle), ssb.getSpanEnd(characterStyle)))
        }

        return ranges
    }

    /**
     * @param ellipsizeText  causes words in the text that are longer than the view is wide
     * to be ellipsized by used the text instead of broken in the middle.
     * @param ellipsizeIndex the index of the ellipsizeText will be inserted in the reverse order.
     */
    fun setEllipsizeText(ellipsizeText: CharSequence, ellipsizeIndex: Int) {
        this.mEllipsizeText = ellipsizeText
        this.mEllipsizeIndex = ellipsizeIndex
    }

    class Range<T : Comparable<T>>(val lower: T, val upper: T) {

        init {

            require(lower <= upper) { "lower must be less than or equal to upper" }
        }

        operator fun contains(value: T): Boolean {

            val gteLower = value >= lower
            val lteUpper = value < upper

            return gteLower && lteUpper
        }
    }


}