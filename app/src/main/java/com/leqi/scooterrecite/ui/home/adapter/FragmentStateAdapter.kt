package com.leqi.scooterrecite.ui.home.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leqi.scooterrecite.base.BaseFragment
import com.leqi.scooterrecite.ui.home.fragment.GaokaoHotFragment
import com.leqi.scooterrecite.ui.home.fragment.ImportContentFragment
import com.leqi.scooterrecite.ui.home.fragment.HomeArticleListFragment

class FragmentStateAdapter(fragmentActivity: FragmentActivity,isChooseGaozhong:Boolean) : FragmentStateAdapter(fragmentActivity) {

    private val fragments: SparseArray<BaseFragment<*>> = SparseArray()

    init {
        fragments.put(0, HomeArticleListFragment())
        fragments.put(1, ImportContentFragment())
        if (isChooseGaozhong){
            fragments.put(2, GaokaoHotFragment())
        }
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment
        when (position) {
            0 -> {
                if (fragments.get(position) == null) {
                    fragment =HomeArticleListFragment()
                    fragments.put(position, fragment)
                } else {
                    fragment = fragments.get(position)
                }
            }
            1 -> {
                if (fragments.get(position) == null) {
                    fragment = ImportContentFragment()
                    fragments.put(position, fragment)
                } else {
                    fragment = fragments.get(position)
                }
            }

            2 -> {
                if (fragments.get(position) == null) {
                    fragment = GaokaoHotFragment()
                    fragments.put(position, fragment)
                } else {
                    fragment = fragments.get(position)
                }
            }

            else -> {
                if (fragments.get(0) == null) {
                    fragment = HomeArticleListFragment();
                    fragments.put(0, fragment)
                } else {
                    fragment = fragments.get(0)
                }
            }
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

//    companion object {
//
//        const val PAGE_HOME = 0
//
//        const val PAGE_FIND = 1
//
//        const val PAGE_INDICATOR = 2
//
//        const val PAGE_OTHERS = 3
//
//    }

}
