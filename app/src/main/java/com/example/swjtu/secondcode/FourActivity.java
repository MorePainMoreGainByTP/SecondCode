package com.example.swjtu.secondcode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by tangpeng on 2017/8/2.
 */

public class FourActivity extends AppCompatActivity {
    NotificationManager manager;
    EditText editText;
    Intent intent;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        editText = (EditText) findViewById(R.id.priority);
        intent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    }

    public void onSendNotification(View v) {
        Notification notification = new NotificationCompat.Builder(this).setContentIntent(pendingIntent).setContentTitle("默认通知")
                .setContentText("测试用纸")
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentInfo("ssssss")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.large))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //setLights 设置LED灯 setVibrate设置振动 setSound设置通知音乐
                .build();
        manager.notify(1, notification);
    }

    public void onPriorityNotifi(View v) {
        diffPriorityNotification(Integer.parseInt(editText.getText().toString()));
    }

    public void onBigTextStyle(View v) {
        Notification notification = new NotificationCompat.Builder(this).setContentIntent(pendingIntent)
                .setContentTitle("默认通知")
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentInfo("ssssss")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.large))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //setLights 设置LED灯 setVibrate设置振动 setSound设置通知音乐
                .setStyle(new NotificationCompat.BigTextStyle().bigText("测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用" +
                        "纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸测试用纸"))
                .build();
        manager.notify(0x201, notification);
    }

    public void onBigPictureStyle(View v) {
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.large))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.large)))
                .build();
        manager.notify(0x202, notification);
    }

    private void diffPriorityNotification(int priority) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setContentTitle("默认通知")
                .setContentText("测试用纸")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentInfo("ssssss")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.large))
                .setSmallIcon(R.mipmap.ic_launcher_round);
        switch (priority) {
            case 0:
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_MIN);
                break;
            case 1:
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_LOW);
                break;
            case 2:
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                break;
            case 3:
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                break;
            case 4:
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                break;
        }
        manager.notify((int) (Math.random() * 1000), notificationBuilder.build());
    }

}

