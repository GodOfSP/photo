package com.fnhelper.photo.index;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.ModifyPhotoWordDetailActivity;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.BaseActivity;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.CanShareBean;
import com.fnhelper.photo.beans.NewDetailBean;
import com.fnhelper.photo.beans.PreviewItemBean;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.PersonalCenterAc;
import com.fnhelper.photo.mine.VipMealAc;
import com.fnhelper.photo.utils.DialogUtils;
import com.fnhelper.photo.utils.DownloadUtil;
import com.fnhelper.photo.utils.FullyGridLayoutManager;
import com.fnhelper.photo.utils.ImageUtil;
import com.fnhelper.photo.utils.STokenUtil;
import com.fnhelper.photo.utils.TimeFormatUtils;
import com.luck.picture.lib.permissions.RxPermissions;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.IThumbViewInfo;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * 动态详情
 */
public class NewsDeatilAc extends BaseActivity {


    @BindView(R.id.head_pic)
    SimpleDraweeView headPic;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.pic_source)
    TextView picSource;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.video)
    SimpleDraweeView video;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.div_l)
    View divL;
    @BindView(R.id.mark_title)
    TextView markTitle;
    @BindView(R.id.div_2)
    View div2;
    @BindView(R.id.good_num_title)
    TextView goodNumTitle;
    @BindView(R.id.good_num)
    TextView goodNum;
    @BindView(R.id.first_tag)
    TextView firstTag;
    @BindView(R.id.get_price)
    TextView getPrice;
    @BindView(R.id.sale_price)
    TextView salePrice;
    @BindView(R.id.pf_price)
    TextView pfPrice;
    @BindView(R.id.second_tag)
    TextView secondTag;
    @BindView(R.id.pack_price)
    TextView packPrice;
    @BindView(R.id.mark)
    TextView mark;
    @BindView(R.id.delete)
    TextView delete;
    @BindView(R.id.toTop)
    TextView toTop;
    @BindView(R.id.download)
    TextView download;
    @BindView(R.id.modify)
    TextView modify;
    @BindView(R.id.share_time)
    TextView shareTime;
    @BindView(R.id.one_share)
    TextView oneShare;
    private String id = "";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_news_deatil);
        ButterKnife.bind(this);
    }

    @Override
    protected void initUI() {
initSharePop();
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        getData();
    }

    @Override
    protected void initListener() {

    }

    private void getData() {

        Call<NewDetailBean> call = RetrofitService.createMyAPI().GetImageText(id);
        call.enqueue(new Callback<NewDetailBean>() {
            @Override
            public void onResponse(Call<NewDetailBean> call, Response<NewDetailBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData() != null) {
                                fillData(response.body());
                            }
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(NewsDeatilAc.this);
                            showBottom(NewsDeatilAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(NewsDeatilAc.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<NewDetailBean> call, Throwable t) {

            }
        });
    }

    private void fillData(final NewDetailBean newDetailBean) {

        boolean isMyNews = Constants.ID.equals(newDetailBean.getData().getSClientId());

        //公共部分
        //头像
        headPic.setImageURI(newDetailBean.getData().getsHeadImg());
        //  helper.setVisible(R.id.vip_logo, newDetailBean.getData().isBIsVip());
        //用户名
        userName.setText(newDetailBean.getData().getsNickName());

        // 发布时间
        date.setText(TimeFormatUtils.formatTime(newDetailBean.getData().getDInsertTime()));
        //上次分享时间
        if (newDetailBean.getData().getDShareTime() == null && TextUtils.isEmpty(newDetailBean.getData().getDInsertTime())) {
            shareTime.setVisibility(View.GONE);
        } else {
            shareTime.setText(newDetailBean.getData().getDShareTime());
        }
        //货号
        if (newDetailBean.getData().getSGoodsNo() == null || TextUtils.isEmpty(newDetailBean.getData().getSGoodsNo())) {

            goodNum.setVisibility(View.GONE);
            goodNumTitle.setVisibility(View.GONE);
        } else {
            goodNum.setText(newDetailBean.getData().getSGoodsNo());
        }
        //拿货价
        if (newDetailBean.getData().getDCommodityPrices() == null || TextUtils.isEmpty(newDetailBean.getData().getDCommodityPrices()) || "1".equals(newDetailBean.getData().getICommodityPricesPrivate())) {
            getPrice.setVisibility(View.GONE);
        } else {
            getPrice.setText("拿货价:" + newDetailBean.getData().getDCommodityPrices());
        }
        //零售
        if (newDetailBean.getData().getDRetailprices() == null || TextUtils.isEmpty(newDetailBean.getData().getDRetailprices()) || "1".equals(newDetailBean.getData().getIRetailpricesPrivate())) {
            salePrice.setVisibility(View.GONE);
        } else {
            salePrice.setText("零售价:" + newDetailBean.getData().getDRetailprices());
        }
        //批发价
        if (newDetailBean.getData().getDTradePrices() == null || TextUtils.isEmpty(newDetailBean.getData().getDTradePrices()) || "1".equals(newDetailBean.getData().getITradePricesPrivate())) {
            pfPrice.setVisibility(View.GONE);
        } else {
            pfPrice.setText("批发价:" + newDetailBean.getData().getDTradePrices());
        }
        //打包价
        if (newDetailBean.getData().getDPackPrices() == null || TextUtils.isEmpty(newDetailBean.getData().getDPackPrices()) || "1".equals(newDetailBean.getData().getIPackPricesPrivate())) {
            packPrice.setVisibility(View.GONE);
        } else {
            packPrice.setText("打包价:" + newDetailBean.getData().getDPackPrices());
        }

        if (getPrice.getVisibility() == View.GONE && salePrice.getVisibility() == View.GONE) {
            firstTag.setVisibility(View.GONE);
        }
        if (pfPrice.getVisibility() == View.GONE && packPrice.getVisibility() == View.GONE) {
            secondTag.setVisibility(View.GONE);
        }
        //备注
        if (newDetailBean.getData().getSRemark() == null || TextUtils.isEmpty(newDetailBean.getData().getSRemark())) {
            mark.setVisibility(View.GONE);
        } else {
            mark.setText(newDetailBean.getData().getSRemark());
        }

        //内容
        if (newDetailBean.getData().getSContext() == null || TextUtils.isEmpty(newDetailBean.getData().getSContext())) {
            content.setVisibility(View.GONE);
        } else {
            content.setText(newDetailBean.getData().getSContext());
        }


        if (newDetailBean.getData().getSVideoUrl() == null || TextUtils.isEmpty(newDetailBean.getData().getSVideoUrl()) || "".equals(newDetailBean.getData().getSVideoUrl())) { //图片

            final ArrayList<IThumbViewInfo> pics = new ArrayList<>();
            String[] p = newDetailBean.getData().getSImagesUrl().split(",");

            for (int i = 0; i < p.length; i++) {
                pics.add(new PreviewItemBean(p[i]));
            }

            recycler.setLayoutManager(new FullyGridLayoutManager(NewsDeatilAc.this, 3, GridLayoutManager.VERTICAL, false));
            recycler.setAdapter(new QuickAdapter<IThumbViewInfo>(NewsDeatilAc.this, R.layout.item_news_pic, pics) {
                @Override
                protected void convert(BaseAdapterHelper helper, final IThumbViewInfo item, final int position) {

                    helper.setFrescoImageResource(R.id.pic, item.getUrl());

                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            GPreviewBuilder.from(NewsDeatilAc.this)//activity实例必须
                                    .setData(pics)//集合
                                    .setCurrentIndex(position)
                                    .setSingleFling(false)//是否在黑屏区域点击返回
                                    .setDrag(false)//是否禁用图片拖拽返回
                                    .setType(GPreviewBuilder.IndicatorType.Dot)//指示器类型
                                    .start();//启动

                        }
                    });
                }
            });

            recycler.setVisibility(View.VISIBLE);
            video.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
        } else { // 视频

            recycler.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            play.setVisibility(View.VISIBLE);
            video.setImageURI(newDetailBean.getData().getSVideoImageUrl());

                  /*  MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    Bitmap bitmap = null;
                    try {
                        //这里要用FileProvider获取的Uri
                        if (newDetailBean.getData().getSVideoUrl().contains("http")) {
                            retriever.setDataSource(newDetailBean.getData().getSVideoUrl(), new HashMap<String, String>());
                        } else {
                            retriever.setDataSource(newDetailBean.getData().getSVideoUrl());
                        }
                        bitmap = retriever.getFrameAtTime();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            retriever.release();
                        } catch (RuntimeException ex) {
                            ex.printStackTrace();
                        }
                    }

                    helper.setImageBitmap(R.id.video, bitmap);*/
            //点击图片播放视频
            video.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(NewsDeatilAc.this, VideoPlayerDetailedActivity.class);
                    intent.putExtra("url", newDetailBean.getData().getSVideoUrl());
                    startActivity(intent);

                }


            });

        }

        //公共点击事件
        if (!isMyNews) {
            delete.setVisibility(View.GONE);
            toTop.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.VISIBLE);
            toTop.setVisibility(View.GONE);
        }


        //下载
       download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(newDetailBean.getData().getIType())) {
                    downloadNews(newDetailBean.getData().getSImagesUrl(), newDetailBean.getData().getIType()+"");
                } else {
                    downloadNews(newDetailBean.getData().getSVideoUrl(), newDetailBean.getData().getIType()+"");
                }
            }
        });
        //编辑，
       modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsDeatilAc.this, ModifyPhotoWordDetailActivity.class);
                i.putExtra("data", newDetailBean.getData());
                startActivity(i);
            }
        });
        //一键分享
        oneShare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowItem = newDetailBean.getData();
                if (TextUtils.isEmpty(newDetailBean.getData().getSVideoUrl())) { //图片
                    nowWhich = 0;
                } else {
                    nowWhich = 1;
                }
                checkCanShare(newDetailBean.getData().getID());
            }
        });

        //进入个人信息
        headPic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDeatilAc.this, PersonalCenterAc.class);
                intent.putExtra("concernId", newDetailBean.getData().getSClientId());
                intent.putExtra("nickName", newDetailBean.getData().getsNickName());
                startActivity(intent);
            }
        });

    }
    private IWXAPI wxAPI = null;
    private static final int THUMB_SIZE = 150;
    private EasyPopup mSharePop;
    private int nowWhich = 0;  //当前分享类型
    private NewDetailBean.DataBean nowItem = null;
    private ZLoadingDialog zLoadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(NewsDeatilAc.this, "除第一张图片之外的需要您手动点击添加哦 ~", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                // 最后通知图库更新
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                zLoadingDialog.dismiss();
                showBottom(NewsDeatilAc.this, "保存成功！");
            } else if (msg.what == 3) {
                zLoadingDialog.dismiss();
                showBottom(NewsDeatilAc.this, "保存视频失败！");
            }
        }
    };
    /**
     * 看能不能分享
     */
    private void checkCanShare(String id){



        Call<CanShareBean> call = RetrofitService.createMyAPI().IsCanShare(id);
        call.enqueue(new Callback<CanShareBean>() {
            @Override
            public void onResponse(Call<CanShareBean> call, Response<CanShareBean> response) {
                if (response!=null){
                    if (response.body()!=null){
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData()!=null){
                                if (response.body().getData().isIsCanShare()){
                                    showSharePop();
                                }else {
                                    DialogUtils.showAlertDialog(NewsDeatilAc.this, "会员提示", "目前暂不能分享,是否去开通会员？确认？", new DialogUtils.OnCommitListener() {
                                        @Override
                                        public void onCommit() {
                                            startActivity(new Intent(NewsDeatilAc.this, VipMealAc.class));
                                        }
                                    },null);
                                }

                            }
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(NewsDeatilAc.this);
                            showBottom(NewsDeatilAc.this, response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(NewsDeatilAc.this, response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CanShareBean> call, Throwable t) {

            }
        });


    }
    private void initSharePop() {

        zLoadingDialog = new ZLoadingDialog(NewsDeatilAc.this);
        zLoadingDialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(getResources().getColor(R.color.colorPrimaryDark))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(getResources().getColor(R.color.text_gray));  // 设置字体颜色

        mSharePop = EasyPopup.create()
                .setContentView(NewsDeatilAc.this, R.layout.share_pop)
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
                .setDimView((ViewGroup) findViewById(android.R.id.content))
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

    private void showSharePop() {
        mSharePop.showAtAnchorView(findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);
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

        if (nowWhich == 0) {
            //图文
            //请求权限
            new RxPermissions(NewsDeatilAc.this).request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //初始化微信
                                if (wxAPI == null) {
                                    wxAPI = WXAPIFactory.createWXAPI(NewsDeatilAc.this, Constants.WECHAT_APPID, true);
                                }
                                if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                    showBottom(NewsDeatilAc.this, "没有安装微信");
                                    return;
                                }
                                wxAPI.registerApp(Constants.WECHAT_APPID);


                                //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //这一步一定要clear,不然分享了朋友圈马上分享好友图片就会翻倍

                                        try {


                                            ArrayList<File> files = new ArrayList<>();

                                            try {

                                                for (int i = 0; i < nowItem.getSImagesUrl().split(",").length; i++) {

                                                    if (i == 0 &&!t)
                                                        files.add(ImageUtil.saveImageToSdCard(NewsDeatilAc.this, nowItem.getSImagesUrl().split(",")[i]));
                                                }

                                                Intent intent = new Intent(Intent.ACTION_SEND);
                                                ComponentName comp;

                                                if (t) {
                                                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                                                } else {
                                                    comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                                                    intent.putExtra("Kdescription", "分享朋友圈的图片说明");
                                                }
                                                intent.setComponent(comp);
                                                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setType("image/*");

                                                ArrayList<Uri> imageUris = new ArrayList<Uri>();
                                                for (File f : files) {
                                                    imageUris.add(ImageUtil.getImageContentUri(f, NewsDeatilAc.this));
                                                }

                                                intent.putExtra(Intent.EXTRA_STREAM, imageUris);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            zLoadingDialog.dismiss();
                                            handler.sendEmptyMessage(1);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();


                            } else {
                                Toast.makeText(NewsDeatilAc.this, "请打开权限!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            //视频

            //请求权限
            new RxPermissions(NewsDeatilAc.this).request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //初始化微信
                                if (wxAPI == null) {
                                    wxAPI = WXAPIFactory.createWXAPI(NewsDeatilAc.this, Constants.WECHAT_APPID, true);
                                }
                                if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                    showBottom(NewsDeatilAc.this, "没有安装微信");
                                    return;
                                }
                                wxAPI.registerApp(Constants.WECHAT_APPID);


                                WXVideoObject video = new WXVideoObject();
                                video.videoUrl = nowItem.getSVideoUrl();

                                WXMediaMessage msg = new WXMediaMessage(video);
                                msg.title = nowItem.getsNickName();
                                msg.description = nowItem.getSContext();
                                Bitmap thumb = BitmapFactory.decodeResource(NewsDeatilAc.this.getResources(), R.drawable.ic_video_play);
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
                                Toast.makeText(NewsDeatilAc.this, "请打开权限!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ImageUtil.copyWord(NewsDeatilAc.this, nowItem.getSContext());

    }


    /**
     * 下载动态
     * type 0图片 1 视频
     */
    private void downloadNews(final String data, final String type) {


        if ("0".equals(type)) {

            zLoadingDialog.setHintText("保存中...");
            zLoadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String[] strings = data.split(",");

                    for (int i = 0; i < strings.length; i++) {
                        // 首先保存图片
                        Bitmap bitmap = ImageUtil.returnBitmap(Uri.parse(strings[i]));
                        File appDir = new File(Environment.getExternalStorageDirectory(), "蜂鸟微商相册");
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File file = new File(appDir, fileName);
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 其次把文件插入到系统图库
                        try {
                            MediaStore.Images.Media.insertImage(NewsDeatilAc.this.getContentResolver(), file.getAbsolutePath(), fileName, null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendEmptyMessage(2);
                }
            }).start();
        } else {

            downFile(data);
        }


    }

    /**
     * 文件下载
     *
     * @param url
     */
    public void downFile(String url) {
        final ProgressDialog
                progressDialog = new ProgressDialog(NewsDeatilAc.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍后...");
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        DownloadUtil.get().download(url, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //下载完成进行相关逻辑操作
                showBottom(NewsDeatilAc.this, "保存成功！");
            }

            @Override
            public void onDownloading(int progress) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                //下载异常进行相关提示操作
            }
        });
    }

}
