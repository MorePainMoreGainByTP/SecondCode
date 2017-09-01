package com.example.swjtu.secondcode.util;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by tangpeng on 2017/8/4.
 */

public class HttpUtil {
    //使用OkHttp提交数据请求服务
    public static void sendOkHttpRequest(String url, Map<String, String> params, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = null;
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
            requestBody = bodyBuilder.build();
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);   //OkHttp自动创建子线程返回数据封装到Callback的方法中
    }
}
