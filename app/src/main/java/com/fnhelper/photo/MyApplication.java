package com.fnhelper.photo;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.fnhelper.photo.interfaces.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;

public class MyApplication extends MultiDexApplication {


    public static String packageName;
    private static MyApplication instance;
    private static final String TAG = "EmojiCompatApplication";

    /**
     * Change this to {@code false} when you want to use the downloadable Emoji font.
     */
    private static final boolean USE_BUNDLED_EMOJI = true;

    // 获取ApplicationContext
    public static Context getContext() {
        return instance;
    }

    @Subscribe
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        packageName = this.getPackageName();
        // 初始化fresco
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getContext())
                .setDownsampleEnabled(true)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, config);

        //初始化微信sdk
        IWXAPI wxAPI = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APPID, true);
        wxAPI.registerApp(Constants.WECHAT_APPID);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决分包问题
        MultiDex.install(base);
    }


}
