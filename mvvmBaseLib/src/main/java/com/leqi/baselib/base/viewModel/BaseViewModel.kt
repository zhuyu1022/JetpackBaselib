package com.leqi.baselib.base.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

//    abstract var mRepository: T
////    open fun BaseViewModel(application: Application) {
////        super(application)
////        mRepository = TUtil.getNewInstance(this, 0)
////    }


    override fun onCleared() {
        super.onCleared()
        // mRepository?.onCleared()
    }

    fun showLoading(msg: String) {
        loadingChange.showDialog.postValue(msg)
    }

    fun dismissLoading() {
        loadingChange.dismissDialog.postValue(null)
    }

    fun onError(msg: String?){
        loadingChange.errorMsg.postValue(msg)
    }
    fun onSuccess(msg: String?){
        loadingChange.errorMsg.postValue(msg)
    }

    /**设置加载状态**/
    fun setStatus(status: String) {
        loadingChange.status.postValue(status)
    }


    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { MutableLiveData<String>() }

        //隐藏
        val dismissDialog by lazy { MutableLiveData<String>() }

        //错误信息
        val errorMsg by lazy { MutableLiveData<String>() }

        //加载状态监听
        val status by lazy { MutableLiveData<String>() }
    }


}