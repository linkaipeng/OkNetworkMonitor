package com.linkaipeng.oknetworkmonitor.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import android.text.TextUtils
import android.widget.Toast
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel


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

        fun appendEvent(eventsBuilder: StringBuilder,
                        eventsTimeMap: MutableMap<String, Long>,
                        eventName: String,
                        startName: String,
                        endName: String) {
            if (!eventsTimeMap.containsKey(startName) || !eventsTimeMap.containsKey(endName)) {
                return
            }
            eventsBuilder.append("${eventName}: ${eventsTimeMap[endName]!! - eventsTimeMap[startName]!!}ms\n")
        }
    }
}