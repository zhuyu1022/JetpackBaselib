package com.leqi.zhuyudemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mAdapter:ListAdapter by lazy { ListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.apply {
            adapter=mAdapter
            val list= mutableListOf<String >()
            list.add("折叠布局")
            list.add("折叠布局2")
            mAdapter.setList(list)
            mAdapter.setOnItemClickListener { _, _, position ->
                when(position){
                    0->{
                        startActivity(Intent(this@MainActivity,CoordinatorLayoutActivity::class.java))
                    }
                    1->{
                        startActivity(Intent(this@MainActivity,CoordinatorLayoutActivity2::class.java))
                    }

                }


            }
        }
    }


    class ListAdapter:BaseQuickAdapter<String ,BaseViewHolder>(R.layout.item_layout){
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.titleTv,item)
        }

    }

}