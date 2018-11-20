package com.fnhelper.photo.interfaces;

/**
 * 常量类
 */
public class Constants {

    //请求马  100-成功，200-失败，300登录过期，400-帐号冻结，500--服务异常
    public static int CODE_SERIVCE_LOSE = 500;
    public static int CODE_SUCCESS = 100;
    public static int CODE_ERROR = 200;
    public static int CODE_FREEZING = 400;
    public static int CODE_TOKEN = 300;
    //微信
    public static String WECHAT_APPID="wx04ff3ceebc3fc690";
    public static String WECHAT_SECRET="661c716beceb8f6fd9cde7cff3b456d9";
    // 服务器根地址
    public static final String ROOT_PATH = "http://153h79422m.imwork.net:10354/open/";
    //sp name
    public static String sp_name ="fn_sp";
    public static String code ="code";

    //用户信息
    public  static String ID ="";
    public  static String sToken ="";
    public  static String sTsNickNameoken ="";
    public  static String sPhone ="";
    public  static String sLinkPhone ="";
    public  static String sHeadImg ="";
    public  static String wx_num ="";
    public  static String album_introduce ="";
    public  static String album_name ="";
    public  static String vip_exi_time ="";
    public  static boolean isVIP =false;

    //分页参数
    public static int pageSize = 15;

    // token 操作参数  是否正在检查
    public static  boolean isCheckTokenNow = false;

}
