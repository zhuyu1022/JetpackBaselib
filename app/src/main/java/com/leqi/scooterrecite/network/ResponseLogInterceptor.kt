package com.leqi.scooterrecite.network

import android.content.Intent
import android.util.Log
import com.blankj.utilcode.util.ActivityUtils
import com.leqi.scooterrecite.util.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response

class ResponseLogInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.d(TAG, "code     =  : " + response.code)
        Log.d(TAG, "message  =  : " + response.message)
        Log.d(TAG, "protocol =  : " + response.protocol)
        Log.d(TAG, "string   =  : " + response.body?.string())

        return  chain.proceed(request)
    }

    companion object {
        const val TAG="ResponseLogInterceptor"
    }
}