package com.linkaipeng.oknetworkmonitor.utils

import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.linkaipeng.oknetworkmonitor.OkNetworkMonitor
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl
import com.linkaipeng.oknetworkmonitor.data.NetworkTraceModel
import com.linkaipeng.oknetworkmonitor.notification.NotificationDispatcher
import com.linkaipeng.oknetworkmonitor.ui.RequestsActivity
import java.util.concurrent.atomic.AtomicInteger


class Utils {

    companion object {

        private val notificationId = AtomicInteger(0)

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
            traceDetailList[NetworkTraceModel.TRACE_NAME_TOTAL] = getEventCostTime(eventsTimeMap, NetworkTraceModel.CALL_START, NetworkTraceModel.CALL_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_DNS] = getEventCostTime(eventsTimeMap, NetworkTraceModel.DNS_START, NetworkTraceModel.DNS_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_SECURE_CONNECT] = getEventCostTime(eventsTimeMap, NetworkTraceModel.SECURE_CONNECT_START, NetworkTraceModel.SECURE_CONNECT_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_CONNECT] = getEventCostTime(eventsTimeMap, NetworkTraceModel.CONNECT_START, NetworkTraceModel.CONNECT_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_REQUEST_HEADERS] = getEventCostTime(eventsTimeMap, NetworkTraceModel.REQUEST_HEADERS_START, NetworkTraceModel.REQUEST_HEADERS_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_REQUEST_BODY] = getEventCostTime(eventsTimeMap, NetworkTraceModel.REQUEST_BODY_START, NetworkTraceModel.REQUEST_BODY_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_RESPONSE_HEADERS] = getEventCostTime(eventsTimeMap, NetworkTraceModel.RESPONSE_HEADERS_START, NetworkTraceModel.RESPONSE_HEADERS_END)
            traceDetailList[NetworkTraceModel.TRACE_NAME_RESPONSE_BODY] = getEventCostTime(eventsTimeMap, NetworkTraceModel.RESPONSE_BODY_START, NetworkTraceModel.RESPONSE_BODY_END)
            return traceDetailList
        }

        fun getEventCostTime(
                eventsTimeMap: MutableMap<String, Long>,
                startName: String,
                endName: String
        ): Long {
            if (!eventsTimeMap.containsKey(startName) || !eventsTimeMap.containsKey(endName)) {
                return 0
            }
            return eventsTimeMap[endName]!! - eventsTimeMap[startName]!!
        }


        fun timeoutChecker(requestId: String?) {
            if (requestId.isNullOrEmpty()) {
                return
            }
            val networkTraceModel = DataPoolImpl.getInstance().getNetworkTraceModel(requestId)


            val traceItemList = networkTraceModel.traceItemList
            val url = networkTraceModel.url

            check(NetworkTraceModel.TRACE_NAME_TOTAL, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_DNS, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_SECURE_CONNECT, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_CONNECT, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_REQUEST_HEADERS, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_REQUEST_BODY, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_RESPONSE_HEADERS, url, traceItemList)
            check(NetworkTraceModel.TRACE_NAME_RESPONSE_BODY, url, traceItemList)
        }

        private fun check(key: String, url: String?, traceItemList: MutableMap<String, Long>) {
            val context = OkNetworkMonitor.context
            if (context == null) {
                Log.d("OkNetworkMonitor", "context is null.")
                return
            }

            val costTime = traceItemList[key]
            if (isTimeout(context, key, costTime)) {
                val title = "Timeout warning. $key cost ${costTime}ms."
                val content = "url: $url"
                val intent = Intent(context, RequestsActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                NotificationDispatcher.showNotification(context, title, content,
                        pendingIntent, notificationId.getAndIncrement())
            }
        }

        fun isTimeout(context: Context, key: String, costTime: Long?): Boolean {
            val value = getSettingTimeout(context, key)
            if (value <= 0 || costTime == null) {
                return false
            }
            return costTime > value
        }

        private fun getSettingTimeout(context: Context, key: String): Int {
            return try {
                PreferenceManager.getDefaultSharedPreferences(context).getString(key, "0").toInt()
            } catch (e: Exception) {
                0
            }
        }
    }
}