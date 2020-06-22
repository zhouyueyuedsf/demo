package com.example.demo.views.importantview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.demo.MyApplication;
import com.example.demo.R;

/*
 * @(#)RippleButton
 * Created by yaodh on 2018/8/14.
 * @link yaodh@rd.netease.com
 * @description
 *
 * Copyright 2018 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
public class RippleButton extends AppCompatTextView {
    public RippleButton(Context context) {
        this(context, null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setRippleBackground(context, attrs);
    }

    public static Drawable createStrokeSolidDrawable(
        int strokeWidth, int strokeColor, int solidColor, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setColor(solidColor);

        return drawable;
    }

    public static void setSelectorDrawable(View view, float radius) {
        view.setBackground(createSelectorDrawable(view, radius));
    }

    public static Drawable createSelectorDrawable(View view, float radius) {
        Drawable normal = view.getBackground(); // 原始背景
        int left = view.getPaddingLeft();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int bottom = view.getPaddingBottom();

        // 相当于selector背景
        StateListDrawable stateListDrawable = new StateListDrawable() {
            /**
             * 使用StateListDrawable会导致padding失效，需要重载getPadding方法手动设置
             * @param padding 需要设置padding的Rect
             * @return 是否需要更新padding，如果4个padding都为0就不更新
             */
            @Override
            public boolean getPadding(Rect padding) {
                padding.set(left, top, right, bottom);
                return (padding.left | padding.top | padding.right | padding.bottom) != 0;
            }
        };

        // pressed状态下覆盖一层阴影
        GradientDrawable mask = new GradientDrawable();
        mask.setCornerRadius(radius);
        mask.setColor(MyApplication.Companion.getInstance().getResources().getColor(R.color.button_pressed));
        LayerDrawable pressed = new LayerDrawable(new Drawable[]{normal, mask});

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed); // pressed状态
        stateListDrawable.addState(new int[]{}, normal); // normal状态

        return stateListDrawable;
    }

    private void setRippleBackground(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleButton, 0, 0);
        int strokeColor = a.getColor(R.styleable.RippleButton_stroke_color, 0);
        int strokeWidth = a.getDimensionPixelSize(R.styleable.RippleButton_stroke_width, 0);
        int solidColor = a.getColor(R.styleable.RippleButton_solid_color, 0);
        int solidCorner = a.getDimensionPixelSize(R.styleable.RippleButton_solid_corner, 0);
        a.recycle();

        if (getBackground() == null) {
            setBackground(createStrokeSolidDrawable(strokeWidth, strokeColor, solidColor, solidCorner));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList stateList = getResources().getColorStateList(R.color.button_pressed);
            RippleDrawable d = new RippleDrawable(stateList, getBackground(), null);
            setBackground(d);
        } else {
            setSelectorDrawable(this, 6);
        }
    }
}
