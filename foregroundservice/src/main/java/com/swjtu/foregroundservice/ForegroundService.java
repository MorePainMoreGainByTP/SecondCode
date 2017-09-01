package com.swjtu.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by tangpeng on 2017/9/1.
 */

public class ForegroundService extends Service {
    int progress;
    DownloadBinder downloadBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (progress < 100) {
                        progress++;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        public int getProgress() {
            return progress;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        progress = 0;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is title title title")
                .setContentText("this is content content!!")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }
}
