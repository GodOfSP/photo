package com.fnhelper.photo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fnhelper.photo.interfaces.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(Constants.WECHAT_APPID);
    }


    public interface onResponse{

    }

}
