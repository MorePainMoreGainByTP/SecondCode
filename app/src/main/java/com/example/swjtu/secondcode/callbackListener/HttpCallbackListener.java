package com.example.swjtu.secondcode.callbackListener;

/**
 * Created by tangpeng on 2017/8/4.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
