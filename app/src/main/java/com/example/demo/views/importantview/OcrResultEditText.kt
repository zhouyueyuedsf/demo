package com.example.demo.views.importantview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import androidx.appcompat.widget.AppCompatEditText
import java.lang.StringBuilder

class OcrResultEditText(context: Context, attributeSet: AttributeSet) : AppCompatEditText(context, attributeSet) {
    private var ocrInputConnection: InputConnection? = null
    override fun onEditorAction(actionCode: Int) {
        Log.d("InputConnectionFix", "onEditorAction: $actionCode")
        super.onEditorAction(actionCode)
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return OcrInputConnection(super.onCreateInputConnection(outAttrs), true).also { ocrInputConnection = it }
    }

//    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
//        super.onTextChanged(text, start, lengthBefore, lengthAfter)
//        var end = selectionStart
//        while (end > 0 && text?.get(--end) == '\n') {
//            ocrInputConnection?.apply {
//                sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
//            }
//        }
//    }


    inner class OcrInputConnection(target: InputConnection, mutable: Boolean) : InputConnectionWrapper(target, mutable) {

        override fun commitText(text: CharSequence?, newCursorPosition: Int): Boolean {
            val applyText = StringBuilder(text ?: "")
            Log.d("InputConnectionFix", "newCursorPosition: $newCursorPosition")
            text?.let {
                if (it == "\n") {
                    applyText.append(it)
                }
            }
            return super.commitText(applyText, newCursorPosition)
        }

        override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
            Log.d("InputConnectionFix", "selectionStart: $selectionStart selectionEnd: $selectionEnd" )
            if (beforeLength == 1 && afterLength == 0) {
                val text = getTextBeforeCursor(selectionStart, 0)
                var end = text.length - 1
                while (end > 0 && text[--end] == '\n') {
                    sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
                }
                return sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
            }
            return super.deleteSurroundingText(beforeLength, afterLength)
        }

        override fun sendKeyEvent(event: KeyEvent?): Boolean {
            Log.d("InputConnectionFix", "sendKeyEvent ${event?.keyCode}")
            return super.sendKeyEvent(event)
        }
    }
}