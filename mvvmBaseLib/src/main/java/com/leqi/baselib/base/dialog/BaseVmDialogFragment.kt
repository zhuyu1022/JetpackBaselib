package com.leqi.baselib.base.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.ext.getVmClazz

abstract  class BaseVmDialogFragment<VM: BaseViewModel>:
    BaseEasyDialogFragment() {
    lateinit var mViewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = createViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObserver()
    }

    /**
     * 创建观察者
     */
    abstract fun createObserver()


    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

}