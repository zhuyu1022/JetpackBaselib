package com.leqi.scooterrecite.ui.home.activity

import android.os.Bundle
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseActivity
import com.leqi.scooterrecite.util.ActivityExt.start
import com.leqi.scooterrecite.viewmodel.WelcomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : BaseActivity<WelcomeViewModel>() {
    override fun layoutId(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
      launch {
          delay(2000)
          start<MainActivity>()
      }
    }

    override fun initData() {

    }


    override fun createObserver() {

    }







}