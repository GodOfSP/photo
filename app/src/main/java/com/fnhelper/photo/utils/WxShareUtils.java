package com.fnhelper.photo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fnhelper.photo.R;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.luck.picture.lib.permissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.base.BaseActivity.showBottom;
import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 微信分析工具
 */
public class WxShareUtils {

    private Activity activity;
    private String sImageTextId ; //动态id
    private ArrayList<File> mFiles; // 文件列表
    private String word = "" ; // 图文的文字
    private String videoUrl = "" ; // 视频路径
    private boolean needFinishThis = false; // 需要关闭当前页面吗?

    public WxShareUtils(Activity activity,String sImageTextId) {
        this.activity = activity;
        this.sImageTextId = sImageTextId;
        initSharePop();
    }

    private IWXAPI wxAPI = null;
    private static final int THUMB_SIZE = 150;
    private EasyPopup mSharePop;
    private int nowWhich = 0;  //当前分享类型
    private ZLoadingDialog zLoadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(activity, "除第一张图片之外的需要您手动点击添加哦 ~", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                // 最后通知图库更新
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                zLoadingDialog.dismiss();
                showBottom(activity, "保存成功！");
            } else if (msg.what == 3) {
                zLoadingDialog.dismiss();
                showBottom(activity, "保存视频失败！");
            }else if (msg.what == 200){
                zLoadingDialog.dismiss();
                if (needFinishThis){
                    activity.finish();
                }
            }
        }
    };

    public void initSharePop() {

        zLoadingDialog = new ZLoadingDialog(activity);
        zLoadingDialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(activity.getResources().getColor(R.color.colorPrimaryDark))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(activity.getResources().getColor(R.color.text_gray));  // 设置字体颜色

        mSharePop = EasyPopup.create()
                .setContentView(activity.getApplicationContext(), R.layout.share_pop)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                //指定任意 ViewGroup 背景变暗
                .setDimView((ViewGroup) activity.findViewById(android.R.id.content))
                .apply();


        RelativeLayout friend = mSharePop.findViewById(R.id.friend_rl);
        RelativeLayout cicler = mSharePop.findViewById(R.id.cicler_rl);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNews(true);
            }
        });

        cicler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNews(false);
            }
        });
    }

    public void showSharePop() {
        mSharePop.showAtAnchorView(activity.findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);
    }

    /**
     * 分享动态
     * t 朋友圈或者朋友
     * which  图片或视频
     */
    private void shareNews(final boolean t) {

        mSharePop.dismiss();
        zLoadingDialog.setHintText("处理中");
        zLoadingDialog.show();

        shareToOurSystem();

        if (nowWhich == 0) {
            //图文
            //请求权限
            new RxPermissions(activity).request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //初始化微信
                                if (wxAPI == null) {
                                    wxAPI = WXAPIFactory.createWXAPI(activity, Constants.WECHAT_APPID, true);
                                }
                                if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                    showBottom(activity, "没有安装微信");
                                    return;
                                }
                                wxAPI.registerApp(Constants.WECHAT_APPID);


                                //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍

                                        try {

                                            try {

                                                Intent intent = new Intent();
                                                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                                ArrayList<Uri> imageUris = new ArrayList<Uri>();
                                                for (File f : mFiles) {
                                                imageUris.add(ImageUtil.getImageContentUri(f, activity));
                                                }

                                                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                                                intent.setType("image");
                                                ComponentName comp;

                                                if (t) {
                                                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                                                } else {
                                                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                                                    intent.putExtra("Kdescription", "分享朋友圈的图片说明");
                                                }
                                                intent.setComponent(comp);



                                                activity.startActivity(Intent.createChooser(intent,"分享图片"));

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            handler.sendEmptyMessage(1);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();


                            } else {
                                Toast.makeText(activity, "请打开权限!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            //视频

            //请求权限
            new RxPermissions(activity).request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //初始化微信
                                if (wxAPI == null) {
                                    wxAPI = WXAPIFactory.createWXAPI(activity, Constants.WECHAT_APPID, true);
                                }
                                if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                    showBottom(activity, "没有安装微信");
                                    return;
                                }
                                wxAPI.registerApp(Constants.WECHAT_APPID);


                                WXVideoObject video = new WXVideoObject();
                                video.videoUrl = videoUrl;


                                WXMediaMessage msg = new WXMediaMessage(video);
                                msg.title = "蜂鸟";
                                msg.description = "视频分享";
                                Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_video_play);
                                /**
                                 * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。
                                 * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。
                                 */
                                Bitmap thumbBitmap = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
                                thumb.recycle();
                                msg.thumbData = ImageUtil.bmpToByteArray(thumbBitmap, true);

                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = ImageUtil.buildTransaction("video");
                                req.message = msg;
                                req.scene = t ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                                wxAPI.sendReq(req);

                            } else {
                                Toast.makeText(activity, "请打开权限!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ImageUtil.copyWord(activity.getApplicationContext(), word);


            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true){
                        if (Constants.isFinishShare)
                        {
                            handler.sendEmptyMessage(200);
                            Constants.isFinishShare = false;
                            break;
                        }
                    }

                }
            }).start();

    }


    public void setNeedFinishThis(boolean needFinishThis){
        this.needFinishThis = needFinishThis;
    }
    /**
     * 调用服务器分享接口
     */
    private void shareToOurSystem(){

        //如果动态id为空 说明现在是创建动态
        if (TextUtils.isEmpty(sImageTextId)){
            return;
        }

        Call<CheckCodeBean> call = RetrofitService.createMyAPI().Share(sImageTextId);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            showBottom(activity, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(activity, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(activity, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(activity);
                            showBottom(activity, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(activity, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                showBottom(activity, "网络异常！");
            }
        });
    }

    public void setPath(ArrayList<File> files){
        mFiles = files;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setVideoUrl(String videoUrl){
        this.videoUrl = videoUrl;
    }

    public void setnowWhich(int nowWhich){
        this.nowWhich = nowWhich;
    }

}
