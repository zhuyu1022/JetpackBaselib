package com.leqi.baselib.state

class UiState<T> {
    //列表数据
    var data: T ?= null
    var status: Int = Status.LOADING
}