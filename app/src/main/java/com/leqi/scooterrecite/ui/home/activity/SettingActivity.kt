package com.leqi.scooterrecite.ui.home.activity

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.*
import com.leqi.scooterrecite.BuildConfig
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseActivity
import com.leqi.scooterrecite.config.Config
import com.leqi.scooterrecite.ui.common.dialog.CustomDialog
import com.leqi.scooterrecite.ui.home.viewmodel.SettingViewModel
import com.leqi.scooterrecite.util.APKUtil
import com.leqi.scooterrecite.util.APKUtil.Companion.chooseMarket
import com.lxj.xpopup.XPopup


import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : BaseActivity<SettingViewModel>() {


    override fun layoutId(): Int = R.layout.activity_setting
    override fun initView(savedInstanceState: Bundle?) {

        versionTv.text = AppUtils.getAppVersionName()

    }


    override fun initEvent() {
        //检查更新
        checkUpdateLayout.setOnClickListener {

        }

        //清空缓存
        clearCacheLayout.setOnClickListener {
            clearCache()
        }

        //登录
        loginBtn.setOnClickListener {
        }
        //退出登录
        logoutBtn.setOnClickListener {
            val dialog=CustomDialog(this,"是否退出登录？","","确定","取消")
            dialog.setClickListener(object : CustomDialog.CustomDialogListener{
                override fun onLeftBtnClick() {

                }
                override fun onRightBtnClick() {
                }
            })
            XPopup.Builder(this).asCustom(dialog).show()

        }

    }

    override fun initData() {

    }




    override fun createObserver() {


    }


    override fun onResume() {
        super.onResume()

    }




    /**
     * 版本信息提示框
     */
    private fun showNewVersionDialog(version: Int) {
        if (BuildConfig.VERSION_CODE < version) {


            val dialog = CustomDialog(this,"提示", "应用有更新！", "取消", "确认")
            dialog.setClickListener(object : CustomDialog.CustomDialogListener {
                override fun onLeftBtnClick() {
                }

                override fun onRightBtnClick() {
                    gotoUpdateOrScore()
                }
            })
           XPopup.Builder(this).asCustom(dialog).show()
        } else {
            val dialog = CustomDialog(this,"提示", "当前版本是最新版本！", "知道了", "")
            XPopup.Builder(this).asCustom(dialog).show()
        }
    }

    /**
     * 更新应用或评分
     */
    private fun gotoUpdateOrScore() {
        chooseMarket(this, object : APKUtil.ShareToMarket {
            override fun onQQMarketSuccess() {
                //跳转到应用宝市场成功
            }

            override fun onOtherMarketSuccess() {
                //未成功跳转到应用宝市场 检测其他市场可用
            }

            override fun onFailed() {
                ToastUtils.showShort("未找到应用市场")
            }
        })
    }

    private fun clearCache() {
        val clearCacheDialog = CustomDialog(this,"是否清除缓存？", "", "取消", "确认")
        clearCacheDialog.setClickListener(object : CustomDialog.CustomDialogListener {
            override fun onLeftBtnClick() {

            }

            override fun onRightBtnClick() {
                CleanUtils.cleanCustomDir(Config.BASE_FOLDER_NAME)
                CleanUtils.cleanInternalCache()
                CleanUtils.cleanExternalCache()
                ToastUtils.showShort("清除成功！")
            }
        })
        XPopup.Builder(this).asCustom(clearCacheDialog).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}