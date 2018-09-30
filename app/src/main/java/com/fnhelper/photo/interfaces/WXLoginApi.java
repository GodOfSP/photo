package com.fnhelper.photo.interfaces;

import com.fnhelper.photo.wxapi.AccessBean;
import com.fnhelper.photo.wxapi.WxUserInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ls on 2016/5/12.
 */

public interface WXLoginApi {


    //传参
    @GET("oauth2/access_token")
    Call<AccessBean> getAcc(@Query("appid") String appid, @Query("secret") String secret,
                             @Query("code") String code, @Query("grant_type") String grant_type);

    //UserInfoBean
    @GET("userinfo")
    Call<WxUserInfoBean> getUserInfo(@Query("access_token") String access_token, @Query("openid") String openid
                        );



}
