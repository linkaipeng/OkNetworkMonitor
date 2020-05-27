package com.linkaipeng.oknetworkmonitor.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import android.text.TextUtils
import android.widget.Toast


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
    }
}