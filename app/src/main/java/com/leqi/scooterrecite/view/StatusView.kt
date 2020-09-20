package com.leqi.scooterrecite.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.leqi.baselib.state.Status
import com.leqi.scooterrecite.R
import kotlinx.android.synthetic.main.status_view_layout.view.*


@SuppressLint("ViewConstructor")
class StatusView : LinearLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
    }

    init {
        initView()
    }

    fun initView() {
        LayoutInflater.from(context).inflate(R.layout.status_view_layout, this, true)

    }

    private var mListener: onStatusBtnClickListener? = null

  fun   interface onStatusBtnClickListener {
        fun onClick()
    }

    var noResultTitleStr = "这里空空如也，还没有导入内容哦！"
    var noResultBtnStr = "立即拍摄内容"
    var noresultTips = ""
    var loadingTitleStr = "努力加载中…"
    var netErrorTitleStr = "网络不太给力，请重新加载哦！"
    var netErrorBtnStr = "刷新一下"
    var errorTitleStr = "哎呀，出错了，刷新重试一下"
    var errorBtnStr = "刷新一下"

    fun setStatusType(type: Int) {
        when (type) {
            Status.LOADING -> {
                this.visibility = View.VISIBLE
                statusImg.setImageResource(R.mipmap.status_loading)
                statusTitleTv.text = loadingTitleStr
                statusBtn.visibility = View.GONE
            }
            Status.SUCCESS -> {
                this.visibility = View.GONE
            }
            Status.EMPTY -> {
                this.visibility = View.VISIBLE
                statusImg.setImageResource(R.mipmap.status_noresult)
                statusTitleTv.text = noResultTitleStr
                statusBtn.visibility = View.VISIBLE
                statusBtn.text = noResultBtnStr
            }

            Status.NET_ERROR -> {
                this.visibility = View.VISIBLE
                statusImg.setImageResource(R.mipmap.status_net_error)
                statusTitleTv.text = netErrorTitleStr
                statusBtn.text = netErrorBtnStr
                statusBtn.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                this.visibility = View.VISIBLE
                statusImg.setImageResource(R.mipmap.status_noresult)
                statusTitleTv.text = errorTitleStr
                statusBtn.text = errorBtnStr
                statusBtn.visibility = View.VISIBLE
            }
        }
        statusBtn.setOnClickListener { mListener?.onClick() }
    }

    /**
     * 设置按钮监听器
     */
    fun setOnStatusBtnClickListener(listener: onStatusBtnClickListener) {
        mListener = listener
    }

    /**
     * 设置标题
     */
    fun setStatusTitle(title: String?) {
        if (title.isNullOrEmpty()) {
            statusTitleTv.visibility = View.GONE
        } else {
            statusTitleTv.text = title
            statusTitleTv.visibility = View.VISIBLE
        }
    }


    /**
     * 设置按钮文字
     */
    fun setStatusBtnText(btn: String?) {
        if (btn.isNullOrEmpty()) {
            statusBtn.visibility = View.GONE
        } else {
            statusBtn.text = btn
            statusBtn.visibility = View.VISIBLE
        }
    }


}