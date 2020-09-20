package com.leqi.baselib.base.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

    fun showLoading(msg: String) {
        loadingChange.showDialog.postValue(msg)
    }

    fun dismissLoading() {
        loadingChange.dismissDialog.postValue(true)
    }
    @Deprecated("已过时的方法，直接toast就行了")
    fun onError(msg: String?){
        loadingChange.errorMsg.postValue(msg)
    }
    @Deprecated("已过时的方法，直接toast就行了")
    fun onSuccess(msg: String?){
        loadingChange.errorMsg.postValue(msg)
    }

    /**设置加载状态**/
    @Deprecated("已过时的方法，之前傻逼了，不应该这样做的")
    fun setStatus(status: Int) {
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
        val dismissDialog by lazy { MutableLiveData<Boolean>() }

        //错误信息
        val errorMsg by lazy { MutableLiveData<String>() }

        //加载状态监听
        val status by lazy { MutableLiveData<Int>() }
    }


}