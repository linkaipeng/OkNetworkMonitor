package com.linkaipeng.oknetworkmonitor.data

import java.io.Serializable

/**
 * Created by linkaipeng on 2020/6/4.
 *
 */
class NetworkTraceModel: Serializable {

    companion object {
        const val CALL_START = "callStart"
        const val CALL_END = "callEnd"

        const val DNS_START = "dnsStart"
        const val DNS_END = "dnsEnd"
        const val CONNECT_START = "connectStart"
        const val SECURE_CONNECT_START = "secureConnectStart"
        const val SECURE_CONNECT_END = "secureConnectEnd"
        const val CONNECT_END = "connectEnd"

        const val REQUEST_BODY_START = "requestBodyStart"
        const val REQUEST_BODY_END = "requestBodyEnd"
        const val REQUEST_HEADERS_START = "requestHeadersStart"
        const val REQUEST_HEADERS_END = "requestHeadersEnd"

        const val RESPONSE_HEADERS_START = "responseHeadersStart"
        const val RESPONSE_HEADERS_END = "responseHeadersEnd"
        const val RESPONSE_BODY_START = "responseBodyStart"
        const val RESPONSE_BODY_END = "responseBodyEnd"

        const val TRACE_NAME_TOTAL = "Total Time"
        const val TRACE_NAME_DNS = "DNS"
        const val TRACE_NAME_SECURE_CONNECT = "Secure Connect"
        const val TRACE_NAME_CONNECT = "Connect"
        const val TRACE_NAME_REQUEST_HEADERS = "Request Headers"
        const val TRACE_NAME_REQUEST_BODY = "Request Body"
        const val TRACE_NAME_RESPONSE_HEADERS = "Response Headers"
        const val TRACE_NAME_RESPONSE_BODY = "Response Body"
    }

    var id: String?= null
    var url: String?= null
    var time: Long?= null
    val networkEventsMap: MutableMap<String, Long> = mutableMapOf()

    val traceItemList: MutableMap<String, Long> = linkedMapOf()

}