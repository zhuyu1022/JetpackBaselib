package com.leqi.scooterrecite.util

import com.blankj.utilcode.util.ToastUtils
import java.util.ArrayList

/**
 * 判断是否包含指定文本
 */
 fun String.containStr(s: ArrayList<String>): Boolean {
    var flag = false
    for (str in s) {
        if (this.contains(str)) {
            flag = true
            break
        } else {
            flag = false
        }
    }
    return flag
}


fun String.toast() {
   ToastUtils.showShort(this)
}