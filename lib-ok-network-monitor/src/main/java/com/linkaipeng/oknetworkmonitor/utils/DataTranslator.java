package com.linkaipeng.oknetworkmonitor.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;
import com.linkaipeng.oknetworkmonitor.stetho.NetworkEventReporter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class DataTranslator {

    private static final String TAG = "DataTranslator";
    private static final String GZIP_ENCODING = "gzip";
    private Map<String, Long> mStartTimeMap = new HashMap();


    public void saveInspectorRequest(NetworkEventReporter.InspectorRequest request) {
        String requestId = request.id();
        mStartTimeMap.put(request.id(), System.currentTimeMillis());

        NetworkFeedModel networkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        String url = request.url();
        if (!TextUtils.isEmpty(url)) {
            String host = Uri.parse(url).getHost();
            networkFeedModel.setHost(host);
            networkFeedModel.setUrl(url);
        }
        networkFeedModel.setMethod(request.method());

        Map<String, String> headersMap = new HashMap();
        for (int i = 0, count = request.headerCount(); i < count; i++) {
            String name = request.headerName(i);
            String value = request.headerValue(i);
            headersMap.put(name, value);
        }
        networkFeedModel.setRequestHeadersMap(headersMap);
    }


    public void saveInspectorResponse(NetworkEventReporter.InspectorResponse response) {
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

        Map<String, String> headersMap = new HashMap();
        for (int i = 0, count = response.headerCount(); i < count; i++) {
            String name = response.headerName(i);
            String value = response.headerValue(i);
            headersMap.put(name, value);
        }
        networkFeedModel.setResponseHeadersMap(headersMap);
    }

    public InputStream saveInterpretResponseStream(String requestId, String contentType, String contentEncoding, InputStream inputStream) {
        NetworkFeedModel networkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        networkFeedModel.setContentType(contentType);
        ByteArrayOutputStream byteArrayOutputStream = parseAndSaveBody(inputStream, networkFeedModel, contentEncoding);
        InputStream newInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, TAG, e);
        }
        return newInputStream;
    }

    private ByteArrayOutputStream parseAndSaveBody(InputStream inputStream, NetworkFeedModel networkFeedModel, String contentEncoding) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            InputStream newStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            BufferedReader bufferedReader;
            if (GZIP_ENCODING.equals(contentEncoding)) {
                GZIPInputStream gzipInputStream = new GZIPInputStream(newStream);
                bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(newStream));
            }
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bodyBuilder.append(line + '\n');
            }
            String body = bodyBuilder.toString();
            networkFeedModel.setBody(body);
            networkFeedModel.setSize(body.getBytes().length);
        } catch (IOException e) {
            Log.e(TAG, TAG, e);
        }
        return byteArrayOutputStream;
    }
}
