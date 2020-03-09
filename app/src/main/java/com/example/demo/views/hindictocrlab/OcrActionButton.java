package com.example.demo.views.hindictocrlab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageButton;


/**
 * Created by yaodh on 2018/1/17.
 */

public class OcrActionButton extends AppCompatImageButton {
    // 中间圆形按钮的各种不同状态
    public static final int TYPE_LOCK = 0;
    public static final int TYPE_LOCK_CANCEL = 1;
    public static final int TYPE_REGION_CAPTURE = 2;
    public static final int TYPE_REGION_CROP = 3;
    public static final int TYPE_REGION_OCR = 4;
    public static final int TYPE_REGION_DONE = 5;


    private int[] bgColors = {
        0xFFF44336, 0xFFFE6B60,
        0xFFF44336,
        0xFFF44336, 0xFF767676,
        0xFF767676
    };

    // 这个ActionType记录的是按钮的状态
    private int actionType = 0;

    public OcrActionButton(Context context) {
        super(context);
    }

    public OcrActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OcrActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setActionType(int actionType) {
        if (this.actionType == actionType) {
            return;
        }
        this.actionType = actionType;

        // 设置不同的背景颜色和图标
//        setImageResource(actionResId[actionType]);
//        setBackgroundTintList(ColorStateList.valueOf(bgColors[actionType]));
    }
}
