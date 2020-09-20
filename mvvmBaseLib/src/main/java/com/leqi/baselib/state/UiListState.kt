package com.leqi.baselib.state

class UiListState<T> {
    //列表数据
    val listData: ArrayList<T> = arrayListOf()
    var status: Int = Status.LOADING
}