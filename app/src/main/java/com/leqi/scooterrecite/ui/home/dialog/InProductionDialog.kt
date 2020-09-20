package com.leqi.scooterrecite.ui.home.dialog

import android.annotation.SuppressLint
import android.content.Context
import com.leqi.scooterrecite.R
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.common_dialog_loading.view.*


/**
 * @Description:    证件照制作中对话框
 * @Author:         ZHUYU
 * @CreateDate:     2019/10/16$ 10:13$
 * @UpdateDate:     2019/10/16$ 10:13$
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@SuppressLint("ViewConstructor")
class InProductionDialog constructor(context: Context, title:String) : CenterPopupView(context) {

    var mTitle: String =title

    override fun getImplLayoutId(): Int {
        return  R.layout.common_dialog_loading
    }

    override fun onCreate() {

        if (mTitle.isNotEmpty()) {
            titileTv.text = mTitle
        }
    }

}