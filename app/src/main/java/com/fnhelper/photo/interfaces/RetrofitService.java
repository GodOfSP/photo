package com.fnhelper.photo.interfaces;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tencent.mm.opensdk.utils.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.fnhelper.photo.interfaces.Constants.ROOT_PATH;

/**
 * <pre>
 *
 *     网络请求引引擎类，包含网络拦截器，包含网络缓存策略
 *     <pre/>
 */
public class RetrofitService {

    //设置 数据的缓存时间，有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control 配置，为 only-if-cache 时，只查询缓存而不会请求服务器， max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cache, max-stale=" + CACHE_STALE_SEC;
    //查询缓存的Cache-Control配置，为 Cache-Control设置为max-age=0时则不会使用缓存，而是请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    /**
     * 单例模式的 OKhttpClient,以及Retrifit引擎类
     */
    private static OkHttpClient mOkHttpClient;
    private static RetrofitService instance = null;

    //私有构造函数，使用newInstance方式访问对象(单利RetrofitService)
    private RetrofitService() {
    }

    //单利 引擎
    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }

    /**
     * API
     */
    private volatile static MyApi myApi = null;

    public static MyApi createMyAPI() {
        if (myApi == null) {
            synchronized (RetrofitService.class) {
                if (myApi == null) {
                    initInterceptor();

                    mOkHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                          .addInterceptor(commonInterceptor)
                            .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                            .readTimeout(mTimeOut, TimeUnit.SECONDS)
                            .writeTimeout(mTimeOut, TimeUnit.SECONDS).build();
                    myApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            //以构建的类型来选择host环境
                            .baseUrl(ROOT_PATH)
                            .addConverterFactory(GsonConverterFactory.create())//增加Gson实体类的支持
                            .addConverterFactory(ScalarsConverterFactory.create())////增加返回值为String的支持
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(MyApi.class);
                }
            }
        }
        return myApi;
    }


    private static HttpLoggingInterceptor loggingInterceptor;
    private static CommonInterceptor commonInterceptor;

    private static void initInterceptor() {

         commonInterceptor = new CommonInterceptor();

     loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("loggingInterceptor", message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static final int mTimeOut = 60;
    private volatile static WXLoginApi wxLoginApi = null;

    public static WXLoginApi createWXAPI() {
        if (wxLoginApi == null) {
            synchronized (RetrofitService.class) {

                if (wxLoginApi == null) {
                    initInterceptor();
                    mOkHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                            .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                            .readTimeout(mTimeOut, TimeUnit.SECONDS)
                            .writeTimeout(mTimeOut, TimeUnit.SECONDS).build();

                    wxLoginApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            //以构建的类型来选择host环境
                            .baseUrl("https://api.weixin.qq.com/sns/")
                            .addConverterFactory(GsonConverterFactory.create())//增加Gson实体类的支持
                            .addConverterFactory(ScalarsConverterFactory.create())////增加返回值为String的支持
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(WXLoginApi.class);
                }


            }
        }
        return wxLoginApi;
    }

}
