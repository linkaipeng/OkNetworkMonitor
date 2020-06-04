package com.linkaipeng.oknetworkmonitor.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import android.text.TextUtils
import android.widget.Toast
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel
import com.linkaipeng.oknetworkmonitor.data.NetworkTraceModel


class Utils {

    companion object {
        fun copyToClipBoard(context: Context, content: String?) {
            if (!TextUtils.isEmpty(content)) {
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("", content)
                clipboardManager.primaryClip = clipData
                Toast.makeText(context, "Copy succeed.", Toast.LENGTH_SHORT).show()
            }
        }

        fun transformToTraceDetail(eventsTimeMap: MutableMap<String, Long>): LinkedHashMap<String, Long> {
            val traceDetailList = linkedMapOf<String, Long>()
            traceDetailList["Total Time"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.CALL_START, NetworkTraceModel.CALL_END)
            traceDetailList["DNS"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.DNS_START, NetworkTraceModel.DNS_END)
            traceDetailList["Secure Connect"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.SECURE_CONNECT_START, NetworkTraceModel.SECURE_CONNECT_END)
            traceDetailList["Connect"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.CONNECT_START, NetworkTraceModel.CONNECT_END)
            traceDetailList["Request Headers"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.REQUEST_HEADERS_START, NetworkTraceModel.REQUEST_HEADERS_END)
            traceDetailList["Request Body"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.REQUEST_BODY_START, NetworkTraceModel.REQUEST_BODY_END)
            traceDetailList["Response Headers"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.RESPONSE_HEADERS_START, NetworkTraceModel.RESPONSE_HEADERS_END)
            traceDetailList["Response Body"] = getEventCostTime(eventsTimeMap, NetworkTraceModel.RESPONSE_BODY_START, NetworkTraceModel.RESPONSE_BODY_END)
            return traceDetailList
        }

        private fun getEventCostTime(
                eventsTimeMap: MutableMap<String, Long>,
                startName: String,
                endName: String
        ): Long {
            if (!eventsTimeMap.containsKey(startName) || !eventsTimeMap.containsKey(endName)) {
                return 0
            }
            return eventsTimeMap[endName]!! - eventsTimeMap[startName]!!
        }

    }
}