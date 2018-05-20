package com.linkaipeng.oknetworkmonitor.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.linkaipeng.oknetworkmonitor.OkHttpManager;
import com.linkaipeng.oknetworkmonitor.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by linkaipeng on 2018/5/20.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test_button).setOnClickListener(view -> NetworkFeedActivity.start(this));
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
                    }
                });
            }
        }.start();
    }
}
