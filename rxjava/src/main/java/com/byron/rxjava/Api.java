package com.byron.rxjava;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by company-ios on 2017/6/2.
 */

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Observable<LoginResultBean> login(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Observable<String> loginSystem(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Observable<ResponseBody> loginToSystem(@Field("phone") String phone, @Field("password") String password);
}
