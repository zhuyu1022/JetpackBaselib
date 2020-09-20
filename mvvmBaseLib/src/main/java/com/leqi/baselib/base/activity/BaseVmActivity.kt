package com.leqi.baselib.base.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.ext.getVmClazz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity(),
    CoroutineScope by MainScope() {

    abstract fun layoutId(): Int
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createInsideViewModel()
        initParam()
        initView(savedInstanceState)
        initEvent()
        initData()
        registerUiChange(mViewModel)
        createObserver()
    }


    abstract fun initView(savedInstanceState: Bundle?)

    open fun initParam() {}
    open fun initEvent() {}
    abstract fun initData()

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()

    abstract fun onError(message: String = "")

    @Deprecated("已过时的方法，之前傻逼了，不应该这样做的")
    open fun onStatusChanged(status: Int) {}

    /**
     * 创建viewModel  内部来自activity 泛型的 viewmodel
     */
    private fun createInsideViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     *创建viewmodel
     */
    inline fun <reified VM2:BaseViewModel>createViewModel(): VM2{
         val vm: VM2 by viewModels()
        registerUiChange(vm) //共享vm时需要把UI注册事件再加一遍
        return vm
    }


    /**
     * 注册 UI 事件
     */
    fun registerUiChange(viewModel: BaseViewModel) {
        //显示弹窗
        viewModel.loadingChange.showDialog.observe(this, Observer {
            showLoading(if (it.isEmpty()) {
                "请求网络中..."
            } else it)
        })
        //关闭弹窗
        viewModel.loadingChange.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.loadingChange.status.observe(this, Observer {
            onStatusChanged(it)
        })
        viewModel.loadingChange.errorMsg.observe(this, Observer {
            if(it!=null){
                onError(it)
            }
            dismissLoading()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() // CoroutineScope.cancel
    }


}