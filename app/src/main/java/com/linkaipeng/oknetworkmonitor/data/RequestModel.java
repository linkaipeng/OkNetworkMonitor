package com.linkaipeng.oknetworkmonitor.data;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class RequestModel implements Serializable {

    private String mUrl;
    private String mHost;
    private String mMethod;
    private Map<String, String> mHeadersMap;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        mHost = host;
    }

    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String method) {
        mMethod = method;
    }

    public Map<String, String> getHeadersMap() {
        return mHeadersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        mHeadersMap = headersMap;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "mUrl='" + mUrl + '\'' +
                ", mHost='" + mHost + '\'' +
                ", mMethod='" + mMethod + '\'' +
                ", mHeadersMap=" + mHeadersMap +
                '}';
    }
}
