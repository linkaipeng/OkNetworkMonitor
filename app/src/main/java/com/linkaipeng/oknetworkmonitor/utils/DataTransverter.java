package com.linkaipeng.oknetworkmonitor.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.stetho.NetworkEventReporter;
import com.linkaipeng.oknetworkmonitor.data.RequestModel;
import com.linkaipeng.oknetworkmonitor.data.ResponseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class DataTransverter {

    public static RequestModel convertInspectorRequestToModel(NetworkEventReporter.InspectorRequest request) {
        RequestModel requestModel = new RequestModel();
        String url = request.url();
        if (!TextUtils.isEmpty(url)) {
            String host = Uri.parse(request.url()).getHost();
            requestModel.setHost(host);
            requestModel.setUrl(url);
        }
        requestModel.setMethod(request.method());

        Map<String, String> headersMap = new HashMap();
        for (int i = 0, count = request.headerCount(); i < count; i++) {
            String name = request.headerName(i);
            String value = request.headerValue(i);
            headersMap.put(name, value);
        }
        requestModel.setHeadersMap(headersMap);
        return requestModel;
    }

    public static ResponseModel convertInspectorResponseToModel(NetworkEventReporter.InspectorResponse response, long costTime) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setName(response.url());
        responseModel.setTime(costTime);
        return responseModel;
    }
}
