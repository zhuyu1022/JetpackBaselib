package com.leqi.scooterrecite.base

import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.leqi.baselib.base.activity.BaseVmActivity
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.scooterrecite.ui.home.dialog.InProductionDialog
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView



/**
 * 时间　: 2019/12/21
 * 作者　: hegaojian
 * 描述　: 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {




    override fun onCreate(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarLightMode(this,true)
        super.onCreate(savedInstanceState)

    }

    private var mLoadingDialog: BasePopupView? = null


    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        mLoadingDialog = XPopup.Builder(this).asCustom(InProductionDialog(this, message)).show()
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        mLoadingDialog?.dismiss();
    }


    override fun onError(message: String) {
        if(message.isNotEmpty()){
            ToastUtils.showShort(message)
        }
    }

    /**
     * 加载框是否正在显示
     */
    open fun isLoadingDialogShowing(): Boolean {
        if (mLoadingDialog != null && mLoadingDialog?.isShow!!) {
            return true
        }
        return false
    }

}