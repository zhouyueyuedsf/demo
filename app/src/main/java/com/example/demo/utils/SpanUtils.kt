package com.example.demo.utils

import android.text.Editable
import android.text.Html
import android.text.Html.TagHandler
import android.text.Spanned
import android.text.style.ImageSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demo.R
import org.xml.sax.XMLReader

/**
 * Create by Administrator on 2020/4/3
 */
fun setParagraphSpacing(textView: TextView, html: String, paragraphHeight: Int): Unit { // br段落换行符，使用<pr><br></pr>表示一个单独的换行
    val finalHtml = html.replace("<br>", "<pr><br></pr>")
    val paragraphHeightDrawable = ContextCompat.getDrawable(textView.context, R.drawable.shape_transparent)
        ?: return
    paragraphHeightDrawable.setBounds(0, 0, 100, paragraphHeight)
    textView.text = Html.fromHtml(finalHtml, null, object : TagHandler {
        private var start = 0
        private var end = 0
        override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {
            if (tag.equals("pr", ignoreCase = true)) {
                if (opening) start = output.length else {
                    end = output.length
                    output.setSpan(ImageSpan(paragraphHeightDrawable), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
    })
}

