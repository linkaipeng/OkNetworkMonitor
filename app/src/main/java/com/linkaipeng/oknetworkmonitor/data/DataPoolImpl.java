package com.linkaipeng.oknetworkmonitor.data;

import java.util.WeakHashMap;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class DataPoolImpl implements IDataPoolHandle {

    private static DataPoolImpl sDataPoolImpl;
    private WeakHashMap<String, NetworkFeedModel> mNetworkFeedMap;

    public static DataPoolImpl getInstance() {
        if (sDataPoolImpl == null) {
            sDataPoolImpl = new DataPoolImpl();
        }
        return sDataPoolImpl;
    }

    private DataPoolImpl() {
        initDataPool();
    }

    @Override
    public void initDataPool() {
        if (mNetworkFeedMap == null) {
            mNetworkFeedMap = new WeakHashMap();
        }
        mNetworkFeedMap.clear();
    }

    @Override
    public void clearDataPool() {
        if (mNetworkFeedMap != null) {
            mNetworkFeedMap.clear();
        }
    }

    @Override
    public void addNetworkFeedData(String key, NetworkFeedModel networkFeedModel) {
        if (mNetworkFeedMap == null) {
            initDataPool();
        }
        mNetworkFeedMap.put(key, networkFeedModel);
    }

    @Override
    public void removeNetworkFeedData(String key) {
        if (mNetworkFeedMap == null) {
            initDataPool();
            return;
        }
        if (mNetworkFeedMap.containsKey(key)) {
            mNetworkFeedMap.remove(key);
        }
    }
}
