package com.leqi.baselib.ext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.state.Status
import com.leqi.baselib.network.AppException
import com.leqi.baselib.network.Error
import com.leqi.baselib.network.ExceptionHandle
import com.leqi.baselib.state.UiState
import kotlinx.coroutines.*
import com.leqi.baselib.network.BaseResponse
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


/**
 *  不过滤请求结果------>最常用的的方法
 * @param request 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    request: suspend () -> T,
    success: (T) -> Unit,
    error: (AppException) -> Unit,
    isShowDialog: Boolean = false,
    loadingMessage: String = "加载中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
    return viewModelScope.launch {
        runCatching {
            //请求时调度在Io线程中
            withContext(Dispatchers.IO) { request() }
        }.onSuccess {
            runCatching {
                //网络请求成功 关闭弹窗
                if (isShowDialog)   loadingChange.dismissDialog.postValue(false)
                //成功回调
                success(it)
            }.onFailure { e ->
                //打印错误消息
                e.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            if (isShowDialog) loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}



/**
 *  请求时同时  解析到数据状态  除了empty的情况需要手动判断
 * @param request 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.requestParseData(liveData: MutableLiveData< UiState<T>>,
    request: suspend () -> T,
    success: (uiState: UiState<T>) -> Unit,
    error: (AppException) -> Unit,
    isShowDialog: Boolean = false,
    loadingMessage: String = "加载中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
    val uiState=UiState<T>()
    return viewModelScope.launch {
        runCatching {
            uiState.status=Status.LOADING
            liveData.postValue(uiState)
            //请求时调度在Io线程中
            withContext(Dispatchers.IO) { request() }
        }.onSuccess {
            runCatching {
                //网络请求成功 关闭弹窗
                if (isShowDialog)   loadingChange.dismissDialog.postValue(false)
                uiState.data=it
                uiState.status=Status.SUCCESS
                liveData.postValue(uiState)
                //成功回调
                success(uiState)
            }.onFailure { e ->
                uiState.status=Status.ERROR
                liveData.postValue(uiState)
                //打印错误消息
                e.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            if (isShowDialog)  loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            val appException=ExceptionHandle.handleException(it)
            if (appException.isNoNet()){
                uiState.status=Status.NET_ERROR
            }else{
                uiState.status=Status.ERROR
            }
            liveData.postValue(uiState)
            //失败回调
            error(appException)
        }
    }
}



/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.requestCheck(
    request: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String = "加载中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            withContext(Dispatchers.IO) { request() }
        }.onSuccess {
            runCatching {
                //网络请求成功 关闭弹窗
                if (isShowDialog)  loadingChange.dismissDialog.postValue(false)
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t -> success(t) }
            }.onFailure { e ->
                //打印错误消息
                e.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            if (isShowDialog)  loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        if (response.isSucces()) success(response.getResponseData())
        else throw AppException(
            response.getResponseCode(),
            response.getResponseMsg()
        )
    }
}


/**
 * 判断AppException是不是网络错误
 */
fun AppException.isNoNet():Boolean{
    return errCode == Error.NO_NETWORK.getKey()
}



