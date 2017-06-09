package com.byron.retrofit.util;

/**
 * Created by company-ios on 2017/6/5.
 */

public class HttpClient {


        /*public static final String API_SERVER = "服务器地址"
        private static final OkHttpClient mOkHttpClient = new OkHttpClient();
        private static Retrofit mRetrofit;

        protected static Retrofit getRetrofit() {
            if (Retrofit == null) {
                Context context = Application.getInstance().getApplicationContext();
                //设定30秒超时
                mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
                //设置拦截器，以用于自定义Cookies的设置
                mOkHttpClient.networkInterceptors()
                        .add(new CookiesInterceptor(context));
                //设置缓存目录
                File cacheDirectory = new File(context.getCacheDir()
                        .getAbsolutePath(), "HttpCache");
                Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);
                mOkHttpClient.setCache(cache);
                //构建Retrofit
                mRetrofit = new Retrofit.Builder()
                        //配置服务器路径
                        .baseUrl(API_SERVER + "/")
                        //设置日期解析格式，这样可以直接解析Date类型
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        //配置转化库，默认是Gson
                        .addConverterFactory(ResponseConverterFactory.create())
                        //配置回调库，采用RxJava
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        //设置OKHttpClient为网络客户端
                        .client(mOkHttpClient)
                        .build();
            }
            return mRetrofit;
        }*/
}
