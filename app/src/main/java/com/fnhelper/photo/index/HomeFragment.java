package com.fnhelper.photo.index;


import android.content.Intent;
import android.os.Bundle;
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

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.NewsListBean;
import com.fnhelper.photo.beans.PreviewItemBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.mine.MyCodeAc;
import com.fnhelper.photo.utils.FullyGridLayoutManager;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.IThumbViewInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fnhelper.photo.base.BaseActivity.showBottom;
import static com.fnhelper.photo.interfaces.Constants.CODE_ERROR;
import static com.fnhelper.photo.interfaces.Constants.CODE_SERIVCE_LOSE;
import static com.fnhelper.photo.interfaces.Constants.CODE_SUCCESS;
import static com.fnhelper.photo.interfaces.Constants.CODE_TOKEN;


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


    private QuickAdapter<NewsListBean.DataBean.RowsBean> adapter;
    private boolean canLoadMore = false;
    private int pageNum = 1;
    private int pageSize = 15;
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
                //公共部分
                //头像
                helper.setFrescoImageResource(R.id.head_pic, item.getSHeadImg());
                //  helper.setVisible(R.id.vip_logo, item.isBIsVip());
                //用户名
                helper.setText(R.id.user_name, item.getSNickName());
                // 发布时间
                helper.setText(R.id.date, item.getDInsertTime());
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
                    helper.setText(R.id.get_price, item.getDCommodityPrices());
                }
                //零售
                if (item.getDRetailprices() == null || TextUtils.isEmpty(item.getDRetailprices()) || "1".equals(item.getIRetailpricesPrivate())) {
                    helper.setVisible(R.id.sale_price, false);
                } else {
                    helper.setText(R.id.sale_price, item.getDRetailprices());
                }
                //批发价
                if (item.getDTradePrices() == null || TextUtils.isEmpty(item.getDTradePrices()) || "1".equals(item.getITradePricesPrivate())) {
                    helper.setVisible(R.id.pf_price, false);
                } else {
                    helper.setText(R.id.pf_price, item.getDTradePrices());
                }
                //打包价
                if (item.getDPackPrices() == null || TextUtils.isEmpty(item.getDPackPrices()) || "1".equals(item.getIPackPricesPrivate())) {
                    helper.setVisible(R.id.pf_price, false);
                } else {
                    helper.setText(R.id.pf_price, item.getDPackPrices());
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


                if (TextUtils.isEmpty(item.getSVideoUrl())) { //图片

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


                } else { // 视频

                    helper.setVisible(R.id.recycler, false);
                    helper.setVisible(R.id.video, true);

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
                     /*       Intent intent = new Intent(getContext(), ShowVedioAc.class);
                            intent.putExtra("path", item.getSVideoUrl());
                            startActivity(intent);*/

                        /*    List<IThumbViewInfo> vList = new ArrayList<>();
                            vList.add(new PreviewItemBean("https://upload.jianshu.io/users/upload_avatars/2354392/e962ec3f97a7.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240",item.getSVideoUrl()));
                            GPreviewBuilder.from(getActivity())
                                    .setData(vList)
                                    .setCurrentIndex(0)
                                    .setSingleFling(true)
                                    .setOnVideoPlayerListener(new VideoClickListener(){
                                        @Override
                                        public void onPlayerVideo(String url) {
                                            Log.d("onPlayerVideo",url);
                                          Intent intent=new Intent(getActivity(),VideoPlayerDetailedActivity.class);
                                            intent.putExtra("url",url);
                                            startActivity(intent);
                                        }
                                    })
                                    .setType(GPreviewBuilder.IndicatorType.Number)
                                    .start();*/
                        }
                    });

                }

                //公共点击事件
                //删除
                helper.setOnClickListener(R.id.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delNews();
                    }
                });
                //置顶
                helper.setOnClickListener(R.id.toTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toTop();
                    }
                });
                //下载
                helper.setOnClickListener(R.id.toTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadNews();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }


    /**
     * 删除动态
     */
    private void delNews() {


    }

    /**
     * 置顶动态
     */
    private void toTop() {


    }

    /**
     * 下载动态
     */
    private void downloadNews(){

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
