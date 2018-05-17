package com.linkaipeng.oknetworkmonitor.data;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public interface IDataPoolHandle {
    void initDataPool();
    void clearDataPool();
    void addNetworkFeedData(String key, NetworkFeedModel networkFeedModel);
    void removeNetworkFeedData(String key);
}
