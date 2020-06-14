package com.linkaipeng.oknetworkmonitor.ui.trace

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.linkaipeng.oknetworkmonitor.R
import com.linkaipeng.oknetworkmonitor.utils.Utils

/**
 * Created by linkaipeng on 2020/6/4.
 *
 */
class TraceView(context: Context?, attr: AttributeSet?): LinearLayout(context, attr) {

    init {
        orientation = VERTICAL
    }

    fun addTraceDetail(traceDetailList: LinkedHashMap<String, Long>) {
        traceDetailList.keys.mapIndexed { index, name ->
            addTraceItem(name, traceDetailList[name], index == traceDetailList.size - 1)
        }
    }

    private fun addTraceItem(name: String?, costTime: Long?, isLast: Boolean) {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_trace_view, null, false)
        val pointView: View = itemView.findViewById(R.id.view_trace_point_view)
        val traceTextView: TextView = itemView.findViewById(R.id.view_trace_item_text_view)
        val lineView: View = itemView.findViewById(R.id.view_trace_line_view)

        traceTextView.text = "$name: $costTime ms"
        if (isLast) {
            lineView.visibility = View.GONE
        } else {
            lineView.visibility = View.VISIBLE
        }

        if (!name.isNullOrEmpty() && Utils.isTimeout(context, name, costTime)) {
            pointView.setBackgroundResource(R.drawable.red_trace)
            traceTextView.setTextColor(ResourcesCompat.getColor(resources,
                    R.color.red, null))
        } else {
            pointView.setBackgroundResource(R.drawable.green_trace)
            traceTextView.setTextColor(ResourcesCompat.getColor(resources,
                    R.color.trace_normal, null))
        }

        addView(itemView)
    }
}