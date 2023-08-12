package com.example.demo.binder.sever

import android.database.MatrixCursor
import android.os.Bundle

class BinderCursor private constructor() : MatrixCursor(arrayOf("service")) {
    companion object {
        private const val KEY_BINDER_COUNT = "key_binder_count"
        val binderCursor = BinderCursor()
    }

    private val mBinderExtra = Bundle()

    init {
        mBinderExtra.putBinder(KEY_BINDER_COUNT, CountServiceImpl())
    }

    override fun getExtras(): Bundle {
        return mBinderExtra
    }
}