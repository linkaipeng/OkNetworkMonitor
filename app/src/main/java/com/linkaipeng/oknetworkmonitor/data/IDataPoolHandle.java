package com.linkaipeng.oknetworkmonitor.data;

import java.util.WeakHashMap;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public interface IDataPoolHandle {
    void initDataPool();
    void clearDataPool();
    void addNetworkFeedData(String key, NetworkFeedModel networkFeedModel);
    void removeNetworkFeedData(String key);
    WeakHashMap<String, NetworkFeedModel> getNetworkFeedMap();
    NetworkFeedModel getNetworkFeedModel(String requestId);
}
