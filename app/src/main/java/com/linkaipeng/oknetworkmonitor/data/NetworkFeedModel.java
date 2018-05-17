package com.linkaipeng.oknetworkmonitor.data;

import java.io.Serializable;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class NetworkFeedModel implements Serializable {

    private RequestModel mRequestModel;
    private ResponseModel mResponseModel;

    public RequestModel getRequestModel() {
        return mRequestModel;
    }

    public void setRequestModel(RequestModel requestModel) {
        mRequestModel = requestModel;
    }

    public ResponseModel getResponseModel() {
        return mResponseModel;
    }

    public void setResponseModel(ResponseModel responseModel) {
        mResponseModel = responseModel;
    }
}
