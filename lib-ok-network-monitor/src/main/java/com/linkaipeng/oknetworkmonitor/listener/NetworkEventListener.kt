package com.linkaipeng.oknetworkmonitor.listener

import android.os.SystemClock
import android.util.Log
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl
import com.linkaipeng.oknetworkmonitor.data.NetworkTraceModel
import okhttp3.*
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by linkaipeng on 2020/5/24.
 */
class NetworkEventListener : EventListener() {

    companion object {
        private const val TAG = "NetworkEventListener"
        val FACTORY = object : Factory {
            override fun create(call: Call): EventListener {
                return NetworkEventListener()
            }
        }
        val mNextRequestId = AtomicInteger(0)
    }

    private var mRequestId: String?= null

    override fun callStart(call: Call) {
        super.callStart(call)
        mRequestId = mNextRequestId.getAndIncrement().toString()
        saveEvent(NetworkTraceModel.CALL_START)
        saveUrl(call.request().url.toString())
    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
        Log.d(TAG, "dnsStart")
        saveEvent(NetworkTraceModel.DNS_START)
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        Log.d(TAG, "dnsEnd")
        saveEvent(NetworkTraceModel.DNS_END)
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        Log.d(TAG, "connectStart")
        saveEvent(NetworkTraceModel.CONNECT_START)
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
        Log.d(TAG, "secureConnectStart")
        saveEvent(NetworkTraceModel.SECURE_CONNECT_START)
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
        Log.d(TAG, "secureConnectEnd")
        saveEvent(NetworkTraceModel.SECURE_CONNECT_END)
    }

    override fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        Log.d(TAG, "connectEnd")
        saveEvent(NetworkTraceModel.CONNECT_END)
    }

    override fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?, ioe: IOException) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        Log.d(TAG, "connectFailed")
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
        Log.d(TAG, "requestHeadersStart")
        saveEvent(NetworkTraceModel.REQUEST_HEADERS_START)
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
        Log.d(TAG, "requestHeadersEnd")
        saveEvent(NetworkTraceModel.REQUEST_HEADERS_END)
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
        Log.d(TAG, "requestBodyStart")
        saveEvent(NetworkTraceModel.REQUEST_BODY_START)
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
        Log.d(TAG, "requestBodyEnd")
        saveEvent(NetworkTraceModel.REQUEST_BODY_END)
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
        Log.d(TAG, "responseHeadersStart")
        saveEvent(NetworkTraceModel.RESPONSE_HEADERS_START)
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
        Log.d(TAG, "responseHeadersEnd")
        saveEvent(NetworkTraceModel.RESPONSE_HEADERS_END)
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
        Log.d(TAG, "responseBodyStart")
        saveEvent(NetworkTraceModel.RESPONSE_BODY_START)
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
        Log.d(TAG, "responseBodyEnd")
        saveEvent(NetworkTraceModel.RESPONSE_BODY_END)
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        Log.d(TAG, "callEnd")
        saveEvent(NetworkTraceModel.CALL_END)
    }

    override fun callFailed(call: Call, ioe: IOException) {
        super.callFailed(call, ioe)
        Log.d(TAG, "callFailed")
    }

    private fun saveEvent(eventName: String) {
        val traceModel = DataPoolImpl.getInstance().getNetworkTraceModel(mRequestId)
        traceModel.networkEventsMap[eventName] = SystemClock.elapsedRealtime()
    }

    private fun saveUrl(url: String?) {
        val traceModel = DataPoolImpl.getInstance().getNetworkTraceModel(mRequestId)
        traceModel.url = url
    }
}