package com.leqi.baselib.base.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


abstract class BaseEasyDialogFragment : DialogFragment() {

    abstract fun layoutId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCanceledOnTouchOutside(false)
        val bundle = arguments
        if (bundle != null) {
            initArguments(bundle)
        }
        init(view)
    }

    fun init(view: View) {
        initView(view)
    }


     abstract fun initArguments(bundle: Bundle)

     abstract fun initView(view: View)


    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }


    /**
     * 与 show方法对应，避免bug
     * zhuyu
     * 2019年3月13日添加，如有问题，直接删除该方法
     */
    override fun dismiss() {
        if (activity != null && !activity!!.isFinishing) {
            super.dismissAllowingStateLoss()
        }
    }


}
