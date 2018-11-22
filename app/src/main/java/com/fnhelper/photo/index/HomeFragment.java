package com.fnhelper.photo.index;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fnhelper.photo.ModifyPhotoWordActivity;
import com.fnhelper.photo.R;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.CheckCodeBean;
import com.fnhelper.photo.beans.NewsListBean;
import com.fnhelper.photo.beans.PreviewItemBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.MyCodeAc;
import com.fnhelper.photo.mine.PersonalCenterAc;
import com.fnhelper.photo.utils.DialogUtils;
import com.fnhelper.photo.utils.DownloadUtil;
import com.fnhelper.photo.utils.FullyGridLayoutManager;
import com.fnhelper.photo.utils.ImageUtil;
import com.fnhelper.photo.utils.STokenUtil;
import com.fnhelper.photo.utils.TimeFormatUtils;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
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
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.base.BaseActivity.showBottom;
import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;
import static com.fnhelper.photo.interfaces.Constants.pageSize;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;


/**
 * 相册动态
 */
public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.code)
    ImageView code;
    @BindView(R.id.share_my_pics)
    ConstraintLayout shareMyPics;
    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.search_cl)
    ConstraintLayout searchCl;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    Unbinder unbinder;
    @BindView(R.id.empty_page)
    RelativeLayout emptyPage;


    private QuickAdapter<NewsListBean.DataBean.RowsBean> adapter;
    private boolean canLoadMore = false;
    private int pageNum = 1;
    private String keyWord = "";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);


        shareMyPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyCodeAc.class));
            }
        });

        initSearch();
        initTklRefreshLayout();
        initRecyclerView();
        initSharePop();


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        refresh.startRefresh();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    private void initSearch() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = searchEt.getText().toString().trim();
                if (!TextUtils.isEmpty(keyWord)) {
                    getList(false);
                }
            }
        });
    }


    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new QuickAdapter<NewsListBean.DataBean.RowsBean>(getContext(), R.layout.item_news) {
            @Override
            protected void convert(BaseAdapterHelper helper, final NewsListBean.DataBean.RowsBean item, int position) {

                boolean isMyNews = Constants.ID.equals(item.getSClientId());

                //公共部分
                //头像
                helper.setFrescoImageResource(R.id.head_pic, item.getSHeadImg());
                //  helper.setVisible(R.id.vip_logo, item.isBIsVip());
                //用户名
                helper.setText(R.id.user_name, item.getSNickName());
                //来源
                if (item.getSClientId().equals(item.getSSourceId())){
                    helper.setVisible(R.id.pic_source,true);
                    helper.setOnClickListener(R.id.pic_source, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 2018-11-22  跳到来源详情
                        }
                    });
                }else {
                    helper.setVisible(R.id.pic_source,false);
                }
                // 发布时间
                helper.setText(R.id.date, TimeFormatUtils.formatTime(item.getDInsertTime()));
                //上次分享时间
                if (item.getDShareTime() == null && TextUtils.isEmpty(item.getDInsertTime())) {
                    helper.setVisible(R.id.share_time, false);
                } else {
                    helper.setText(R.id.share_time, item.getDShareTime());
                }
                //货号
                if (item.getSGoodsNo() == null || TextUtils.isEmpty(item.getSGoodsNo())) {

                    helper.setVisible(R.id.good_num, false);
                    helper.setVisible(R.id.good_num_title, false);
                } else {
                    helper.setText(R.id.good_num, item.getSGoodsNo());
                }
                //拿货价
                if (item.getDCommodityPrices() == null || TextUtils.isEmpty(item.getDCommodityPrices()) || "1".equals(item.getICommodityPricesPrivate())) {
                    helper.setVisible(R.id.get_price, false);
                } else {
                    helper.setText(R.id.get_price, "拿货价:" + item.getDCommodityPrices());
                }
                //零售
                if (item.getDRetailprices() == null || TextUtils.isEmpty(item.getDRetailprices()) || "1".equals(item.getIRetailpricesPrivate())) {
                    helper.setVisible(R.id.sale_price, false);
                } else {
                    helper.setText(R.id.sale_price, "零售价:" + item.getDRetailprices());
                }
                //批发价
                if (item.getDTradePrices() == null || TextUtils.isEmpty(item.getDTradePrices()) || "1".equals(item.getITradePricesPrivate())) {
                    helper.setVisible(R.id.pf_price, false);
                } else {
                    helper.setText(R.id.pf_price, "批发价:" + item.getDTradePrices());
                }
                //打包价
                if (item.getDPackPrices() == null || TextUtils.isEmpty(item.getDPackPrices()) || "1".equals(item.getIPackPricesPrivate())) {
                    helper.setVisible(R.id.pack_price, false);
                } else {
                    helper.setText(R.id.pack_price, "打包价:" + item.getDPackPrices());
                }


                if (helper.getTextView(R.id.get_price).getVisibility() == View.GONE && helper.getTextView(R.id.sale_price).getVisibility() == View.GONE) {
                    helper.setVisible(R.id.first_tag, false);
                }
                if (helper.getTextView(R.id.pf_price).getVisibility() == View.GONE && helper.getTextView(R.id.pack_price).getVisibility() == View.GONE) {
                    helper.setVisible(R.id.second_tag, false);
                }
                //备注
                if (item.getSRemark() == null || TextUtils.isEmpty(item.getSRemark())) {
                    helper.setVisible(R.id.mark, false);
                } else {
                    helper.setText(R.id.mark, item.getSRemark());
                }

                //内容
                if (item.getSContext() == null || TextUtils.isEmpty(item.getSContext())) {
                    helper.setVisible(R.id.content, false);
                } else {
                    helper.setText(R.id.content, item.getSContext());
                }


                if (item.getSVideoUrl() == null || TextUtils.isEmpty(item.getSVideoUrl()) || "".equals(item.getSVideoUrl())) { //图片

                    final ArrayList<IThumbViewInfo> pics = new ArrayList<>();
                    String[] p = item.getSImagesUrl().split(",");

                    for (int i = 0; i < p.length; i++) {
                        pics.add(new PreviewItemBean(p[i]));
                    }

                    RecyclerView recyclerView = helper.getView(R.id.recycler);
                    recyclerView.setLayoutManager(new FullyGridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new QuickAdapter<IThumbViewInfo>(getContext(), R.layout.item_news_pic, pics) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, final IThumbViewInfo item, final int position) {

                            helper.setFrescoImageResource(R.id.pic, item.getUrl());

                            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    GPreviewBuilder.from(getActivity())//activity实例必须
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

                    helper.setVisible(R.id.recycler, true);
                    helper.setVisible(R.id.video, false);
                } else { // 视频

                    helper.setVisible(R.id.recycler, false);
                    helper.setVisible(R.id.video, true);
                    helper.setImageByUrl(R.id.video,item.getsVideoImageUrl());

                  /*  MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    Bitmap bitmap = null;
                    try {
                        //这里要用FileProvider获取的Uri
                        if (item.getSVideoUrl().contains("http")) {
                            retriever.setDataSource(item.getSVideoUrl(), new HashMap<String, String>());
                        } else {
                            retriever.setDataSource(item.getSVideoUrl());
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
                    helper.setOnClickListener(R.id.video, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getContext(), VideoPlayerDetailedActivity.class);
                            intent.putExtra("url", item.getSVideoUrl());
                            startActivity(intent);

                        }


                    });

                }

                //公共点击事件
                if (!isMyNews) {
                    helper.setVisible(R.id.delete, false);
                    helper.setVisible(R.id.toTop, false);
                }else {
                    helper.setVisible(R.id.delete, true);
                    helper.setVisible(R.id.toTop, true);
                }
                //删除
                helper.setOnClickListener(R.id.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delNews(item.getID());
                    }
                });
                //置顶
                helper.setOnClickListener(R.id.toTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toTop(item.getID());
                    }
                });
                //下载
                helper.setOnClickListener(R.id.download, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("0".equals(item.getIType())) {
                            downloadNews(item.getSImagesUrl(), item.getIType());
                        } else {
                            downloadNews(item.getSVideoUrl(), item.getIType());
                        }
                    }
                });
                //编辑，
                helper.setOnClickListener(R.id.modify, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), ModifyPhotoWordActivity.class);
                        i.putExtra("data", item);
                        startActivity(i);
                    }
                });
                //一键分享
                helper.setOnClickListener(R.id.one_share, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nowItem = item;
                        if (TextUtils.isEmpty(item.getSVideoUrl())) { //图片
                            nowWhich = 0;
                        } else {
                            nowWhich = 1;
                        }
                        showSharePop();
                    }
                });

                //进入个人信息
                helper.setOnClickListener(R.id.head_pic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), PersonalCenterAc.class);
                        intent.putExtra("concernId", item.getSClientId());
                        intent.putExtra("nickName", item.getSNickName());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }


    /**
     * 删除动态
     */
    private void delNews(final String sImageTextId) {


        DialogUtils.showDelNewsTips(getContext(), new DialogUtils.OnCommitListener() {
            @Override
            public void onCommit() {
                Call<CheckCodeBean> call = RetrofitService.createMyAPI().Cancel(sImageTextId);
                call.enqueue(new Callback<CheckCodeBean>() {
                    @Override
                    public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                        if (response != null) {
                            if (response.body() != null) {
                                if (response.body().getCode() == CODE_SUCCESS) {
                                    //成功
                                    showBottom(getContext(), response.body().getInfo());
                                    refresh.startRefresh();
                                } else if (response.body().getCode() == CODE_ERROR) {
                                    //失败
                                    showBottom(getContext(), response.body().getInfo());
                                } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                                    //服务错误
                                    showBottom(getContext(), response.body().getInfo());
                                } else if (response.body().getCode() == CODE_TOKEN) {
                                    //登录过期
                                    STokenUtil.check(getActivity());
                                    showBottom(getContext(), response.body().getInfo());
                                } else if (response.body().getCode() == CODE_TOKEN) {
                                    //账号冻结
                                    showBottom(getContext(), response.body().getInfo());
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                        showBottom(getContext(), "网络异常！");
                    }
                });
            }
        });


    }

    /**
     * 置顶动态
     */
    private void toTop(String sImageTextId) {
        Call<CheckCodeBean> call = RetrofitService.createMyAPI().SetTop(sImageTextId);
        call.enqueue(new Callback<CheckCodeBean>() {
            @Override
            public void onResponse(Call<CheckCodeBean> call, Response<CheckCodeBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            showBottom(getContext(), response.body().getInfo());
                            refresh.startRefresh();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(getActivity());
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            showBottom(getContext(), response.body().getInfo());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CheckCodeBean> call, Throwable t) {
                showBottom(getContext(), "网络异常！");
            }
        });

    }

    private IWXAPI wxAPI = null;
    private static final int THUMB_SIZE = 150;
    private EasyPopup mSharePop;
    private int nowWhich = 0;
    private NewsListBean.DataBean.RowsBean nowItem = null;
    private ZLoadingDialog zLoadingDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(getContext(), "除第一张图片之外的需要您手动点击添加哦 ~", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                // 最后通知图库更新
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                zLoadingDialog.dismiss();
                showBottom(getContext(), "保存成功！");
            }else if (msg.what ==3){
                zLoadingDialog.dismiss();
                showBottom(getContext(), "保存视频失败！");
            }
        }
    };

    private void initSharePop() {

        zLoadingDialog = new ZLoadingDialog(getContext());
        zLoadingDialog.setLoadingBuilder(DOUBLE_CIRCLE)//设置类型
                .setLoadingColor(getResources().getColor(R.color.colorPrimaryDark))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(getResources().getColor(R.color.text_gray));  // 设置字体颜色

        mSharePop = EasyPopup.create()
                .setContentView(getContext(), R.layout.share_pop)
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
                .setDimView((ViewGroup) getActivity().findViewById(android.R.id.content))
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
        mSharePop.showAtAnchorView(getActivity().findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);
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
            new RxPermissions(getActivity()).request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //初始化微信
                                if (wxAPI == null) {
                                    wxAPI = WXAPIFactory.createWXAPI(getContext(), Constants.WECHAT_APPID, true);
                                }
                                if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                    showBottom(getContext(), "没有安装微信");
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

                                                    if (i == 0)
                                                        files.add(ImageUtil.saveImageToSdCard(getContext(), nowItem.getSImagesUrl().split(",")[i]));
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
                                                    imageUris.add(ImageUtil.getImageContentUri(f, getContext()));
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
                                Toast.makeText(getContext(), "请打开权限!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            //视频

            //请求权限
            new RxPermissions(getActivity()).request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                //初始化微信
                                if (wxAPI == null) {
                                    wxAPI = WXAPIFactory.createWXAPI(getContext(), Constants.WECHAT_APPID, true);
                                }
                                if (!wxAPI.isWXAppInstalled()) {//检查是否安装了微信
                                    showBottom(getContext(), "没有安装微信");
                                    return;
                                }
                                wxAPI.registerApp(Constants.WECHAT_APPID);


                                WXVideoObject video = new WXVideoObject();
                                video.videoUrl = nowItem.getSVideoUrl();

                                WXMediaMessage msg = new WXMediaMessage(video);
                                msg.title = nowItem.getSNickName();
                                msg.description = nowItem.getSContext();
                                Bitmap thumb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_video_play);
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
                                Toast.makeText(getContext(), "请打开权限!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        ImageUtil.copyWord(getContext(),nowItem.getSContext());

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
                            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
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
        progressDialog = new ProgressDialog(getContext());
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
                    showBottom(getContext(),"保存成功！");
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

    /**
     * 初始化下拉控件
     */
    private void initTklRefreshLayout() {

        new TwinklingRefreshLayoutUtil().getUpdateAndLoadMoreTwinkling(getActivity(), refresh);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {


            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                pageNum = 1;
                getList(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (canLoadMore) {
                    pageNum++;
                    getList(true);
                } else {
                    refreshLayout.finishLoadmore();
                    showBottom(getContext(), "没有更多了 ~");
                }
            }
        });
    }


    /**
     * 判断是否有置顶的item
     */
    private void checkHaveTopTag(ArrayList<NewsListBean.DataBean.RowsBean> elem) {


        for (int i = 0; i < elem.size(); i++) {
            if (elem.get(i).isBIsDeleted()) {
                elem.remove(i);
            }
            if (elem.get(i).isBIsTop()) {
                elem.add(0, elem.remove(i));
            }
        }
    }

    private void getList(final boolean isLoadMore) {


        Call<NewsListBean> call = RetrofitService.createMyAPI().GetImageTextList(keyWord, pageSize, pageNum);
        call.enqueue(new Callback<NewsListBean>() {
            @Override
            public void onResponse(Call<NewsListBean> call, Response<NewsListBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body().getData() != null) {
                                if (response.body().getData().getRows() != null && response.body().getData().getRows().size() != 0) {
                                    checkHaveTopTag((ArrayList<NewsListBean.DataBean.RowsBean>) response.body().getData().getRows());
                                    if (isLoadMore) {
                                        adapter.addAll(response.body().getData().getRows());
                                    } else {
                                        adapter.replaceAll(response.body().getData().getRows());
                                    }
                                    if (response.body().getData().getTotal() > adapter.getData().size()) {
                                        canLoadMore = true;
                                    } else {
                                        canLoadMore = false;
                                    }
                                    emptyPage.setVisibility(View.GONE);
                                } else {
                                    if (!isLoadMore) {
                                        adapter.clear();
                                        emptyPage.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            STokenUtil.check(getActivity());
                            showBottom(getContext(), response.body().getInfo());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                            refresh.finishRefreshing();
                            refresh.finishLoadmore();
                            showBottom(getContext(), response.body().getInfo());
                        }
                    } else {
                        refresh.finishRefreshing();
                        refresh.finishLoadmore();
                        showBottom(getContext(), "网络异常！");
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsListBean> call, Throwable t) {
                refresh.finishRefreshing();
                refresh.finishLoadmore();
            }
        });
    }


}
