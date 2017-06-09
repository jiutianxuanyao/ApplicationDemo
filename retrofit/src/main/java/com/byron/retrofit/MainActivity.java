package com.byron.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byron.retrofit.bean.BookResponse;
import com.byron.retrofit.bean.User;
import com.byron.retrofit.util.ByronUtils;
import com.byron.retrofit.util.MD5Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit使用总结：
 * 1、BaseUrl以"/"结尾
 * 2、
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
//    private static final String BASE_URL = "https://api.douban.com/v2/";
    //json请求
//    private static final String BASE_URL = "http://42.121.58.216/mycircle/index.php/api/user/";
    private static final String BASE_URL = "http://ott.jsocn.com/jinshan/web/index.php/api/";
    private Retrofit retrofit;
    private Api api;
    private EditText mEtMethod;
    private Button mBtnGet;
    private Button mBtnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initView();
        initListener();
    }

    /**
     *  POST http://42.121.58.216/mycircle/index.php/api/user/login?token=86e40eb1f3acfe825d18802195fc32a6&timestamp=1496799849&mobile_phone=18752008062&password=e10adc3949ba59abbe56e057f20f883e http/1.1
        D/OkHttp: Content-Type: application/json;charset=utf-8
        D/OkHttp: Content-Length: 0
        END POST (0-byte body)
        这种方式会把参数拼接到url中，body为0，而后台需要的是json格式数据，所以访问出错
        .addInterceptor(new UrlInterceptor())       //通过Interceptor添加公用的url
        .addInterceptor(new HeaderInterceptor())    //通过Interceptor添加公用的请求头
        通过Interceptor方式添加url和header都会直接拼接在请求的url上，这种方式适合get，但是不适合有些post(主要看后台是如何处理的)
        如果想通过json方式，请求数据，最合适的还是需要通过@BODY，
     */

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient().newBuilder()
//                        .addInterceptor(new UrlInterceptor())
//                        .addInterceptor(new HeaderInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private boolean hasMethod(String methodName) {
        Method[] methods = MainActivity.class.getDeclaredMethods();
        Log.e(TAG, "method size === " + methods.length);
        for (int i = 0; i < methods.length; i++) {
            Log.e(TAG, "methodName " + i + " === " + methods[i]);
            String name = methods[i].getName();

            if(name.equals(methodName)){
                return true;
            }
        }
        return false;
    }

    private void callMethod(String methodName) {
        Class<MainActivity> clazz = MainActivity.class;
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mEtMethod = (EditText) findViewById(R.id.et_method);
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnPost = (Button) findViewById(R.id.btn_post);
    }

    private void initListener() {
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBookInfo();
            }
        });
        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSystem();
//                login();
//                loginByMap();
//                loginByJson();
//                loginByBody();
                /*File file1 = new File("/storage/emulated/0/DCIM/camera/IMG_20170426_133220.jpg");
                File file2 = new File("/storage/emulated/0/DCIM/camera/IMG_20170426_133009.jpg");
                Toast.makeText(MainActivity.this, "fileName = " + file1.getName(), Toast.LENGTH_SHORT).show();
                uploadFile(file1, file2);*/
