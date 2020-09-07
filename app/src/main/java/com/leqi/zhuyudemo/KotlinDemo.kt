package com.leqi.zhuyudemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_kotlin_demo.*

class KotlinDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)

        //textView1.setOnClickListener {
            val str=""
            ToastUtils.showShort(str)
       // }


    }



   interface onclickListener{
        fun onClick()
    }

}