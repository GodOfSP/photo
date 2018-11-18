package com.fnhelper.photo.interfaces;

import com.fnhelper.photo.beans.BalanceBean;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.beans.FansListBean;
import com.fnhelper.photo.beans.FollowListBean;
import com.fnhelper.photo.beans.GetCodeBean;
import com.fnhelper.photo.beans.LoginBean;
import com.fnhelper.photo.beans.MadiRecordBean;
import com.fnhelper.photo.beans.MyVipInfoBean;
import com.fnhelper.photo.beans.NewsListBean;
import com.fnhelper.photo.beans.NoticeBean;
import com.fnhelper.photo.beans.PersonalHeadBean;
import com.fnhelper.photo.beans.PhoneLoginBean;
import com.fnhelper.photo.beans.PresentRecordBean;
import com.fnhelper.photo.beans.UpdatePicBean;
import com.fnhelper.photo.beans.UpdateVdieoBean;
import com.fnhelper.photo.beans.VipMealListBean;
import com.fnhelper.photo.beans.VipMealOrderBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by ls on 2016/5/12.
 */

public interface MyApi {


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

    /**
     * 关注  /Concren/Follow
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Concern/Follow")
    Call<CheckCodeBean> Follow(@Field("sConcernId") String sConcernId);

    /**
     * 取消关注  /Concern/Cancel
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Concern/Cancel")
    Call<CheckCodeBean> CancelFollow(@Field("sConcernId") String sConcernId);

    /**
     * 设置关注的人备注名  /Concern/SetConcrenRemark
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Concern/SetConcernRemark")
    Call<CheckCodeBean> SetConcrenRemark(@Field("sConcernId") String sConcernId, @Field("sRemarkName") String sRemarkName);

    /**
     * 设置粉丝的备注名  /Concern/SetFansRemark
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Concern/SetFansRemark")
    Call<CheckCodeBean> SetFansRemark(@Field("clientId") String clientId, @Field("sRemarkName") String sRemarkName);

    /**
     * 设置粉丝的权限  /Client/SetFansPermissions
     * 0 看  1 不看
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Concern/SetFansPermissions")
    Call<CheckCodeBean> SetFansPermissions(@Field("clientId") String clientId, @Field("bIsNotSeen") String bIsNotSeen);

    /**
     * 上传图片  /Upload/UploadImage
     * sImageBase64tr 逗号隔开
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Upload/UploadImage")
    Call<UpdatePicBean> UploadImage(@Field("sImageBase64tr") String sImageBase64tr);


    /**
     * 上传图片  /Upload/UploadVideo
     * sImageBase64tr 逗号隔开
     *
     * @return
     */
    @Multipart
    @POST("Upload/UploadVideo")
    Call<CheckCodeBean> UploadVideo(@PartMap List<RequestBody> params);

    /**
     * 文件整块上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("Upload/UploadVideo")
    Call<UpdateVdieoBean> uploadFile(@Part MultipartBody.Part file, @Query("format") String format);


    /**
     * 发布和编辑动态 /ImageText/InsertAndUpdate
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ImageText/InsertAndUpdate")
    Call<CheckCodeBean> InsertAndUpdate(@Field("ID") String ID,
                                        @Field("sClientId") String sClientId,
                                        @Field("sSourceId") String sSourceId,
                                        @Field("dRetailprices") String dRetailprices,
                                        @Field("iTradePricesPrivate") String iTradePricesPrivate,
                                        @Field("sContext") String sContext,
                                        @Field("sGoodsNo") String sGoodsNo,
                                        @Field("dCommodityPrices") String dCommodityPrices,
                                        @Field("iCommodityPricesPrivate") String iCommodityPricesPrivate,
                                        @Field("dPackPrices") String dPackPrices,
                                        @Field("iRetailpricesPrivate") String iRetailpricesPrivate,
                                        @Field("iPackPricesPrivate") String iPackPricesPrivate,
                                        @Field("dTradePrices") String dTradePrices,
                                        @Field("sRemark") String sRemark,
                                        @Field("iPrivate") String iPrivate,
                                        @Field("sVideoUrl") String sVideoUrl,
                                        @Field("sImagesUrl") String sImagesUrl,
                                        @Field("iType") String iType
    );

    /**
     * 套餐充值  /VipPackage/Recharge
     * sVipPackageId 充值的套餐ID
     *
     * @return
     */
    @FormUrlEncoded
    @POST("VipPackage/Recharge")
    Call<VipMealOrderBean> Recharge(@Field("sVipPackageId") String sVipPackageId);

