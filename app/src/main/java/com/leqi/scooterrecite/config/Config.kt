package com.leqi.scooterrecite.config

import android.os.Environment


/**
 * 全部配置类
 */
object Config {

    const val APP_NAME = "金陵金城武的目录"

    const val BASE_URL = "https://www.wanandroid.com"
    const val PHONE="123456789"
    //根文件夹
    private val BASE_DIR = Environment.getExternalStorageDirectory()!!
    //根目录名称
    val BASE_FOLDER_NAME = "$BASE_DIR/$APP_NAME"
    const val JSON_TYPE = "application/json; charset=utf-8"



}
