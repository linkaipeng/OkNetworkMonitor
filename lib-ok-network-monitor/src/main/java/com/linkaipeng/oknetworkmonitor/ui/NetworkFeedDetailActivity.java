package com.linkaipeng.oknetworkmonitor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.linkaipeng.oknetworkmonitor.R;
import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by linkaipeng on 2018/5/20.
 */

public class NetworkFeedDetailActivity extends AppCompatActivity {

    public static final int JSON_INDENT = 4;
    private NetworkFeedModel mNetworkFeedModel;
    private TextView mRequestHeadersTextView;
    private TextView mResponseHeadersTextView;
    private TextView mBodyTextView;
    private View mBackView;

    public static void start(Context context, String requestId) {
        Intent starter = new Intent(context, NetworkFeedDetailActivity.class);
        starter.putExtra("requestId", requestId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_feed_detail);
        mRequestHeadersTextView = findViewById(R.id.request_headers_textView);
        mResponseHeadersTextView = findViewById(R.id.response_headers_textView);
        mBodyTextView = findViewById(R.id.body_textView);
        mBackView = findViewById(R.id.feed_detail_back_layout);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        String requestId = getIntent().getStringExtra("requestId");
        if (TextUtils.isEmpty(requestId)) {
            return;
        }
        mNetworkFeedModel = DataPoolImpl.getInstance().getNetworkFeedModel(requestId);
        if (mNetworkFeedModel == null) {
            return;
        }
        setRequestHeaders();
        setResponseHeaders();
        setBody();
    }

    private void setRequestHeaders() {
        mRequestHeadersTextView.setText(parseHeadersMapToString(mNetworkFeedModel.getRequestHeadersMap()));
    }

    private void setResponseHeaders() {
        mResponseHeadersTextView.setText(parseHeadersMapToString(mNetworkFeedModel.getResponseHeadersMap()));
    }

    private void setBody() {
        if (mNetworkFeedModel.getContentType().contains("json")) {
            mBodyTextView.setText(formatJson(mNetworkFeedModel.getBody()));
        } else {
            mBodyTextView.setText(mNetworkFeedModel.getBody());
        }

    }

    private String parseHeadersMapToString(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return "Header is Empty.";
        }
        StringBuilder headersBuilder = new StringBuilder();
        for (String name : headers.keySet()) {
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            headersBuilder
                    .append(name)
                    .append(": ")
                    .append(headers.get(name))
                    .append("\n");
        }
        return headersBuilder.toString();
    }

    private String formatJson(String body) {
        String message;
        try {
            if (body.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(body);
                message = jsonObject.toString(JSON_INDENT);
            } else if (body.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(body);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = body;
            }
        } catch (JSONException e) {
            message = body;
        }

        return message;
    }
}
