package com.fnhelper.photo.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fnhelper.photo.interfaces.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APPID, true);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if (!result) {
                Log.d("fn", "参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result = "";
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                switch (baseResp.getType()) {
                    case 1: //SendAuth
                        String code = ((SendAuth.Resp) baseResp).code;
                        SharedPreferences WxSp = getApplicationContext().getSharedPreferences(Constants.sp_name, Context.MODE_PRIVATE);
                        SharedPreferences.Editor WxSpEditor = WxSp.edit();
                        WxSpEditor.putString(Constants.code, code);
                        WxSpEditor.apply();
                        Intent intent = new Intent();
                        intent.setAction("authlogin");
                        WXEntryActivity.this.sendBroadcast(intent);
                        Log.d("fn", "ERR_OK");
                        finish();
                        break;
                    case 2: //SendMessageToWX
                        Log.d("fn", "分享视频成功");
                        Constants.isFinishShare = true;
                        finish();
                        break;
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Log.d("fn", "发送取消");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Log.d("fn", "发送被拒绝");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                result = "发送返回";
                Log.d("fn", "发送返回");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}