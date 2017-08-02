package com.example.swjtu.secondcode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OrderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "OrderBroadcastReceiver abortBroadcast", Toast.LENGTH_SHORT).show();
        abortBroadcast();   //拦截广播
    }
}
