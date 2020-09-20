package com.leqi.scooterrecite.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.leqi.scooterrecite.R
import kotlinx.android.synthetic.main.title_view_layout.view.*

open class TitleView : LinearLayout {


    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0) {}

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

        initView()
        setAttributes(attributeSet)
    }

    @SuppressLint("CustomViewStyleable")
    private fun setAttributes(attributeSet: AttributeSet) {
        val a: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleView)
        val title = a.getString(R.styleable.TitleView_title_text)
        val titleColor = a.getColor(R.styleable.TitleView_title_text_color,resources.getColor(R.color.normalTextColor))
        val rightSrc = a.getDrawable(R.styleable.TitleView_right_src)
        val rightSrc2 = a.getDrawable(R.styleable.TitleView_right_src2)
        val leftSrc = a.getDrawable(R.styleable.TitleView_left_src)
        titleViewTitleTv.text = title
        titleViewTitleTv.setTextColor(titleColor)
        titleViewRightBtn.setImageDrawable(rightSrc)
        titleViewRight2Btn.setImageDrawable(rightSrc2)
        leftSrc?.let { titleViewBackBtn.setImageDrawable(leftSrc) }
        a.recycle()
    }


    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.title_view_layout, this, true)
        titleViewTitleTv!!.isFocusable = true
        titleViewTitleTv!!.isFocusableInTouchMode = true
        titleViewTitleTv!!.requestFocus()
        setBackBtnDefaultClickListener()

    }

    /**
     * 设置背景色
     */
    open fun setTitleBackground(color: Int) {
        this.setBackgroundColor(color)
    }


    /**
     * 设置返回按钮默认点击操作：finish
     */
    open fun setBackBtnDefaultClickListener() {
        titleViewBackBtn!!.setOnClickListener {
            (context as Activity).onBackPressed()
        }
    }

    /**
     * 设置返回按钮点击事件
     *
     * @param clickListener
     */
    open fun setBackBtnClickListener(clickListener: View.OnClickListener) {
        if (titleViewBackBtn != null) titleViewBackBtn!!.setOnClickListener(clickListener)
    }

    /**
     * 设置标题栏右侧按钮点击事件
     *
     * @param clickListener
     */
    open fun setRightBtnClickListener(clickListener: View.OnClickListener) {
        if (titleViewRightBtn != null) titleViewRightBtn!!.setOnClickListener(clickListener)
    }

    /**
     * 设置标题栏右侧按钮图片资源
     *
     * @param resId
     */
    open fun setRightBtnImageRes(@DrawableRes resId: Int) {
        if (titleViewRightBtn != null) titleViewRightBtn!!.setImageResource(resId)
    }

    /**
     * 设置标题栏右侧按钮点击事件
     *
     * @param clickListener
     */
    open fun setRight2BtnClickListener(clickListener: View.OnClickListener) {
        if (titleViewRight2Btn != null) titleViewRight2Btn!!.setOnClickListener(clickListener)
    }

    /**
     * 设置标题栏右侧按钮图片资源
     *
     * @param resId
     */
    open fun setRight2BtnImageRes(@DrawableRes resId: Int) {
        if (titleViewRight2Btn != null) titleViewRight2Btn!!.setImageResource(resId)
    }

    /**
     * 设置标题栏文字
     *
     * @param resId
     */
    open fun setTitleText(@StringRes resId: Int) {
        if (titleViewTitleTv == null) return
        titleViewTitleTv!!.setText(resId)
    }

    /**
     * 设置标题栏文字
     *
     * @param title
     */
    open fun setTitleText(title: String?) {
        if (titleViewTitleTv == null) return
        titleViewTitleTv!!.text = title
    }
}