package com.leqi.scooterrecite.ui.common.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.leqi.scooterrecite.R
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.common_custom_dialog_layout.view.*


@SuppressLint("ViewConstructor")
class CustomDialog(context: Context, title: String, message: String, leftMsg: String, rightMsg: String) : CenterPopupView(context) {


    private var title: String? = title
    private var message: String? = message
    private var rightMsg: String? = rightMsg
    private var leftMsg: String? = leftMsg
    private var customDialogListener: CustomDialogListener? = null


    override fun getImplLayoutId(): Int= R.layout.common_custom_dialog_layout


    override fun onCreate() {
        super.onCreate()
        titleTv.text = title
        messageTV.text = message
        rightBtn.text = rightMsg
        leftBtn.text = leftMsg
        if (TextUtils.isEmpty(title)) {
            titleTv.visibility = View.GONE
        }
        if (TextUtils.isEmpty(message)) {
            messageTV.visibility = View.GONE
        }
        if (TextUtils.isEmpty(rightMsg)) {
            rightBtn.visibility = View.GONE
            lineCenter.visibility = View.GONE
        }
        if (TextUtils.isEmpty(leftMsg)) {
            leftBtn.visibility = View.GONE
            lineCenter.visibility = View.GONE
        }
        leftBtn.setOnClickListener {
            dismiss()
            customDialogListener?.onLeftBtnClick()
        }
        rightBtn.setOnClickListener {
            dismiss()
            customDialogListener?.onRightBtnClick()
        }
        //        if (isLeftRed) {
        //            leftBtn.textColor = ActivityCompat.getColor(activity!!, R.color.dialogWarningTextColor)
        //        }
        //        if (isRightRed) {
        //            rightBtn.textColor = ActivityCompat.getColor(activity!!, R.color.dialogWarningTextColor)
        //        }
    }

    interface CustomDialogListener {
        fun onLeftBtnClick()
        fun onRightBtnClick()
    }

    fun setClickListener(customDialogListener: CustomDialogListener) {
        this.customDialogListener = customDialogListener
    }





    var isLeftRed = false
    var isRightRed = false
    fun setLeftBtnRed() {
        isLeftRed = true
    }

    fun setRightBtnRed() {
        isRightRed = true
    }


}