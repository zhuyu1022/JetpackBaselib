package com.leqi.scooterrecite.ui.home.activity


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.tabs.TabLayout
import com.leqi.baselib.state.parseState
import com.leqi.scooterrecite.R
import com.leqi.scooterrecite.base.BaseActivity
import com.leqi.scooterrecite.ui.home.adapter.FragmentStateAdapter
import com.leqi.scooterrecite.ui.home.dialog.CustomerDialog
import com.leqi.scooterrecite.util.ActivityExt.start
import com.leqi.scooterrecite.util.MyTabLayoutMediator
import com.leqi.scooterrecite.util.toast
import com.leqi.scooterrecite.viewmodel.MainViewModel
import com.lxj.xpopup.XPopup
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_slide_left_layout.*


class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun layoutId(): Int = R.layout.activity_main
    override fun initParam() {

    }


    @SuppressLint("RtlHardcoded")
    override fun initView(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent))
        initTabLayout()

        initViewPager()
        initPermission()
    }

    override fun initEvent() {
        slideLeftLayout.setOnClickListener { }
        userHeadImg.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        leftUserNameTv.setOnClickListener {

        }
        chooseTeachingMateriaLayout.setOnClickListener {

        }
        //购买课程
        buyClassesLayout.setOnClickListener {

        }
        //侧滑菜单-设置
        settingLayout.setOnClickListener {
            start<SettingActivity>()
        }
        //侧滑菜单-背诵记录
        reciteRecordLayout.setOnClickListener {

        }
        //客服
        customerLayout.setOnClickListener {
            XPopup.Builder(this).asCustom(CustomerDialog(this)).show()
        }
        //侧滑菜单  分享APP
        shareLayout.setOnClickListener {

        }

    }


    override fun initData() {

    }


    /**
     * 加载数据
     */
    private fun loadData() {
        mViewModel.getHomeArticleList()

    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun createObserver() {

    }

    private fun initPermission() {
        PermissionX.init(this).permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "需要使用相机权限用来拍摄证件照的照片", "同意", "不同意")
            }.onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "需要使用相机权限和文件权限用来制作证件照的照片，请在权限设置勾选页面,勾选相机和存储", "去设置", "取消")
            }.request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                } else {
                }
            }
    }

    private fun initTabLayout() {
        val tabtitleLsit = listOf("热门博文", "热门项目")

        //因为多次调用，这里要先remove掉原来的
        tabLayout.removeAllTabs()
        for (x in tabtitleLsit.indices) {
            val tab: TabLayout.Tab = tabLayout.newTab()
            val view: View = View.inflate(this, R.layout.customtablayout, null)
            val tabNameTv = view.findViewById<TextView>(R.id.tabNameTv)
            val tabImg = view.findViewById<View>(R.id.tabImg)

            tabNameTv.text = tabtitleLsit[x]
            if (x == 0) {
                tabNameTv?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
            }
            tab.customView = view
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    val view = tab.customView
                    val tabNameTv = view?.findViewById<TextView>(R.id.tabNameTv)
                    val tabImg = view?.findViewById<View>(R.id.tabImg)
                    tabNameTv?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.normalLightTextColor))
                    tab.customView = view
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    val view = tab.customView
                    val tabNameTv = view?.findViewById<TextView>(R.id.tabNameTv)
                    val tabImg = view?.findViewById<View>(R.id.tabImg)
                    tabNameTv?.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
                    tab.customView = view

                }
            }
        })
    }


    /**
     * 初始化Viewpager
     */
    private fun initViewPager() {
        //设置viewpager
        viewPager.apply {
            adapter = FragmentStateAdapter(this@MainActivity, true)
            offscreenPageLimit = 3
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {})
            //实现tablayout和viewpager联动 ，用官方的改下源码
            MyTabLayoutMediator(tabLayout, viewPager, object : MyTabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                }
            }).attach()
        }
    }


    //按两次返回键退出app的间隔时间
    private val TIME_EXIT = 2000
    private var mBackPressed: Long = 0

    override fun onBackPressed() {
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            "再按一次退出".toast()
            mBackPressed = System.currentTimeMillis()
        }
    }


}