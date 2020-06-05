package com.linkaipeng.oknetworkmonitor.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
        supportActionBar?.title = "Requests"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initTabLayoutAndViewpager2()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.requests, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.requests_settings -> {
                SettingsActivity.starter(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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