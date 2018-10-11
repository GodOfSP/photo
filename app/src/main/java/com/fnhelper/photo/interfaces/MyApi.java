package com.fnhelper.photo.interfaces;

import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.beans.GetCodeBean;
import com.fnhelper.photo.beans.LoginBean;
import com.fnhelper.photo.beans.PhoneLoginBean;

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


    //登陆
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
    Call<GetCodeBean> GetMobileCode(@Query("sPhone") String sPhone, @Query("iType") int Type);

    /**
     * 分页获取我的关注
     *
     * @return
     */
    @GET("Concern/GetConcernListByPage")
    Call<CheckCodeBean> GetConcernListByPage(@Query("keyword") String keyword, @Query("rows") int rows, @Query("page") int page);

    /**
     * 绑定绑定手机号  ?ID="+Constants.ID+"&sToken="+Constants.sToken
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Client/BingMobile")
    Call<CheckCodeBean> checkCode(@Field("sPhone") String sPhone, @Field("sPassword") String sPassword);


    /**
     * 手机号码登录  LoginByPhone
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Client/LoginByPhone")
    Call<PhoneLoginBean> LoginByPhone(@Field("sPhone") String sPhone, @Field("sPassword") String sPassword);


    /**
     * 修改用户信息  UpdatePersonalInfo
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Client/UpdatePersonalInfo")
    Call<CheckCodeBean> UpdatePersonalInfo(@Field("sPhotoName") String sPhotoName,
                                           @Field("sWeiXinNo") String sWeiXinNo,
                                           @Field("sPhone") String sPhone,
                                           @Field("sIntroduce") String sIntroduce);

    /**
     * 更新用户密码  UpdatePassword
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Client/UpdatePassword")
    Call<CheckCodeBean> UpdatePassword(@Field("sPhone") String sPhone,
                                       @Field("sPassword") String sPassword, @Field("sCode") String sCode);


}
