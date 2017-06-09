package com.byron.retrofit;

import com.byron.retrofit.bean.BookResponse;
import com.byron.retrofit.bean.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by company-ios on 2017/6/5.
 */

public interface Api {
    //@URL 会替换掉BaseUrl


    @GET("book/search")
    Call<BookResponse> getBooks(@Query("q") String name,
                                @Query("tag") String tag, @Query("start") int start,
                                @Query("count") int count);

    @GET("book/search")
    Call<BookResponse> getBooksUseMapParams(@QueryMap Map<String, String> params);

    @GET("book/{id}")
    Call<Object> getBookInfo(@Path("id") String id);

    /**
     * POST 方式
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded     //@FormUrlEncoded将会自动将请求参数的类型调整为application/x-www-form-urlencoded，FormUrlEncoded不能用于Get请求
    @POST("login")
    /**
     *  @Field注解将每一个请求参数都存放至请求体中，还可以添加encoded参数，该参数为boolean型，具体的用法为
        @Field(value = "book", encoded = true) String book
        encoded参数为true的话，key-value-pair将会被编码，即将中文和特殊字符进行编码转换
     */
    Call<Object> login(@Field("phone") String phone, @Field("password") String password);


    @FormUrlEncoded     //@FormUrlEncoded 需要和@Field开头的一起使用，否则会出现异常
    //@FieldMap parameters can only be used with form encoding.
    @POST("login")
    Call<Object> loginByMap(@FieldMap Map<String, String> params);


    //@Body 使用的是json格式 传递参数
    @POST("login")
    Call<Object> loginByBody(@Body User user);

    @POST("login")
    Call<Object> loginByJson(@Body RequestBody requestBody);

    //上传单张图片
    @Multipart
    @POST("upload/uploadFile")
    Call<Object> uploadFile(@Part MultipartBody.Part file);

    //上传单张图片
    @Multipart
    @POST("upload/uploadFile")      //可以添加描述
    /**
     *   String descriptionString = "hello, 这是文件描述";
         RequestBody description =
         RequestBody.create(
         MediaType.parse("multipart/form-data"), descriptionString);
     */
    Call<Object> uploadFile(@Part("description") RequestBody requestBody, @Part MultipartBody.Part file);

    //上传固定数量的多张图片
    @Multipart
    @POST("upload/uploadFile")
    Call<Object> uploadFile(@Part MultipartBody.Part file1,@Part MultipartBody.Part file2);

    //上传不定数量的多张图片
    @Multipart
    @POST("upload/uploadFile")
    Call<Object> uploadFiles(@PartMap Map<String, RequestBody> params);

    //上传不定数量的多张图片
    @Multipart
    @POST("upload/uploadFile")
    Call<Object> uploadFiles(@Part("files") MultipartBody.Part files);

    //图文同时上传
    @Multipart
    @POST("upload/uploadFile")      //TODO FieldMap  一起使用待验证
    Call<Object> uploadFiles(@FieldMap Map<String, String> params, @PartMap Map<String, RequestBody> files);

    //图文同时上传
    @Multipart
    @POST("upload/uploadFile")      //可以通过part传递其他类型参数
    Call<Object> uploadFiles(@Part("username") String username, @PartMap Map<String, RequestBody> files);


    /**
     *      POST http://42.121.58.216/mycircle/index.php/api/user/login?token=86e40eb1f3acfe825d18802195fc32a6&timestamp=1496799849&mobile_phone=18752008062&password=e10adc3949ba59abbe56e057f20f883e http/1.1
            D/OkHttp: Content-Type: application/json;charset=utf-8
            D/OkHttp: Content-Length: 0
            END POST (0-byte body)
            这种方式会把参数拼接到url中，body为0，而后台需要的是json格式数据，所以访问出错
     * @param token
     * @param timestamp
     * @param phone
     * @param password
     * @return
     */
    @Headers({"Content-Type:application/json;charset=utf-8"})
    @POST("login")
    Call<String> loginSystem(@Query("token") String token, @Query("timestamp") String timestamp, @Query("mobile_phone") String phone, @Query("password") String password);
}
