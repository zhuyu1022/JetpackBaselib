package com.leqi.baselib.state


/**
 * 数据状态解析
 */
//fun <T>BaseVmActivity<*>.parseState(uiState:UiState<T>,
//    onSuccess: (T) -> Unit = {},
//    onLoading: (() -> Unit)? = null,
//    onEmpty: (() -> Unit)? = null,
//    onError: (() -> Unit)? = null,
//    onNetError: (() -> Unit)? = null){
//    when(uiState.status){
//        Status.LOADING->{
//            onLoading?.run { this }
//        }
//        Status.SUCCESS->{
//            uiState.data?.let { onSuccess(it) }
//        }
//        Status.EMPTY->{
//            onEmpty?.run { this }
//        }
//        Status.NET_ERROR->{
//            onNetError?.run { this }
//        }
//        Status.ERROR->{
//            onError?.run { this }
//        }
//    }
//}
//fun <T>BaseVmFragment<*>.parseState(uiState:UiState<T>,
//    onSuccess: (T) -> Unit = {},
//    onLoading: (() -> Unit)? = null,
//    onEmpty: (() -> Unit)? = null,
//    onError: (() -> Unit)? = null,
//    onNetError: (() -> Unit)? = null){
//    when(uiState.status){
//        Status.LOADING->{
//            onLoading?.run { this }
//        }
//        Status.SUCCESS->{
//            uiState.data?.let { onSuccess(it) }
//        }
//        Status.EMPTY->{
//            onEmpty?.run { this }
//        }
//        Status.NET_ERROR->{
//            onNetError?.run { this }
//        }
//        Status.ERROR->{
//            onError?.run { this }
//        }
//    }
//}

/**
 * 数据状态解析
 */
fun <T>UiState<T>.parseState(
    onSuccess: (T) -> Unit = {},
    onLoading: (() -> Unit)? = null,
    onEmpty: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onNetError: (() -> Unit)? = null){
    when(status){
        Status.LOADING->{
            onLoading?.let { it() }
        }
        Status.SUCCESS->{
            data?.let { onSuccess(it) }
        }
        Status.EMPTY->{
            onEmpty?.let { it() }
        }
        Status.NET_ERROR->{
            onNetError?.let { it() }
        }
        Status.ERROR->{
            onError?.let { it() }
        }
    }
}


