package com.example.swjtu.secondcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by tangpeng on 2017/4/17.
 */

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    //启动Activity的最佳写法，针对需要传递参数的activity。通过此方法启动一个activity可以清晰地知道本activity所需要参数个数与类型，而且内部的细节被封装
    //其他activity只需要调用方法传递参数即可，而不管参数是如果使用的
    public static void actionStart(Context context, String name, int age) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        context.startActivity(intent);
    }
}
