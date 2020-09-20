package com.leqi.scooterrecite.ui.home.fragment


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.leqi.baselib.state.Status
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseFragment
import com.leqi.scooterrecite.ui.home.viewmodel.ImportContentViewModel
import com.leqi.scooterrecite.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_import_content.teachingContentRecyclerview



class ImportContentFragment : BaseFragment<ImportContentViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_import_content
    private val mMainViewmodel: MainViewModel by activityViewModels()


    override fun initView(savedInstanceState: Bundle?) {

        teachingContentRecyclerview.apply {

        }
        mViewModel.setStatus(Status.EMPTY)
    }


    override fun createObserver() {


    }

    override fun initData() {

    }




    override fun onStatusChanged(status: Int) {


    }
}