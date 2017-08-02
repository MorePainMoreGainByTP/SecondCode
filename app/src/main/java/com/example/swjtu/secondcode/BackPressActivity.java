package com.example.swjtu.secondcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by tangpeng on 2017/4/17.
 */

public class BackPressActivity extends BaseActivity {
    private static final String TAG = "BackPressActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpress);
    }

    public void exit(View v){
        Intent intent = new Intent();
        intent.putExtra("hello", "from BackPress");
        setResult(0,intent);
        finish();
    }

//    @Override
//    public void onBackPressed() {     //使用此方法，不能返回intent到上一个activity，为空，为啥？？
//        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("hello", "from BackPress");
//        Log.i(TAG, "onBackPressed: why????");
//        setResult(0,intent);
//       // finish();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode )
        {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent();
                intent.putExtra("hello", "from BackPress");
                Log.i(TAG, "onBackPressed: what  ????");
                setResult(0,intent);    //可以设置返回intent
                // finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
