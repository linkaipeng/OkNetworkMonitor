package com.linkaipeng.oknetworkmonitor.reporter;

import android.util.Log;

import com.facebook.stetho.okhttp3.stetho.NetworkEventReporter;
import com.facebook.stetho.okhttp3.stetho.ResponseHandler;
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;
import com.linkaipeng.oknetworkmonitor.utils.DataTranslator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

/**
 * Created by linkaipeng on 2018/4/28.
 */

public class OkNetworkReporterImpl implements NetworkEventReporter {

    private static final String TAG = "OkNetworkReporterImpl";
    private final AtomicInteger mNextRequestId = new AtomicInteger(0);
    private Map<String, Long> mStartTimeMap = new HashMap();

    private static OkNetworkReporterImpl sInstance;

    public static OkNetworkReporterImpl getInstance() {
        if (sInstance == null) {
            sInstance = new OkNetworkReporterImpl();
        }
        return sInstance;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void requestWillBeSent(InspectorRequest request) {
        Log.d(TAG, "requestWillBeSent");
        StringBuilder requestBuilder = new StringBuilder()
                .append("url = ").append(request.url())
                .append("\nmethod = ").append(request.method())
                .append("\nheaderCount = ").append(request.headerCount())
                .append("\nfriendlyName = ").append(request.friendlyName());
        Log.d(TAG, "requestBuilder = "+requestBuilder.toString());

        DataTranslator.saveInspectorRequest(request);
        mStartTimeMap.put(request.id(), System.currentTimeMillis());
    }

    @Override
    public void responseHeadersReceived(InspectorResponse response) {
        Log.d(TAG, "responseHeadersReceived");
        String requestId = response.requestId();
        long costTime;
        if (mStartTimeMap.containsKey(requestId)) {
            costTime = System.currentTimeMillis() - mStartTimeMap.get(requestId);
            Log.d(TAG, "cost time = " + costTime + "ms");
        } else {
            costTime = -1;
        }
        NetworkFeedModel networkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        networkFeedModel.setCostTime(costTime);
        networkFeedModel.setStatus(response.statusCode());
    }

    @Override
    public void httpExchangeFailed(String requestId, String errorText) {
        Log.d(TAG, "httpExchangeFailed");
    }

    @Nullable
    @Override
    public InputStream interpretResponseStream(String requestId, @Nullable String contentType, @Nullable String contentEncoding, @Nullable InputStream inputStream, ResponseHandler responseHandler) {
        Log.d(TAG, "interpretResponseStream");
        NetworkFeedModel networkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        networkFeedModel.setContentType(contentType);
        ByteArrayOutputStream byteArrayOutputStream = DataTranslator.parseAndSaveBody(inputStream, networkFeedModel);
        InputStream newInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, TAG, e);
        }
        return newInputStream;
    }

    @Override
    public void responseReadFailed(String requestId, String errorText) {
        Log.d(TAG, "responseReadFailed");
    }

    @Override
    public void responseReadFinished(String requestId) {
        Log.d(TAG, "responseReadFinished");
    }

    @Override
    public void dataSent(String requestId, int dataLength, int encodedDataLength) {
        Log.d(TAG, "dataSent");
    }

    @Override
    public void dataReceived(String requestId, int dataLength, int encodedDataLength) {
        Log.d(TAG, "dataReceived");
    }

    @Override
    public String nextRequestId() {
        Log.d(TAG, "nextRequestId");
        return String.valueOf(mNextRequestId.getAndIncrement());
    }

    @Override
    public void webSocketCreated(String requestId, String url) {
        Log.d(TAG, "webSocketCreated");

    }

    @Override
    public void webSocketClosed(String requestId) {
        Log.d(TAG, "webSocketClosed");

    }

    @Override
    public void webSocketWillSendHandshakeRequest(InspectorWebSocketRequest request) {
        Log.d(TAG, "webSocketWillSendHandshakeRequest");
    }

    @Override
    public void webSocketHandshakeResponseReceived(InspectorWebSocketResponse response) {
        Log.d(TAG, "webSocketHandshakeResponseReceived");
    }

    @Override
    public void webSocketFrameSent(InspectorWebSocketFrame frame) {
        Log.d(TAG, "webSocketFrameSent");
    }

    @Override
    public void webSocketFrameReceived(InspectorWebSocketFrame frame) {
        Log.d(TAG, "webSocketFrameReceived");
    }

    @Override
    public void webSocketFrameError(String requestId, String errorMessage) {
        Log.d(TAG, "webSocketFrameError");
    }
}
