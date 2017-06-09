package com.byron.retrofit.interceptor;

import com.byron.retrofit.util.ByronUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by company-ios on 2017/6/5.
 */

public class UrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //这里是拼接请求url，不适合post请求
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("token", ByronUtils.getTimestamp())
                .addQueryParameter("timestamp", ByronUtils.getTimestamp())
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }
}
