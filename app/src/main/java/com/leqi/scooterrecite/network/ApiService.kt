package com.leqi.scooterrecite.network

import com.leqi.scooterrecite.model.ApiPagerResponse
import com.leqi.scooterrecite.model.ApiResponse
import com.leqi.scooterrecite.model.Article
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 网络API
 */
interface ApiService {


    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getAritrilList(@Path("page") pageNo: Int): ApiResponse<ApiPagerResponse<Article>>


}