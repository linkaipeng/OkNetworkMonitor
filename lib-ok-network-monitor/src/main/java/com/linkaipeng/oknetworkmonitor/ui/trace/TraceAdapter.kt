package com.linkaipeng.oknetworkmonitor.ui.trace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linkaipeng.oknetworkmonitor.R
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl
import com.linkaipeng.oknetworkmonitor.data.NetworkTraceModel
import com.linkaipeng.oknetworkmonitor.utils.Utils

/**
 * Created by linkaipeng on 2020/6/4.
 *
 */
class TraceAdapter(private val context: Context?): RecyclerView.Adapter<TraceAdapter.TraceViewHolder>() {
    
    private val traceList = mutableListOf<NetworkTraceModel>()
    
    init {
        val values = DataPoolImpl.getInstance().traceModelMap?.values
        if (!values.isNullOrEmpty()) {
            traceList.addAll(values)
        }
        traceList.sortByDescending {
            it.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraceViewHolder {
        return TraceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_network_trace, parent, false))
    }

    override fun getItemCount(): Int {
        return traceList.size
    }

    override fun onBindViewHolder(holder: TraceViewHolder, position: Int) {

        val networkTraceModel = traceList[position]
        holder.urlTextView.text = networkTraceModel.url
        holder.traceDetailView.addTraceDetail(Utils.transformToTraceDetail(networkTraceModel.networkEventsMap))
    }

    class TraceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val urlTextView: TextView = itemView.findViewById(R.id.item_trace_url_text_view)
        val traceDetailView: TraceView = itemView.findViewById(R.id.item_trace_detail_view)
    }
}