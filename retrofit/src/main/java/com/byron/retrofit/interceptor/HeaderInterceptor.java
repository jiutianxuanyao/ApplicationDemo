package com.byron.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by company-ios on 2017/6/5.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                //header 和 addHeader 的区别是，header会检查是否存在，存在则删除，重新添加，addHeader不会
                .build();
        return chain.proceed(request);
    }
}
