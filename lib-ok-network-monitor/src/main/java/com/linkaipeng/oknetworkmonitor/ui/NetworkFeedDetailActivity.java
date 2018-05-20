package com.linkaipeng.oknetworkmonitor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.stetho.okhttp3.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;

/**
 * Created by linkaipeng on 2018/5/20.
 */

public class NetworkFeedDetailActivity extends AppCompatActivity {

    private TextView mBodyTextView;

    public static void start(Context context, String requestId) {
        Intent starter = new Intent(context, NetworkFeedDetailActivity.class);
        starter.putExtra("requestId", requestId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_feed_detail);
        mBodyTextView = findViewById(R.id.body_textView);
        setBody();
    }

    private void setBody() {
        String requestId = getIntent().getStringExtra("requestId");
        NetworkFeedModel networkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonObject bodyJO = new JsonParser().parse(networkFeedModel.getBody()).getAsJsonObject();
        mBodyTextView.setText(gson.toJson(bodyJO));
    }
}
