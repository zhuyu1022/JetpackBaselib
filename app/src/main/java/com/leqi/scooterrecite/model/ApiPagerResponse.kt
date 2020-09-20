package com.leqi.scooterrecite.model

import me.hgj.jetpackmvvm.network.BaseResponse
import java.io.Serializable

/**
 *  分页数据的基类
 */
data class ApiPagerResponse<T>(
    var datas:List<T> ,
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
) :Serializable {

}