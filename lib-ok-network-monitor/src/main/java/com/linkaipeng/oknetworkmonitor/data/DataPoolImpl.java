package com.linkaipeng.oknetworkmonitor.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linkaipeng on 2018/5/17.
 */

public class DataPoolImpl implements IDataPoolHandle {

    private static DataPoolImpl sDataPoolImpl;
    private HashMap<String, NetworkFeedModel> mNetworkFeedMap;
    private Map<String, NetworkTraceModel> mTraceModelMap;

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
            mNetworkFeedMap = new HashMap();
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

    @Override
    public HashMap<String, NetworkFeedModel> getNetworkFeedMap() {
        return mNetworkFeedMap;
    }

    @Override
    public NetworkFeedModel getNetworkFeedModel(String requestId) {
        if (mNetworkFeedMap == null) {
            initDataPool();
        }
        NetworkFeedModel networkFeedModel = mNetworkFeedMap.get(requestId);
        if (networkFeedModel == null) {
            networkFeedModel = new NetworkFeedModel();
            networkFeedModel.setRequestId(requestId);
            networkFeedModel.setCreateTime(System.currentTimeMillis());
            mNetworkFeedMap.put(requestId, networkFeedModel);
        }
        return networkFeedModel;
    }

    @Override
    public NetworkTraceModel getNetworkTraceModel(String id) {
        if (mTraceModelMap == null) {
            mTraceModelMap = new HashMap<>();
        }
        if (mTraceModelMap.containsKey(id)) {
            return mTraceModelMap.get(id);
        } else {
            NetworkTraceModel traceModel = new NetworkTraceModel();
            traceModel.setId(id);
            traceModel.setTime(System.currentTimeMillis());
            mTraceModelMap.put(id, traceModel);
            return traceModel;
        }
    }

    public Map<String, NetworkTraceModel> getTraceModelMap() {
        return mTraceModelMap;
    }
}
