package com.example.demo.sourcecodelab

import android.text.Layout
import android.text.TextPaint

class MyLayout(text: CharSequence, paint: TextPaint, width: Int, align: Alignment, spacingMult: Float, spacingAdd: Float)
    : Layout(text, paint, width, align, spacingMult, spacingAdd) {

    override fun getBottomPadding(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLineDirections(line: Int): Directions {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEllipsisCount(line: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLineDescent(line: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getParagraphDirection(line: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLineCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLineContainsTab(line: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLineTop(line: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEllipsisStart(line: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLineStart(line: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTopPadding(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}