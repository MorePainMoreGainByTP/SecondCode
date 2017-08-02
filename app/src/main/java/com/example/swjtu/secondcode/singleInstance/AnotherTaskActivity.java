package com.example.swjtu.secondcode.singleInstance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.swjtu.secondcode.BaseActivity;
import com.example.swjtu.secondcode.R;

/**
 * Created by tangpeng on 2017/4/17.
 */

public class AnotherTaskActivity extends BaseActivity {

    private static final String TAG = "AnotherTaskActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_task);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: AnotherTaskActivity");
        Log.i(TAG, "AnotherTaskActivity: 比 MainActivity还要晚被销毁");
        //当MainActivity所在栈为空时，才会去显示AnotherTaskActivity所在栈的内容
    }

    public void goMiddle(View v){
        startActivity(new Intent(this,MiddleActivity.class));
    }
}
