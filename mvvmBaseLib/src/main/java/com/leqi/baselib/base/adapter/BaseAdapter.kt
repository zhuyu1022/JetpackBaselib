package com.leqi.baselib.base.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


abstract  class BaseAdapter<T, K : BaseViewHolder>(@LayoutRes  layoutResId:Int)  : BaseQuickAdapter<T, K>(layoutResId) {
    var mSelectedPosition: Int = -1


    fun setCurrent(index: Int) {
        val lastSelected = mSelectedPosition
        mSelectedPosition = index
        notifyItemChanged(mSelectedPosition)
        notifyItemChanged(lastSelected)
    }
}