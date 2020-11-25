package com.ziv.demo.publishbintray;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ziv.develop.utils.LogUtil;
import com.ziv.develop.utils.TimeUtil;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.d(TAG, "onCreate");

        new Thread(new Runnable() {
            @Override
            public void run() {
                long currentTime = TimeUtil.getCurrentTime();
                LogUtil.d(TAG, "currentTime: " + new Date(currentTime));
            }
        }).start();
    }
}
