package com.leqi.scooterrecite


import android.app.Application
import android.graphics.Color
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils

import com.lxj.xpopup.XPopup


import com.tencent.mmkv.MMKV



/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　:
 */
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initUtils()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        XPopup.setAnimationDuration(200);
    }



    private fun initUtils() {
        //设置toast样式
        ToastUtils.setBgColor(Color.parseColor("#111111"))
        ToastUtils.setMsgColor(ColorUtils.getColor(R.color.white))
    }


}
