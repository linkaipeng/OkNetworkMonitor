package com.linkaipeng.oknetworkmonitor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linkaipeng.oknetworkmonitor.R;


/**
 * Created by linkaipeng on 2018/5/20.
 */

public class NetworkFeedActivity extends AppCompatActivity {

    private static final String TAG = "NetworkFeedActivity";
    private RecyclerView mNetworkFeedRecyclerView;
    private NetworkFeedAdapter mNetworkFeedAdapter;
    private View mBackView;

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
        mBackView = findViewById(R.id.network_feed_back_layout);
        mNetworkFeedRecyclerView = findViewById(R.id.network_feed_recyclerView);
        mNetworkFeedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNetworkFeedAdapter = new NetworkFeedAdapter(this);
        mNetworkFeedRecyclerView.setAdapter(mNetworkFeedAdapter);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
