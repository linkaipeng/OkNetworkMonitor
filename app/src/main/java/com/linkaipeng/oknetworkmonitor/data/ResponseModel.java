package com.linkaipeng.oknetworkmonitor.data;

import java.io.Serializable;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class ResponseModel implements Serializable {

    private String mName;
    private int mStatus;
    private int mSize;
    private long mTime;
    private String mContentType;
    private String mBody;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "mName='" + mName + '\'' +
                ", mStatus=" + mStatus +
                ", mSize=" + mSize +
                ", mTime=" + mTime +
                ", mContentType='" + mContentType + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }
}
