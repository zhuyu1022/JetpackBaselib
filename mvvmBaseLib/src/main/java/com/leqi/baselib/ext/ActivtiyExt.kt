package com.leqi.baselib.ext

import com.leqi.baselib.base.activity.BaseVmActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun BaseVmActivity<*>.launchTryException(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
    launch (Dispatchers.Main){
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}
