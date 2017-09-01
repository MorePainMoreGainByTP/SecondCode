package com.swjtu.foregroundservice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ForegroundService.DownloadBinder downloadBinder;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (ForegroundService.DownloadBinder) service;
            downloadBinder.startDownload();
            Toast.makeText(MainActivity.this, "current progress: " + downloadBinder.getProgress(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //可断点下载
    DownloadService.DownloadBinder mBinder;
    ServiceConnection downConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //启动下载服务，但并没有开始下载
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);   //启动服务，保证服务一直在后台运行
        bindService(intent, downConnection, BIND_AUTO_CREATE);  //绑定服务使其与activity通信
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public void onStartService(View v) {
        Intent intent = new Intent(this, ForegroundService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public void onStopService(View v) {
        Toast.makeText(MainActivity.this, "current progress: " + downloadBinder.getProgress(), Toast.LENGTH_SHORT).show();
        unbindService(connection);
    }

    //启动下载任务
    public void onStart(View v) {
        mBinder.startDownload("https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe");
    }

    //暂停下载任务
    public void onPause(View v) {
        mBinder.pauseDownload();
    }

    //取消下载任务
    public void onCancel(View v) {
        mBinder.cancelDownload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "无法获得权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(downConnection);
    }
}
