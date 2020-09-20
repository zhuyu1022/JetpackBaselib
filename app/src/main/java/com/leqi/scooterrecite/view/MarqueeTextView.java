package com.leqi.scooterrecite.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/*
 * @Author: zhuxiaoyao
 * @Date: 2019/7/19 - 13 : 49
 * @LastEditors: zhuxiaoyao
 * @LastEditTime: 2019/7/19 - 13 : 49
 * @Description: institute-Android
 * @Email: zhuxs@venpoo.com
 * 跑马灯文字 横向滚动文字
 */
public class MarqueeTextView extends AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
