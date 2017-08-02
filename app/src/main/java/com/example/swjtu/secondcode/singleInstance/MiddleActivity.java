package com.example.swjtu.secondcode.singleInstance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.swjtu.secondcode.BaseActivity;
import com.example.swjtu.secondcode.R;

/**
 * Created by tangpeng on 2017/4/17.
 */

public class MiddleActivity extends BaseActivity {
    private static final String TAG = "MiddleActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: MiddleActivity");
    }
}
