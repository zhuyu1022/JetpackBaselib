package com.leqi.zhuyudemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.leqi.zhuyudemo.adapter.SimpleAdapter
import kotlinx.android.synthetic.main.activity_bottom_demo.*


/**
  * @Description:    流式布局流畅性调试
  * @Author:         ZHUYU
  * @CreateDate:     2020/9/8 13:39
 */
class RecyclerviewNestingDemo : AppCompatActivity() {

    val mAdapter by lazy{ SimpleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_nesting_demo)
        recyclerView.apply {
            adapter=mAdapter
            val list= mutableListOf<String >()
            repeat(1000){
                list.add("大")
                list.add("小")
                list.add("魁")
                list.add("天")
                list.add("传")
                list.add("人")
                list.add("中")
                list.add("国")
                list.add("万")
                list.add("岁")
                list.add("一")

            }
            mAdapter.setList(list)
        }
    }



}