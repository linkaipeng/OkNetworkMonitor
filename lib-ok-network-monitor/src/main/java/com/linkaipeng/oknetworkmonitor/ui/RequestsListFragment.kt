package com.linkaipeng.oknetworkmonitor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkaipeng.oknetworkmonitor.R

/**
 * Created by linkaipeng on 2020/6/4.
 *
 */
class RequestsListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_requests_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestsRecyclerView: RecyclerView = view.findViewById(R.id.requests_recyclerView)
        requestsRecyclerView.layoutManager = LinearLayoutManager(activity)
        requestsRecyclerView.adapter = NetworkFeedAdapter(activity)
    }
}