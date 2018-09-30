package com.fnhelper.photo.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UpdateAPI {

    @POST("/app/version/new")//升级
    Call<ResponseBody> getData(@Query("appCode") String appCode, @Query("appType") String appType,
                               @Query("version") String version);
}
