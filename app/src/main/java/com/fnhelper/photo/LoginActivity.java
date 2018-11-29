package com.fnhelper.photo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.beans.LoginBean;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.BindInputTelActivity;
import com.fnhelper.photo.utils.DialogUtils;
import com.fnhelper.photo.wxapi.AccessBean;
import com.fnhelper.photo.wxapi.WeiXin;
import com.fnhelper.photo.wxapi.WxUserInfoBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;

public class LoginActivity extends BaseActivity implements IWXAPIEventHandler {


    @BindView(R.id.wx_login)
    TextView wxLogin;
    @BindView(R.id.tel_login)
    TextView telLogin;

    private IWXAPI wxAPI;
    private ReceiveBroadCast receiveBroadCast;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        //微信
        wxLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithWeChat();
            }
        });

        //账号密码
        telLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(TelLoginActivity.class);
            }
        });

    }


    private void loginWithWeChat() {
        if (wxAPI == null) {
            wxAPI = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.WECHAT_APPID, true);
        }
        if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
            showBottom(LoginActivity.this, "没有安装微信");
            return;
        }
        wxAPI.registerApp(Constants.WECHAT_APPID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//官方固定写法
        req.state = "com.jensen.example";//自定义一个字串
        wxAPI.sendReq(req);
    }

    @Override
    public void onReq(BaseReq arg0) {
        Log.i("ansen", "WXEntryActivity onReq:" + arg0);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            Log.i("ansen", "微信分享操作.....");
            WeiXin weiXin = new WeiXin(2, resp.errCode, "");
            EventBus.getDefault().post(weiXin);
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
            Log.i("ansen", "微信登录操作....");
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            WeiXin weiXin = new WeiXin(1, resp.errCode, authResp.code);
            EventBus.getDefault().post(weiXin);
        }
        finish();
    }


    public void getAccessToken() {

        loadingDialog.show();

        SharedPreferences WxSp = getApplicationContext()
                .getSharedPreferences(Constants.sp_name, Context.MODE_PRIVATE);
        String code = WxSp.getString(Constants.code, "");
        final SharedPreferences.Editor WxSpEditor = WxSp.edit();
        Log.d("fn", "-----获取到的code----" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.WECHAT_APPID
                + "&secret="
                + Constants.WECHAT_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        Log.d("fn", "--------即将获取到的access_token的地址--------");

        //获取access_token
        retrofit2.Call<AccessBean> call = RetrofitService.createWXAPI().getAcc(Constants.WECHAT_APPID, Constants.WECHAT_SECRET, code, "authorization_code");
        call.enqueue(new retrofit2.Callback<AccessBean>() {
            @Override
            public void onResponse(retrofit2.Call<AccessBean> call, retrofit2.Response<AccessBean> response) {

                if (response != null && response.body() != null && response.body().getErrmsg() == null) {
                    //access_token获取成功 再获取userInfo
                    retrofit2.Call<WxUserInfoBean> call1 = RetrofitService.createWXAPI().getUserInfo(response.body().getAccess_token(), response.body().getOpenid());
                    call1.enqueue(new retrofit2.Callback<WxUserInfoBean>() {
                        @Override
                        public void onResponse(retrofit2.Call<WxUserInfoBean> call, retrofit2.Response<WxUserInfoBean> response) {

                            if (response != null && response.body() != null && response.body().getErrmsg() == null) {
                                //自己登陆接口
                                retrofit2.Call<LoginBean> call2 = RetrofitService.createMyAPI().login(response.body().getNickname(), response.body().getUnionid(), response.body().getHeadimgurl(), response.body().getOpenid());
                                call2.enqueue(new retrofit2.Callback<LoginBean>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<LoginBean> call, retrofit2.Response<LoginBean> response) {

                                        if (response != null && response.body() != null) {
                                            if (response.body().getCode() == CODE_SUCCESS) {
                                                //成功
                                                Constants.shareLink =response.body().getData().getShareLink();
                                                Constants.ID = response.body().getData().getID();
                                                Constants.sToken = response.body().getData().getSToken();
                                                Constants.sHeadImg = response.body().getData().getSHeadImg();
                                                Constants.sPhone = response.body().getData().getSPhone();
                                                Constants.sLinkPhone = response.body().getData().getsLinkPhone();
                                                Constants.sTsNickNameoken = response.body().getData().getSNickName();
                                                Constants.wx_num = response.body().getData().getSWeiXinNo();
                                                Constants.album_introduce = response.body().getData().getSIntroduce();
                                                Constants.album_name = response.body().getData().getSPhotoName();

                                                //存入静态变量
                                                editor.putString("ID",Constants.ID);
                                                editor.putString("shareLink",Constants.shareLink);
                                                editor.putString("sToken",Constants.sToken);
                                                editor.putString("sHeadImg",Constants.sHeadImg);
                                                editor.putString("sTsNickNameoken",Constants.sTsNickNameoken);
                                                editor.putString("wx_num",Constants.wx_num);
                                                editor.putString("album_introduce",Constants.album_introduce);
                                                editor.putString("album_name",Constants.album_name);
                                                editor.putString("sPhone",Constants.sPhone);
                                                editor.putString("sLinkPhone",Constants.sLinkPhone);
                                                editor.putString("vip_exi_time",Constants.vip_exi_time);
                                                editor.putBoolean("isVIP",Constants.isVIP);
                                                //存入html文件
                                                editor.putString("aboutHtml",response.body().getData().getHtmlPageUrl().getAbout());
                                                editor.putString("clientPowerHtml",response.body().getData().getHtmlPageUrl().getClientPower());
                                                editor.putString("drawInstructionHtml",response.body().getData().getHtmlPageUrl().getDrawInstruction());
                                                editor.putString("linkHtml",response.body().getData().getHtmlPageUrl().getLink());
                                                editor.putString("userAgreementHtml",response.body().getData().getHtmlPageUrl().getUserAgreement());
                                                editor.commit();

                                                if (response.body().getData().getSPhone()==null || TextUtils.isEmpty(response.body().getData().getSPhone())){
                                                    //如果绑定手机号返回为空 说明未绑定  -- 弹出提示框
                                                    //未绑定手机号提示
                                                    DialogUtils.showLoginTips(LoginActivity.this, new DialogUtils.OnCommitListener() {
                                                        @Override
                                                        public void onCommit() {
                                                            //去绑定
                                                            Intent i = new Intent(LoginActivity.this, BindInputTelActivity.class);
                                                            i.putExtra("which", 1);
                                                            startActivity(i);
                                                            finish();
                                                        }
                                                    }, new DialogUtils.OnCancelListener() {
                                                        @Override
                                                        public void onCancel() {
                                                            openActivityAndCloseThis(MainActivity.class);
                                                        }
                                                    });
                                                }else {
                                                    openActivityAndCloseThis(MainActivity.class);
                                                }
                                            } else if (response.body().getCode() == CODE_ERROR) {
                                                //失败
                                                showBottom(LoginActivity.this, response.body().getInfo());
                                            } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                                                //服务错误
                                                showBottom(LoginActivity.this, response.body().getInfo());
                                            } else if (response.body().getCode() == CODE_TOKEN) {
                                                //登录过期
                                                showBottom(LoginActivity.this, response.body().getInfo());
                                            } else if (response.body().getCode() == CODE_TOKEN) {
                                                //账号冻结
                                                showBottom(LoginActivity.this, response.body().getInfo());
                                            }
                                        }

                                        loadingDialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<LoginBean> call, Throwable t) {
                                        loadingDialog.dismiss();
                                    }
                                });

                            }

                        }

                        @Override
                        public void onFailure(Call<WxUserInfoBean> call, Throwable t) {
                            loadingDialog.dismiss();
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<AccessBean> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });

    }

    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //授权成功 获取token
            getAccessToken();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("authlogin");
        registerReceiver(receiveBroadCast, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.e("login", "onDestroy: " );
        unregisterReceiver(receiveBroadCast);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * 清除微信memory leak
     */
    public static void cleanWXLeak() {
        try {
            Class clazz = com.tencent.a.a.a.a.g.class;
            Field field = clazz.getDeclaredField("V");
            field.setAccessible(true);
            Object obj = field.get(clazz);
            if (obj != null) {
                com.tencent.a.a.a.a.g g = (com.tencent.a.a.a.a.g) obj;
                Field mapField = clazz.getDeclaredField("U");
                mapField.setAccessible(true);
                Map map = (Map) mapField.get(g);
                map.clear();
            }
            field.set(clazz, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
