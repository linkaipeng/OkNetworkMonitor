package com.linkaipeng.oknetworkmonitor;

import com.linkaipeng.oknetworkmonitor.reporter.OkNetworkMonitorInterceptor;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by linkaipeng on 2018/4/28.
 */

public class OkHttpManager {

    private static final String TAG = "OkHttpManager";
    private static OkHttpManager sInstance;

    public static OkHttpManager getInstance() {
        if (sInstance == null) {
            sInstance = new OkHttpManager();
        }
        return sInstance;
    }

    public void get(String url, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new OkNetworkMonitorInterceptor())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
