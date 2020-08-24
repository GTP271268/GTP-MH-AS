package com.gtp.myhistory.utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Gtp
 * @description: 同步get/post请求,都要放在子线程中,否则会报异常 : NetworkOnMainThreadException
 * @date :2020/6/4 0004 9:33
 */
public class OkHttpUtils {
    //get 同步请求
    public static String getSync(String url) {
        String s = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();  //创建url的请求
        Response response = null;
        try {
            response = client.newCall(request).execute();  //execute() : 同步, enqueue() : 异步
            s = response.body().string();  //获取数据
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    //post 同步请求
    public static String postSync(String url, String body) {  //body为参数列表
        String s = "";
        OkHttpClient client = new OkHttpClient();
        //"application/x-www-form-urlencoded"  form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), body);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            s = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    // get 异步请求
    public static void getAsync(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    //post 异步请求
    public static void postAsync(String url, String body, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), body);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }


}
