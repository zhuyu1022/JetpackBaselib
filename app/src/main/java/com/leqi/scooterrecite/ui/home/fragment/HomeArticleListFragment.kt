package com.leqi.scooterrecite.ui.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.ext.logd
import com.leqi.baselib.state.Status
import com.leqi.baselib.state.parseState
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseFragment
import com.leqi.scooterrecite.ui.home.adapter.TeachingContentAdapter
import com.leqi.scooterrecite.util.toast
import com.leqi.scooterrecite.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_teaching_content.*


/**
 * @Description:    年级课文内容
 * @Author:         ZHUYU
 * @CreateDate:     2020/9/4 9:24
 */
class HomeArticleListFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int = R.layout.fragment_teaching_content
    private val mAdapter: TeachingContentAdapter by lazy { TeachingContentAdapter() }

    //注意不要写viewModels()  不然拿不到activity作用域的viewmodel
    private val mMainViewmodel: MainViewModel by activityViewModels()

    @SuppressLint("InflateParams")
    override fun initView(savedInstanceState: Bundle?) {
        recyclerview.apply {
            adapter = mAdapter
        }



    }





    @SuppressLint("InflateParams")
    override fun createObserver() {
        mMainViewmodel.articlrList.observe(this, { it ->
            statusView.setStatusType(it.status)
            it.parseState(onSuccess = {
                mAdapter.setList(it.data.datas)
                    "onSuccess".logd()
            }, onLoading = {
                "onLoading".logd()
            }, onEmpty = {
                "onEmpty".logd()
            }, onNetError = {
                "onNetError".logd()
            }, onError = {
                "onError".logd()
            })
        })
    }

    override fun initData() {

    }






}