//                uploadFiles();
            }
        });
    }

    private void getBooks() {
        api.getBooks("小王子", "", 0, 3)
                .enqueue(new Callback<BookResponse>() {
                    @Override
                    public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                        Log.e(TAG, "getBooks onResponse === " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<BookResponse> call, Throwable t) {
                        Log.e(TAG, "getBooks onFailure === " + t.getMessage());
                    }
                });
    }

    private void getBooksUseMap() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "见素见美");
        params.put("tag", null);
        params.put("start", "0");
        params.put("count", "3");
        api.getBooksUseMapParams(params)
                .enqueue(new Callback<BookResponse>() {
                    @Override
                    public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                        Log.e(TAG, "getBooksUseMap onResponse === " + response.body().getCount());
                    }

                    @Override
                    public void onFailure(Call<BookResponse> call, Throwable t) {
                        Log.e(TAG, "getBooksUseMap onFailure === " + t.getMessage());
                    }
                });
    }

    private void getBookInfo() {
        api.getBookInfo("1003078")
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e(TAG, "getBookInfo onResponse === " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(TAG, "getBookInfo onFailure === " + t.getMessage());
                    }
                });
    }

    private void login() {
        api.login("18752008062", MD5Utils.getMd5("123456"))
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e(TAG, "onResponse === " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(TAG, "onFailure === " + t.getMessage());
                    }
                });
    }

    private void loginByMap() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", "18752008062");
        params.put("password", MD5Utils.getMd5("123456"));
        api.loginByMap(params) //"18752008062", MD5Utils.getMd5("123456")
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        String data = response.body().toString();
                        try {
                            JSONObject json = new JSONObject(data);
                            int status = json.getInt("status");
                            Toast.makeText(MainActivity.this, "status = " + status, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "loginByMap onResponse === " + data);
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(TAG, "loginByMap onFailure === " + t.getMessage());
                    }
                });
    }

    private void loginByBody() {
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/user/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(Api.class);
        User user = new User();
        user.token = ByronUtils.getToken();
        user.timestamp = ByronUtils.getTimestamp();
        user.mobile_phone = "18752008062";
        user.password = MD5Utils.getMd5("123456");
        api.loginByBody(user)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e(TAG, "loginByBody onResponse === " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(TAG, "loginByBody onFailure === " + t.getMessage());
                    }
                });
    }

    private void loginByJson() {
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/user/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(Api.class);
        JSONObject params = new JSONObject();
        try {
            params.put("token", ByronUtils.getToken());
            params.put("timestamp", ByronUtils.getTimestamp());
            params.put("mobile_phone", "18752008062");
            params.put("password", MD5Utils.getMd5("123456"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), params.toString());
        api.loginByJson(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e(TAG, "loginByJson onResponse === " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(TAG, "loginByJson onFailure === " + t.getMessage());
            }
        });

    }

    private void uploadFile(File file) {
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(Api.class);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        api.uploadFile(body).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e(TAG, "uploadFile onResponse === " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(TAG, "uploadFile onFailure === " + t.getMessage());
            }
        });
    }

    private void uploadFile(File file1, File file2) {
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(Api.class);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file1);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body1 =
                MultipartBody.Part.createFormData("image1", file1.getName(), requestFile1);

        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile2 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file2);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body2 =
                MultipartBody.Part.createFormData("image2", file2.getName(), requestFile2);
        api.uploadFile(body1, body2).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e(TAG, "uploadFile onResponse === " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(TAG, "uploadFile onFailure === " + t.getMessage());
            }
        });
    }

    private void uploadFiles(){
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(Api.class);
        Map<String, RequestBody> files = new HashMap<>();
        File file = new File("/storage/emulated/0/DCIM/camera/IMG_20170426_133220.jpg");
        for (int i = 0; i < 2; i++) {
            RequestBody body =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

           /* // MultipartBody.Part  和后端约定好Key，这里的partName是用image
            MultipartBody.Part part =
                    MultipartBody.Part.createFormData("image"+i, file.getName(), body);*/
            files.put("image" + i + "\";filename=\"" + file.getName(), body);
        }

        api.uploadFiles(files)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e(TAG, "uploadFiles onResponse === " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(TAG, "uploadFiles onFailure === " + t.getMessage());
                    }
                });
    }

    /*private void uploadFiles2(){
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(Api.class);


        api.uploadFiles(files)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.e(TAG, "uploadFiles onResponse === " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(TAG, "uploadFiles onFailure === " + t.getMessage());
                    }
                });
    }*/

    private void upload() {
        String description = "this is description";
        RequestBody desBody = RequestBody.create(MediaType.parse("multipart/form-data"), description);
        String filePath = "";
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), fileBody);


    }

    private void loginSystem() {
        Api api = new Retrofit.Builder()
                .baseUrl("http://42.121.58.216/mycircle/index.php/api/user/")
                .client(new OkHttpClient.Builder()
//                        .addInterceptor(new UrlInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(ScalarsConverterFactory.create())  //这一句需要放到 GsonConverter上面，否则转换String类型会报错
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api.class);
        api.loginSystem(ByronUtils.getToken(), ByronUtils.getTimestamp(), "18752008062", MD5Utils.getMd5("123456"))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e(TAG, "loginSystem onResponse : " + response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "loginSystem onFailure : " + t.getMessage());
                    }
                });
    }
}
