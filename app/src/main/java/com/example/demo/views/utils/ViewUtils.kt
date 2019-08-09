package com.example.demo.views.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

object ViewUtils {
    @Suppress("UNCHECKED_CAST")
        internal fun <T> ViewGroup.inflate(
        @LayoutRes res: Int,
        root: ViewGroup? = this
    ) = LayoutInflater.from(context).inflate(res, root, false) as T

}