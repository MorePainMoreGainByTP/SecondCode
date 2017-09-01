package com.swjtu.foregroundservice;

/**
 * Created by tangpeng on 2017/9/1.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
