package com.leqi.zhuyudemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.leqi.zhuyudemo.adapter.SimpleAdapter
import kotlinx.android.synthetic.main.activity_bottom_demo.*
import java.lang.reflect.Field


/**
  * @Description:    流式布局流畅性调试
  * @Author:         ZHUYU
  * @CreateDate:     2020/9/8 13:39
 */
class BottomDemo : AppCompatActivity() {

    val mAdapter by lazy{ SimpleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_demo)
        recyclerView.apply {
            adapter=mAdapter
            animation=null
            val list= mutableListOf<String>()
            repeat(10000){
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
        setMaxFlingVelocity(recyclerView,4000)

    }

    //设定RecyclerView最大滑动速度
    private fun setMaxFlingVelocity(recycleview: RecyclerView, velocity: Int) {
        try {
            val field: Field = recycleview.javaClass.getDeclaredField("mMaxFlingVelocity")
            field.setAccessible(true)
            field.set(recycleview, velocity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}