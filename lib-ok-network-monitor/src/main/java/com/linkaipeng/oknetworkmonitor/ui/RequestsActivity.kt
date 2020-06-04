package com.linkaipeng.oknetworkmonitor.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.linkaipeng.oknetworkmonitor.R
import com.linkaipeng.oknetworkmonitor.ui.trace.RequestTraceFragment

/**
 * Created by linkaipeng on 2020/6/4.
 *
 */
class RequestsActivity: AppCompatActivity() {

    companion object {
        fun starter(context: Context) {
            val intent = Intent(context, RequestsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)
        initToolBar()
        initTabLayoutAndViewpager2()
    }

    private fun initToolBar() {
        val toolBar: Toolbar = findViewById(R.id.requests_tool_bar)
        toolBar.setNavigationOnClickListener { finish() }
    }

    private fun initTabLayoutAndViewpager2() {
        val tabLayout: TabLayout = findViewById(R.id.requests_tab_layout)
        val viewPager: ViewPager2 = findViewById(R.id.requests_view_pager2)
        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Request Content"
                1 -> tab.text = "Request Trace"
            }
        }.attach()
    }

    private class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                RequestsListFragment()
            } else {
                RequestTraceFragment()
            }
        }
    }
}