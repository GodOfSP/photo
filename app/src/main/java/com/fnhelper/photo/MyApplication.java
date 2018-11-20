package com.fnhelper.photo;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.fnhelper.photo.index.TestImageLoader;
import com.fnhelper.photo.interfaces.Constants;
import com.previewlibrary.ZoomMediaLoader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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
        initStoken();
        // 初始化fresco
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getContext())
                .setDownsampleEnabled(true)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .build();
        Fresco.initialize(this, config);


        //初始化zxing
        ZXingLibrary.initDisplayOpinion(this);
        //初始化预览框架
        ZoomMediaLoader.getInstance().init(new TestImageLoader());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决分包问题
        MultiDex.install(base);
    }


    private void initStoken() {
        Constants.ID = getSharedPreferences("SystemPreferences",
                Activity.MODE_PRIVATE).getString("ID", "");
        Constants.sToken = getSharedPreferences("SystemPreferences",
                Activity.MODE_PRIVATE).getString("sToken", "");

    }

}
