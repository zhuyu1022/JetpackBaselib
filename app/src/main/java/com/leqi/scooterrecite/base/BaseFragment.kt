package com.leqi.scooterrecite.base

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.base.fragment.BaseVmFragment
import com.leqi.scooterrecite.ui.home.dialog.InProductionDialog
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/21
 * 描述　: 你项目中的Fragment基类，在这里实现显示弹窗，吐司，还有自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmFragment例如
 * abstract class BaseFragment<VM : BaseViewModel> : BaseVmFragment<VM>() {
 */
abstract class BaseFragment<VM : BaseViewModel> : BaseVmFragment<VM>() {

//    private var dialog: MaterialDialog? = null
//
//    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
//    val shareViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }
//
//    //Application全局的ViewModel，用于发送全局通知操作
//    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    private var mLoadingDialog: BasePopupView? = null

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        mLoadingDialog = XPopup.Builder(requireActivity()).asCustom(InProductionDialog(requireActivity(), message)).show()
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


}