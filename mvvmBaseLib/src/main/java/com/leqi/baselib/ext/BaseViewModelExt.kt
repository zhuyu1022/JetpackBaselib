package com.leqi.baselib.ext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.baselib.base.activity.BaseVmActivity
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.base.viewModel.Status
import kotlinx.coroutines.*
import java.net.UnknownHostException


/**
 * 处理网络请求
 */
fun <T> BaseViewModel.launchRequest(
    request: suspend () -> T?,
    onSuccess: (T) -> Unit = {},
    onFail: (Throwable) -> Unit = {},
    onNetError: () -> Unit = {}
) {

    viewModelScope.launch {
        try {
            val res: T? = request() // IO线程中执行网络请求，成功后返回这里继续执行
            res?.let {
                onSuccess(it)
            }
        } catch (e: UnknownHostException) {
            //无网络时会报此异常
            onNetError()
        } catch (e: CancellationException) {
            Log.e("launchRequest", "job cancelled")
        } catch (e: Exception) {
            Log.e("launchRequest", "request caused exception:${e.toString()}")
            onFail(e)
        }
    }

}


fun <T> BaseVmActivity<*>.parseState(
    resultState: ResultState<T>,
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onNoResult: () -> Unit = {},
    onNoNetWork: () -> Unit = {}
) {
    when (resultState.status) {
        Status.LOADING -> {
            onLoading()
        }
        Status.SUCCESS -> {
            resultState.mData?.let { onSuccess(it) }
        }
        Status.NO_RESULT -> {
            onNoResult()
        }
        Status.ERROR -> {
            onError(resultState.throwable)
        }
        Status.NO_NETWORK -> {
            onNoNetWork()
        }
    }
}


/**
 *  不过滤请求结果
 * @param request 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.              request(
    request: suspend () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit,
    isShowDialog: Boolean = false,
    loadingMessage: String = "加载中...",
    onNetError: () -> Unit = { ToastUtils.showShort("当前无网络连接") }
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    if (isShowDialog)  showLoading(loadingMessage)
    return viewModelScope.launch {
        runCatching {
            //请求时调度在Io线程中
            withContext(Dispatchers.IO) { request() }
        }.onSuccess {
            //网络请求成功 关闭弹窗
            // dismissLoading()
            //成功回调
            success(it)
        }.onFailure {
            if (!NetworkUtils.isConnected()) {
                onNetError()
            } else {
                error(it)
            }
            //网络请求异常 关闭弹窗
            dismissLoading()
            //打印错误消息
            Log.e("launchRequest", it.message.toString())
            //失败回调
        }
    }
}



