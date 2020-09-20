package com.leqi.scooterrecite.util

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import java.lang.reflect.InvocationTargetException

/**
 * Created by Administrator on 2017/5/27 0027.
 *
 * @see .isInstallAPP
 * @see .isQQMarketInstall
 * @see .chooseMarket
 */
class APKUtil {

    private fun goToSet(context: Context) {
        // 进入设置系统应用权限界面
        val intent = Intent(Settings.ACTION_SETTINGS)
        context.startActivity(intent)
    }

    interface ShareToMarket {
        fun onQQMarketSuccess()
        fun onOtherMarketSuccess()
        fun onFailed()
    }

    companion object {
        /**
         * 检查通知栏是否打开
         *
         * @param context
         * @return
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private fun isNotificationEnabled(context: Context): Boolean {
            val checkOpNoThrow = "checkOpNoThrow"
            val opPost = "OP_POST_NOTIFICATION"
            val mAppOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val appInfo = context.applicationInfo
            val pkg = context.applicationContext.packageName
            val uid = appInfo.uid
            val appOpsClass: Class<*>?
            /* Context.APP_OPS_MANAGER */
            try {
                appOpsClass = Class.forName(AppOpsManager::class.java.name)
                val checkOpNoThrowMethod = appOpsClass!!.getMethod(checkOpNoThrow, Integer.TYPE, Integer.TYPE, String::class.java)
                val opPostNotificationValue = appOpsClass.getDeclaredField(opPost)
                val value = opPostNotificationValue.get(Int::class.java) as Int
                return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return false
        }

        /**
         * 检测是否安装APP   1微信 2微博  3支付宝 4 QQ
         * @param context
         * @return
         */
        fun isInstallAPP(context: Context, status: Int): Boolean {
            val appPackage =
                    when (status) {
                        1 -> "com.tencent.mm"
                        2 -> "com.sina.weibo"
                        3 -> "com.eg.android.AlipayGphone"
                        4 -> "com.tencent.mobileqq"
                        else -> "com.tencent.mm"
                    }
            val packageManager = context.packageManager // 获取packageManager
            val packageInfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
            if (packageInfo != null) {
                for (i in packageInfo.indices) {
                    val pn = packageInfo[i].packageName
                    if (pn == appPackage) {
                        return true
                    }
                }
            }
            return false
        }


        //     com.sina.weibo
        //     com.eg.android.AlipayGphone
        /**
         * 检测是否安装了对应应用市场
         *
         * @param context
         * @param apkMarket
         * @return
         */
        private fun isQQMarketInstall(context: Context, apkMarket: String): Boolean {
            val packageManager = context.packageManager // 获取packageManager
            val packageInfo = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
            packageInfo?.indices?.map { packageInfo[it].packageName }?.filter { it == apkMarket }?.forEach { return true }
            return false
        }

        /**
         * 选择应用市场 去评论
         * @param context
         */
        fun chooseMarket(context: Context, listener: ShareToMarket?) {
            val uri = Uri.parse("market://details?id=" + context.packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val installMarket = "com.tencent.android.qqdownloader"
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            if (isQQMarketInstall(context, installMarket)) {
//                try {
//                    listener?.onQQMarketSuccess()
//                    intent.`package` = installMarket
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    context.startActivity(intent)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//            } else {
            if (intent.resolveActivity(context.packageManager) != null) {
                listener?.onOtherMarketSuccess()
                context.startActivity(Intent.createChooser(intent, "请选择要查看的市场软件"))
            } else {
                listener?.onFailed()
            }
            //  }
        }

    }
}
