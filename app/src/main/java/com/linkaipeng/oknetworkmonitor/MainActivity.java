package com.linkaipeng.oknetworkmonitor;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.linkaipeng.oknetworkmonitor.data.DataPoolImpl;
import com.linkaipeng.oknetworkmonitor.data.NetworkFeedModel;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpManager.getInstance().get("http://www.wanandroid.com/article/list/1/json", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "e = "+e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String body = response.body().string();
                        Log.d(TAG, body);
                        new Handler(getMainLooper()).post(()->mTextView.setText(body));
                        print();
                    }
                });
            }
        }.start();
    }

    private void print() {
        DataPoolImpl dataPool = DataPoolImpl.getInstance();
        Map<String, NetworkFeedModel> dataMap = dataPool.getNetworkFeedMap();
        for (String requestId : dataMap.keySet()) {
            NetworkFeedModel feedModel = dataMap.get(requestId);
            String url = feedModel.getUrl();
            Log.d(TAG, "url = " + url);
            Log.d(TAG, "cost time = " + feedModel.getCostTime());
        }
    }
}
