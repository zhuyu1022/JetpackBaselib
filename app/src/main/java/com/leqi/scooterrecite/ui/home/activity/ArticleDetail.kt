package com.leqi.scooterrecite.ui.home.activity

import android.os.Bundle
import com.github.penfeizhou.animation.apng.APNGDrawable
import com.github.penfeizhou.animation.loader.ResourceStreamLoader
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_artivle_detail.*

class ArticleDetail :BaseActivity<BaseViewModel>(){
    override fun layoutId(): Int= R.layout.activity_artivle_detail


    override fun initView(savedInstanceState: Bundle?) {
        val resourceLoader = ResourceStreamLoader(this, R.drawable.mic_bg)
        val apngDrawable = APNGDrawable(resourceLoader)
        imageView.setImageDrawable(apngDrawable)
    }

    override fun initData() {

    }

    override fun createObserver() {

    }
}