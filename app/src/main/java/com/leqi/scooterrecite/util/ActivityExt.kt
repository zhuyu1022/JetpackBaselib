package com.leqi.scooterrecite.util

import android.app.Activity
import android.content.Intent

object ActivityExt {

    inline fun <reified ACTIVITY : Activity>Activity.start(){
        startActivity(Intent(this,ACTIVITY::class.java))
    }

}