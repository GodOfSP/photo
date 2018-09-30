package com.fnhelper.photo.interfaces;

import com.fnhelper.photo.beans.LoginBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ls on 2016/5/12.
 */

public interface MyApi {

    //查询  带参数
    @POST("sy/pad/downloadQRCodeUrl")
    Call<NormalApiResponse<String>> downloadQRCodeUrl(@Query("mainCode") String mainCode);


    //传参
    @FormUrlEncoded
    @POST("Client/Login")
    Call<LoginBean> login(@Field("sNickName") String sNickName,
                          @Field("sUnionid") String sUnionid,
                          @Field("sHeadImg") String sHeadImg,
                          @Field("sOpenId") String sOpenId);


    /**
     * //发送验证码  sPhone 手机号码
     * 类型   iType  1-绑定手机,2-找回密码
     *
     * @return
     */
    @GET("Client/GetMobileCode")
    Call<NormalApiResponse<String>> GetMobileCode(@Query("sPhone") String sPhone, @Query("iType") int Type);


}
