package com.leqi.scooterrecite.network

import com.leqi.scooterrecite.BuildConfig
import com.leqi.scooterrecite.config.Config
import com.leqi.scooterrecite.util.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
           // .addHeader("App-Key", Config.APP_ID)
           // .addHeader("Client-Type", "HUAWEI")
          //  .addHeader("User-Key", userKey)
          //  .addHeader("Software-Version", BuildConfig.VERSION_NAME)
        builder .addHeader("Content-Type", "application/json").build()
        builder   .addHeader("Accept", "application/json").build()
        builder  .addHeader("Connection", "close").build()

        return chain.proceed(builder.build())
    }


}