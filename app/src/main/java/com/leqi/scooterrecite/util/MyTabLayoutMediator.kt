package com.leqi.scooterrecite.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.lang.ref.WeakReference

class MyTabLayoutMediator (private val tabLayout: TabLayout, private val viewPager: ViewPager2, private val autoRefresh: Boolean,
        private val tabConfigurationStrategy: TabConfigurationStrategy) {
        private var adapter: RecyclerView.Adapter<*>? = null
        private var attached = false
        private var onPageChangeCallback: TabLayoutOnPageChangeCallback? = null
        private var onTabSelectedListener: OnTabSelectedListener? = null
        private var pagerAdapterObserver: AdapterDataObserver? = null

        /**
         * A callback interface that must be implemented to set the text and styling of newly created
         * tabs.
         */
        interface TabConfigurationStrategy {
            /**
             * Called to configure the tab for the page at the specified position. Typically calls [ ][TabLayout.Tab.setText], but any form of styling can be applied.
             *
             * @param tab The Tab which should be configured to represent the title of the item at the given
             * position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            fun onConfigureTab(tab: TabLayout.Tab, position: Int)
        }

        constructor(tabLayout: TabLayout, viewPager: ViewPager2, tabConfigurationStrategy: TabConfigurationStrategy) : this(tabLayout, viewPager,
            true, tabConfigurationStrategy) {
        }

        /**
         * Link the TabLayout and the ViewPager2 together. Must be called after ViewPager2 has an adapter
         * set. To be called on a new instance of TabLayoutMediator or if the ViewPager2's adapter
         * changes.
         *
         * @throws IllegalStateException If the mediator is already attached, or the ViewPager2 has no
         * adapter.
         */
        fun attach() {
            check(!attached) { "TabLayoutMediator is already attached" }
            adapter = viewPager.adapter
            checkNotNull(adapter) { "TabLayoutMediator attached before ViewPager2 has an " + "adapter" }
            attached = true

            // Add our custom OnPageChangeCallback to the ViewPager
            onPageChangeCallback = TabLayoutOnPageChangeCallback(tabLayout)
            viewPager.registerOnPageChangeCallback(onPageChangeCallback!!)

            // Now we'll add a tab selected listener to set ViewPager's current item
            onTabSelectedListener = ViewPagerOnTabSelectedListener(viewPager)
            tabLayout.addOnTabSelectedListener(onTabSelectedListener as ViewPagerOnTabSelectedListener)

            // Now we'll populate ourselves from the pager adapter, adding an observer if
            // autoRefresh is enabled
            if (autoRefresh) {
                // Register our observer on the new adapter
                pagerAdapterObserver = PagerAdapterObserver()
                adapter!!.registerAdapterDataObserver(pagerAdapterObserver as PagerAdapterObserver)
            }
          //  populateTabsFromPagerAdapter()

            // Now update the scroll position to match the ViewPager's current item
            tabLayout.setScrollPosition(viewPager.currentItem, 0f, true)
        }

        /**
         * Unlink the TabLayout and the ViewPager. To be called on a stale TabLayoutMediator if a new one
         * is instantiated, to prevent holding on to a view that should be garbage collected. Also to be
         * called before [.attach] when a ViewPager2's adapter is changed.
         */
        fun detach() {
            if (autoRefresh && adapter != null) {
                adapter!!.unregisterAdapterDataObserver(pagerAdapterObserver!!)
                pagerAdapterObserver = null
            }
            tabLayout.removeOnTabSelectedListener(onTabSelectedListener)
            viewPager.unregisterOnPageChangeCallback(onPageChangeCallback!!)
            onTabSelectedListener = null
            onPageChangeCallback = null
            adapter = null
            attached = false
        }

//        fun populateTabsFromPagerAdapter() {
//            tabLayout.removeAllTabs()
//            if (adapter != null) {
//                val adapterCount = adapter!!.itemCount
//                for (i in 0 until adapterCount) {
//                    val tab = tabLayout.newTab()
//                    tabConfigurationStrategy.onConfigureTab(tab, i)
//                    tabLayout.addTab(tab, false)
//                }
//                // Make sure we reflect the currently set ViewPager item
//                if (adapterCount > 0) {
//                    val lastItem = tabLayout.tabCount - 1
//                    val currItem = Math.min(viewPager.currentItem, lastItem)
//                    if (currItem != tabLayout.selectedTabPosition) {
//                        tabLayout.selectTab(tabLayout.getTabAt(currItem))
//                    }
//                }
//            }
//        }

        /**
         * A [ViewPager2.OnPageChangeCallback] class which contains the necessary calls back to the
         * provided [TabLayout] so that the tab position is kept in sync.
         *
         *
         * This class stores the provided TabLayout weakly, meaning that you can use [ ][ViewPager2.registerOnPageChangeCallback] without removing the
         * callback and not cause a leak.
         */
        private class TabLayoutOnPageChangeCallback internal constructor(tabLayout: TabLayout) : OnPageChangeCallback() {
            private val tabLayoutRef: WeakReference<TabLayout>
            private var previousScrollState = 0
            private var scrollState = 0
            override fun onPageScrollStateChanged(state: Int) {
                previousScrollState = scrollState
                scrollState = state
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val tabLayout = tabLayoutRef.get()
                if (tabLayout != null) {
                    // Only update the text selection if we're not settling, or we are settling after
                    // being dragged
                    val updateText = scrollState != ViewPager2.SCROLL_STATE_SETTLING || previousScrollState == ViewPager2.SCROLL_STATE_DRAGGING
                    // Update the indicator if we're not settling after being idle. This is caused
                    // from a setCurrentItem() call and will be handled by an animation from
                    // onPageSelected() instead.
                    val updateIndicator = !(scrollState == ViewPager2.SCROLL_STATE_SETTLING && previousScrollState == ViewPager2.SCROLL_STATE_IDLE)
                    tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator)
                }
            }

            override fun onPageSelected(position: Int) {
                val tabLayout = tabLayoutRef.get()
                if (tabLayout != null && tabLayout.selectedTabPosition != position && position < tabLayout.tabCount) {
                    // Select the tab, only updating the indicator if we're not being dragged/settled
                    // (since onPageScrolled will handle that).
                    val updateIndicator =
                        scrollState == ViewPager2.SCROLL_STATE_IDLE || scrollState == ViewPager2.SCROLL_STATE_SETTLING && previousScrollState == ViewPager2.SCROLL_STATE_IDLE
                    tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator)
                }
            }

            fun reset() {
                scrollState = ViewPager2.SCROLL_STATE_IDLE
                previousScrollState = scrollState
            }

            init {
                tabLayoutRef = WeakReference(tabLayout)
                reset()
            }
        }

        /**
         * A [TabLayout.OnTabSelectedListener] class which contains the necessary calls back to the
         * provided [ViewPager2] so that the tab position is kept in sync.
         */
        private class ViewPagerOnTabSelectedListener internal constructor(private val viewPager: ViewPager2) : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // No-op
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // No-op
            }

        }

        private inner class PagerAdapterObserver internal constructor() : AdapterDataObserver() {
            override fun onChanged() {
               // populateTabsFromPagerAdapter()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
               // populateTabsFromPagerAdapter()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                //populateTabsFromPagerAdapter()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
               // populateTabsFromPagerAdapter()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
               // populateTabsFromPagerAdapter()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
               // populateTabsFromPagerAdapter()
            }
        }


}