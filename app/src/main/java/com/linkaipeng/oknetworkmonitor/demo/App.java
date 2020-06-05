package com.linkaipeng.oknetworkmonitor.demo;

import android.app.Application;

import com.linkaipeng.oknetworkmonitor.OkNetworkMonitor;


/**
 * Created by linkaipeng on 2018/4/28.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkNetworkMonitor.INSTANCE.setContext(this);
    }
}
