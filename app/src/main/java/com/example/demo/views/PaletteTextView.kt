package com.example.demo.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.toRectF
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.utils.Util
import com.example.demo.utils.dp
import com.youdao.hindict.drawable.RoundBackgroundDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @data 2020/7/2
 * @author joy zhou
 * 动态调色的Textview
 */
class PaletteTextView(context: Context, attributeSet: AttributeSet) : AppCompatTextView(context, attributeSet) {
    private var adaptColor = Color.WHITE
    private val TEN_PX = 10.dp.toFloat()
    private val TWO_PX = 2.dp.toFloat()
    private var mRadius = floatArrayOf(TEN_PX, TEN_PX, TEN_PX, TEN_PX, TEN_PX, TEN_PX, TWO_PX, TWO_PX)
    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.PaletteTextView, 0, 0)
        val paletteDrawable = ta.getDrawable(R.styleable.PaletteTextView_palette_src)
        if (paletteDrawable == null) {
            val paletteUrl = ta.getString(R.styleable.PaletteTextView_palette_src)
            if (paletteUrl != null) {
                MainScope().launch {
                    launch(Dispatchers.IO) {
                        val swatch = Palette.from(Glide.with(context).load(paletteUrl).asBitmap().into(360, 640).get()).generate().getVibrantSwatch()
                        if (swatch != null) {
                            adaptColor = swatch.rgb
                        }
                    }.join()
                    setColor(adaptColor)
                }
            }
        } else {
            MainScope().launch {
                launch(Dispatchers.IO) {
                    val swatch = Palette.from(Util.DrawableToBitmap(paletteDrawable)).generate().getVibrantSwatch()
                    if (swatch != null) {
                        adaptColor = swatch.rgb
                    }
                }.join()
                setColor(adaptColor)
            }
        }
        ta.recycle()

    }

    private fun setColor(backgroundColor: Int) {
        val roundBackgroundDrawable = RoundBackgroundDrawable();
        roundBackgroundDrawable.setBounds(0, 0, width, height)
        roundBackgroundDrawable.setRadius(mRadius);
        roundBackgroundDrawable.setColor(backgroundColor, Color.TRANSPARENT)
        background = roundBackgroundDrawable;
    }
}