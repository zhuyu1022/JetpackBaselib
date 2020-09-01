package com.leqi.zhuyudemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.BarUtils

class CoordinatorLayoutActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinator_layout2)
        BarUtils.setStatusBarColor(this,ContextCompat.getColor(this,R.color.tran))
    }

}