    /**
     * 会员提现  /DrawRecord/Draw
     * dDrawMoney 提现金额
     *
     * @return
     */
    @FormUrlEncoded
    @POST("DrawRecord/Draw")
    Call<CheckCodeBean> Draw(@Field("dDrawMoney") String dDrawMoney);

    /**
     * 删除动态/ImageText/Cancel  sImageTextId
     * dDrawMoney
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ImageText/Cancel")
    Call<CheckCodeBean> Cancel(@Field("sImageTextId") String sImageTextId);


    /**
     * 置顶动态/ImageText/SetTop  sImageTextId
     * dDrawMoney
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ImageText/SetTop")
    Call<CheckCodeBean> SetTop(@Field("sImageTextId") String sImageTextId);

    /**
     * 分享动态/ImageText/SetTop  sImageTextId
     * /ImageText/Share
     * 接口状态: 有效
     * 接口描述
     * <p>
     * 图文分享到微信朋友圈成功后需要调用该接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("ImageText/Share")
    Call<CheckCodeBean> Share(@Field("sImageTextId") String sImageTextId);


    /**
     * 获取可提现余额
     *    /Client/GetBalance
     * @return
     */
    @GET("Client/GetBalance")
    Call<BalanceBean> GetBalance();


    /**
     * 获取关注用户的信息
     *    /Concern/GetConcernUserInfo
     * @return
     */
    @GET("Concern/GetConcernUserInfo")
    Call<PersonalHeadBean> GetConcernUserInfo(@Query("sConcernId")String sConcernId);


    /**
     * 分页获取我的关注
     *
     * @return
     */
    @GET("Concern/GetConcernListByPage")
    Call<FollowListBean> GetConcernListByPage(@Query("keyword") String keyword, @Query("rows") int rows, @Query("page") int page);

    /**
     * 分页获取动态  /ImageText/GetImageTextList
     *
     * @return
     */
    @GET("ImageText/GetImageTextList")
    Call<NewsListBean> GetImageTextList(@Query("keyword") String keyword, @Query("rows") int rows, @Query("page") int page);

    /**
     * 分页获取动态 GetConcernImageTextList
     *
     * @return
     */
    @GET("ImageText/GetConcernImageTextList")
    Call<NewsListBean> GetConcernImageTextList(@Query("iType") String iType, @Query("sConcernId") String sConcernId, @Query("keyword") String keyword, @Query("rows") int rows, @Query("page") int page);

    /**
     * 分页获取我的粉丝  /Concern/GetFansListByPage
     *
     * @return
     */
    @GET("Concern/GetFansListByPage")
    Call<FansListBean> GetFansListByPage(@Query("keyword") String keyword, @Query("rows") int rows, @Query("page") int page);

    /**
     * 分页获取提现记录  //DrawRecord/GetPageList
     *
     * @return
     */
    @GET("DrawRecord/GetPageList")
    Call<PresentRecordBean> GetPageList(@Query("rows") int rows, @Query("page") int page);

    /**
     * 分页获取返佣记录 /VipPackage/GetCommisionRecord
     *
     * @return
     */
    @GET("VipPackage/GetCommisionRecord")
    Call<MadiRecordBean> GetCommisionRecord(@Query("rows") int rows, @Query("page") int page);

    /**
     * 分页获取公共列表 Notice/GetNoticeList
     *
     * @return
     */
    @GET("Notice/GetNoticeList")
    Call<NoticeBean> GetNoticeList(@Query("rows") int rows, @Query("page") int page);

    /**
     * 获取会员Vip的信息 /Client/GetVipInfo
     *
     * @return
     */
    @GET("Client/GetVipInfo")
    Call<MyVipInfoBean> GetCommisionRecord();

    /**
     * 获取会员充值套餐 /VipPackage/GetVipPackageList
     *
     * @return
     */
    @GET("VipPackage/GetVipPackageList")
    Call<VipMealListBean> GetVipPackageList();


}
