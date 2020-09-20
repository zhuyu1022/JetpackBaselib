package com.leqi.scooterrecite.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseFragment
import com.leqi.scooterrecite.viewmodel.MainViewModel


class GaokaoHotFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int = R.layout.fragment_gaokao_hot
    private val mMainViewmodel: MainViewModel by activityViewModels()



    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {

    }

    override fun initData() {

    }




}