package com.leqi.baselib.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.ext.getVmClazz

abstract class BaseVmFragment<VM : BaseViewModel>:Fragment() {

    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        registerUiChange()
        initData()
    }
    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    open fun initParam() {}

    abstract fun initData()
    /**
     * 创建观察者
     */
    abstract fun createObserver()


    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()
    abstract fun onError(message: String = "")

    @Deprecated("已过时的方法，之前傻逼了，不应该这样做的")
    open fun onStatusChanged(status: Int) {}

    /**
     * 注册 UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading(
                if (it.isEmpty()) {
                    "请求网络中..."
                } else it
            )
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
        mViewModel.loadingChange.status.observe(viewLifecycleOwner, Observer {
            onStatusChanged(it)
        })
        mViewModel.loadingChange.errorMsg.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                onError(it)
            }
            dismissLoading()
        })
    }
}