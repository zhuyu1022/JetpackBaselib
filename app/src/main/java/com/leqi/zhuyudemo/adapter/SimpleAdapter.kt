package com.leqi.zhuyudemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.leqi.zhuyudemo.R

class SimpleAdapter:BaseQuickAdapter<String ,BaseViewHolder>(R.layout.item_flexbox_layout) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.titleTv,item)
    }
}