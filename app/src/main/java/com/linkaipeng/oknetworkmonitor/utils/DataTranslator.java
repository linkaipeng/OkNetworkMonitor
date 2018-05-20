package com.linkaipeng.oknetworkmonitor.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.okhttp3.stetho.NetworkEventReporter;
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class DataTranslator {

    private static final String TAG = "DataTranslator";

    public static void saveInspectorRequest(NetworkEventReporter.InspectorRequest request) {
        String requestId = request.id();
        NetworkFeedModel networkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        String url = request.url();
        if (!TextUtils.isEmpty(url)) {
            String host = Uri.parse(request.url()).getHost();
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
        networkFeedModel.setHeadersMap(headersMap);
    }



    public static ByteArrayOutputStream parseAndSaveBody(InputStream inputStream, NetworkFeedModel networkFeedModel) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1 ) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            InputStream newStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newStream));
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
