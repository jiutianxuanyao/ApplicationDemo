package com.byron.retrofit.util;

/**
 * Created by company-ios on 2017/6/5.
 */

public class ByronUtils {

    public static final String SALT = "weinjuytrellllserty";

    public static String getTimestamp() {
        long timestamp = System.currentTimeMillis() / 1000;
        return timestamp+"";
    }

    public static String getToken(){
        long timestamp = System.currentTimeMillis() / 1000;
        String token = MD5Utils.getMd5(timestamp + SALT);
        return token;
    }
}
