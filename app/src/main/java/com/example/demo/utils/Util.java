package com.example.demo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Administrator on 2020/4/3
 */
public class Util {
    /**
     * 设置TextView段落间距
     *
     * @param context          上下文
     * @param tv               给谁设置段距，就传谁
     * @param content          文字内容
     * @param paragraphSpacing 请输入段落间距（单位dp）
     * @param lineSpacingExtra xml中设置的的行距（单位dp）
     */
    public static void setParagraphSpacing(Context context, TextView tv, String content,  int paragraphHeight) {
        if (!content.contains("\n")) {
            tv.setText(content);
            return;
        }
        content = content.replace("\n", "\n\r");

        int previousIndex = content.indexOf("\n\r");
        //记录每个段落开始的index，第一段没有，从第二段开始
        List<Integer> nextParagraphBeginIndexes = new ArrayList<>();
        nextParagraphBeginIndexes.add(previousIndex);
        while (previousIndex != -1) {
            int nextIndex = content.indexOf("\n\r", previousIndex + 2);
            previousIndex = nextIndex;
            if (previousIndex != -1) {
                nextParagraphBeginIndexes.add(previousIndex);
            }
        }
        //获取行高（包含文字高度和行距）
        float lineHeight = tv.getLineHeight();

        //把\r替换成透明长方形（宽:1px，高：字高+段距）
        SpannableString spanString = new SpannableString(content);
        Drawable d = ContextCompat.getDrawable(context, R.drawable.shape_transparent);
        float density = context.getResources().getDisplayMetrics().density;
        //int强转部分为：行高 - 行距 + 段距
        d.setBounds(0, 0, 130, paragraphHeight);

        for (int index : nextParagraphBeginIndexes) {
            // \r在String中占一个index
            spanString.setSpan(new ImageSpan(d), index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanString);
    }
}
