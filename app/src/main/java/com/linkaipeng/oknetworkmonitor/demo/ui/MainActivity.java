package com.linkaipeng.oknetworkmonitor.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.linkaipeng.oknetworkmonitor.demo.OkHttpManager;
import com.linkaipeng.oknetworkmonitor.demo.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by linkaipeng on 2018/5/20.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mResponseTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResponseTextView = findViewById(R.id.response_textView);
        findViewById(R.id.test_button).setOnClickListener(view -> sendRequest());
        findViewById(R.id.to_feed_list_button).setOnClickListener(view -> NetworkFeedActivity.start(this));
    }

    private void sendRequest() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String testUrl = "https://www.wanandroid.com/article/list/0/json";
//                String testUrl = "https://i.loli.net/2020/05/23/KVIWv5MFyBLJdkY.png";
                OkHttpManager.getInstance().get(testUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "e = "+e.getMessage());
                        runOnUiThread(() -> mResponseTextView.setText(e.getMessage()));
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(() -> {
                            final String body;
                            try {
                                body = response.body().string();
                                Log.d(TAG, body);
                                mResponseTextView.setText(body);
                            } catch (IOException e) {
                                e.printStackTrace();
                                mResponseTextView.setText(e.getMessage());
                            }
                        });

                    }
                });
            }
        }.start();
    }
}
