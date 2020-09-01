package com.leqi.baselib.ext

import androidx.lifecycle.MutableLiveData
import com.leqi.baselib.base.viewModel.Status


/**
  * @Description:  自定义结果集封装类
  * @Author:         ZHUYU
  * @CreateDate:     2020/7/3 15:39
  * @UpdateRemark:   更新说明：
  * @UpdateDate:     2020/7/3 15:39
  * @Version:        1.0
 */
sealed class ResultState< T> {
    var status = Status.LOADING
    var throwable = Throwable()
    var mData: T? = null
    fun postLoading():ResultState<T> {
        status = Status.LOADING
        return this
    }

    fun setSuccess(data: T):ResultState< T> {
        mData = data
        status = Status.SUCCESS
        return this
    }

    fun setNoResult() :ResultState< T>{
        status = Status.NO_RESULT
        return this
    }

    fun setError(throwable: Throwable):ResultState< T> {
        status = Status.ERROR
        this.throwable = throwable
        return this
    }

    fun setNoNetWork() :ResultState< T>{
        status = Status.NO_NETWORK
        return this
    }
}






