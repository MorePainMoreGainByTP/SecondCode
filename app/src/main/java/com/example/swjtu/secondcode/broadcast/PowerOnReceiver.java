package com.example.swjtu.secondcode.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.swjtu.secondcode.MainActivity;
import com.example.swjtu.secondcode.R;


/**
 * 静态注册BroadcastReceiver，实现开机监听并发送通知
 */
public class PowerOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context).setContentTitle("程序自启动")
                .setContentText("快速启动本程序")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.large))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent).build();
        manager.notify(1, notification);
        Toast.makeText(context, "手机开机！！", Toast.LENGTH_SHORT).show();
    }
}
