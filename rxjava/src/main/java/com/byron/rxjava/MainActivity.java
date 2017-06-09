package com.byron.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 学习RxJava的使用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://ott.jsocn.com/jinshan/web/index.php/api/";
    private List<String> list;
    private List<School> schools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        View demo = findViewById(R.id.btn_demo);
        demo.setOnClickListener(this);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("data " + i);
        }
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            students.add(new Student("name" + i));
        }
        schools = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            schools.add(new School("school" + i, students));
        }
    }

    /**
     * RxJava：观察者Observer，被观察者Observable，订阅Subscribe
     *
     */

    private void basic(){
        /**
         *  被观察者Observable是一个类
         *  通过Observable的create方法创建最基本的被观察者
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("hello rxjava");
                e.onNext("this is basic operation");
                e.onComplete();
            }
        });
        /**
         *  观察者Observer是一个接口
         *  创建一个观察者对象
         */
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e(TAG, "onNext = " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };
        /**
         *  订阅
         *  当订阅后，被观察者才开始发送数据
         */
        observable.subscribe(observer);

        /**
         *  原理：相当于接口回调，给被观察者Observable传入一个Observer接口，当被观察者满足某个条件时，
         *  通知观察者(执行观察者的方法)
         */
    }

    /**
     * 创建Observable的方式
     * 1、使用Observable的create(),这种方式订阅完成后，不会执行onComplete()
     * 2、使用Observable的just(),这种方式订阅完成后，会执行onComplete()
     */

    private void just() {
        /**
         *  just会自动调用被观察者的onNext()方法
         *  Consumer消费者，如果不需要关系onComplete、onError等方法，可以执行使用Consumer创建一个观察者
         */
        Observable.just("hello just").subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "just accept = " + s);
            }
        });
    }



    private void fromIterable() {
        Observable.fromIterable(list).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "fromIterable accept = " + s);
            }
        });
    }

    /**
     *  map可以进行类型转换，只能一对一
     */
    private void map() {
        Observable.just(1, 2).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "integer = " + integer ;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "map accept : " + s);
            }
        });

        Log.e(TAG, "-------------");

        Observable.fromIterable(list).map(new Function<String, Integer>() {
            @Override
            public Integer apply(@NonNull String s) throws Exception {
                return Integer.parseInt(s.substring(4).trim());
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "map accept = " + integer);
            }
        });
    }

    /**
     *  flatMap 可以进行一对多转换
     */
    private void flatMap() {
        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull List<String> strings) throws Exception {
                return Observable.fromIterable(strings);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "flatMap accept = " + s);
            }
        });

        Observable.fromIterable(schools).flatMap(new Function<School, ObservableSource<Student>>() {
            @Override
            public ObservableSource<Student> apply(@NonNull School school) throws Exception {
                return Observable.fromIterable(school.getStudents());
            }
        }).subscribe(new Consumer<Student>() {
            @Override
            public void accept(@NonNull Student student) throws Exception {
                Log.e(TAG, "flatMap accept = " + student.getName());
            }
        });
    }

    private void filter() {
        Observable.fromIterable(list).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return Integer.parseInt(s.substring(4).trim()) >6;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "filter accept = " + s);
            }
        });
    }


    /**
     * take限制数量
     * doOnNext()允许我们在每次输出一个元素之前做一些额外的事情。
     */
    private void doOnNext() {
        Observable.fromIterable(list).take(5).doOnNext(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "doOnNext " + s);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "accept " + s);
            }
        });
    }

    /**
     * 当观察者订阅时，才创建Observable，并且针对每个观察者创建都是一个新的Observable。
     * 以何种方式创建这个Observable对象，当满足回调条件后，就会进行相应的回调。
     */
    private void defer() {
        Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return null;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {

            }
        });
    }

    /**
     *  创建一个按固定时间间隔发射整数序列的Observable，可用作定时器。即按照固定2秒一次调用onNext()方法。
     */
    private void interval() {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                Log.e(TAG, "interval = " + aLong);
            }
        });
    }

    /**
     *  创建一个发射特定整数序列的Observable，第一个参数为起始值，第二个为发送的个数，
     *  如果为0则不发送，负数则抛异常。
     *  上述表示发射1到20的数。即调用20次nNext()方法，依次传入1-20数字。
     */
    private void range() {
        Observable.range(1, 10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "range " + integer);
            }
        });
    }

    /**
     *  创建一个Observable，它在一个给定的延迟后发射一个特殊的值，即表示延迟2秒后，调用onNext()方法。
     */
    private void timer() {
        Log.e(TAG, "timer -----------");
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                Log.e(TAG, "timer " + aLong);
            }
        });
    }

    /**
     * 创建一个Observable，该Observable的事件可以重复调用。 不给参数，会一直重复，可以指定重复的次数
     */
    private void repeat() {
        Observable.fromIterable(list).repeat(2).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "repeat " + s);
            }
        });
    }

    /**
     *  Flowable 与 Subscribe 用来处理背压问题，只有异步才会出现背压
     *  所谓背压：被观察者发送事件很快，而观察者消费事件很慢，
     */

    private void flowable() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 10000; i++) {
                    Log.e(TAG, "emit " + i);
                    e.onNext(i);
                }
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe");
                s.request(1000);
                Log.e(TAG, "---------");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "flowable : " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError : " + t.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });
    }

    private void login(){

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        api.login("18752008062", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResultBean>() {
                    @Override
                    public void accept(@NonNull LoginResultBean loginResultBean) throws Exception {
                        Log.e(TAG, "login : " + loginResultBean.toString());
                        Log.e(TAG, "login : " + loginResultBean.getMessage());
                    }
                });
    }

    private void loginSystem() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        api.loginSystem("18752008062", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.e(TAG, "onNext : " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    private void loginToSystem(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        api.loginToSystem("18752008062", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            Log.e(TAG, "onNext : " + responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_demo:
                loginToSystem();
                break;
            default:
                break;
        }
    }
}
