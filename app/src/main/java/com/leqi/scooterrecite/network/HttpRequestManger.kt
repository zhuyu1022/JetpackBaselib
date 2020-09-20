package com.leqi.scooterrecite.network

import com.leqi.scooterrecite.config.Config


/**
 * 作者　: hegaojian
 * 时间　: 2020/5/4
 * 描述　: 从网络中获取数据
 */
const val HTTP_SUCCESS=200
class HttpRequestManger {

    companion object {

        /**
         * 如果是复杂的 需要请求多个接口的，或者多个异步请求等等，可以写在这里，普通简单的接口直接 apiService 调用
         */
        val instance: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpRequestManger()
        }

        //双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
        val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi.instance.getApi(ApiService::class.java, Config.BASE_URL)
        }

        //双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
        val apiServiceOss: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkOssApi.instance.getApi(ApiService::class.java, Config.BASE_URL)
        }
    }


}