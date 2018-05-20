package com.linkaipeng.oknetworkmonitor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.stetho.okhttp3.R;


/**
 * Created by linkaipeng on 2018/5/20.
 */

public class NetworkFeedActivity extends AppCompatActivity {

    private static final String TAG = "NetworkFeedActivity";
    private RecyclerView mNetworkFeedRecyclerView;
    private NetworkFeedAdapter mNetworkFeedAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkFeedActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_feed);
        initView();
    }

    private void initView() {
        mNetworkFeedRecyclerView = findViewById(R.id.network_feed_recyclerView);
        mNetworkFeedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNetworkFeedAdapter = new NetworkFeedAdapter(this);
        mNetworkFeedRecyclerView.setAdapter(mNetworkFeedAdapter);
    }

}
