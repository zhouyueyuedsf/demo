package com.example.demo.utils

import android.content.res.Resources
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/*
 * Created by yaodh on 2019/10/31.
 */
/**
 * dp转成px
 * 在代码中直接调用8.dp这种就可以使用dp
 */
val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()

