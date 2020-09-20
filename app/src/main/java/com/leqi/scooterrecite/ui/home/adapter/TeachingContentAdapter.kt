package com.leqi.scooterrecite.ui.home.adapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.leqi.baselib.base.adapter.BaseAdapter
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.model.Article


/**
 * 描述：
 * 作者：ZHUYU
 * 日期：2019/9/29 10:35
 */
class TeachingContentAdapter : BaseAdapter<Article, BaseViewHolder>(R.layout.item_teaching_content_layout) {
    override fun convert(holder: BaseViewHolder, item: Article) {

        holder.setText(R.id.titleTv,item.title)
        holder.setText(R.id.authorTv,item.author)


    }

}