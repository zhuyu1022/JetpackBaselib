package com.leqi.scooterrecite.ui.home.dialog

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.VibrateUtils
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.config.Config
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_customer_layout.view.*

/**
 * @Description:    客服
 * @Author:         ZHUYU
 * @CreateDate:     2020/9/14 17:35
 */
@SuppressLint("ViewConstructor")
class CustomerDialog constructor(context: Context) : BottomPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_customer_layout
    }

    override fun onCreate() {
        super.onCreate()
        callTv.setOnClickListener {
            PhoneUtils.dial(Config.PHONE)
        }
        copyTv.setOnClickListener {
            val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clipData = ClipData.newPlainText("微信", Config.PHONE)
            cm.setPrimaryClip(clipData)
            VibrateUtils.vibrate(500)
            ToastUtils.showLong("已复制微信号:12345")
        }
        cancelTv.setOnClickListener {
            dismiss()
        }
    }


}