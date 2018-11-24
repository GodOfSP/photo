package com.fnhelper.photo.myfans;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.beans.FansListBean;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.STokenUtil;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

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
import static com.fnhelper.photo.interfaces.Constants.pageSize;


/**
 * 我的粉丝
 */
public class MyFansFrafment extends Fragment {


    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout refresh;
    Unbinder unbinder;
    @BindView(R.id.viewGroup)
    ConstraintLayout viewGroup;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.empty_page)
    RelativeLayout emptyPage;


    private QuickAdapter<FansListBean.DataBean.RowsBean> adapter;
    private boolean canLoadMore = false;
    private int pageNum = 1;
    private EasyPopup mCirclePop;
    private String nowId = "";
    private String keyWord = "";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyFansFrafment() {
        // Required empty public constructor
    }


    public static MyFansFrafment newInstance(String param1, String param2) {
        MyFansFrafment fragment = new MyFansFrafment();
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
        View view = inflater.inflate(R.layout.fragment_my_fans, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTklRefreshLayout();
        initRecyclerView();
        initSearch();
        initPop();
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
     * 初始化弹框
     */
    private void initPop() {
        mCirclePop = EasyPopup.create()
                .setContentView(getContext(), R.layout.auth_pop)
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
                .setDimView(viewGroup)
                .apply();

        //设置备注名
        mCirclePop.findViewById(R.id.set_markName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SetRemarkNameAc.class);
                intent.putExtra("id", nowId);
                startActivity(intent);
                mCirclePop.dismiss();
            }
        });

        //设置权限
        mCirclePop.findViewById(R.id.set_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SetFansPermissionsAc.class);
                intent.putExtra("id", nowId);
                startActivity(intent);
                mCirclePop.dismiss();
            }
        });
        //取消
        mCirclePop.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
            }
        });
    }


    /**
     * 权限设置
     */
    private void showAuthSettingPop() {

        mCirclePop.showAtAnchorView(getActivity().findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);

    }

    private void initRecyclerView() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        adapter = new QuickAdapter<FansListBean.DataBean.RowsBean>(getContext(), R.layout.item_my_fans) {
            @Override
            protected void convert(BaseAdapterHelper helper, final FansListBean.DataBean.RowsBean item, int position) {

                helper.setVisible(R.id.auth_setting_btn,false);//权限，设置按钮 被要求隐藏

                helper.setFrescoImageResource(R.id.head_pic, item.getSHeadImg());

                helper.setText(R.id.expiry_date, item.getDExpireTime());
                //   helper.setText(R.id.num, item.getNumber() + "");
                helper.setVisible(R.id.vip_logo, item.isBIsVip());
                if (item.getSRemarkName() != null) {
                    helper.setText(R.id.user_name, item.getSNickName() + "(" + item.getSRemarkName() + ")");
                } else {
                    helper.setText(R.id.user_name, item.getSNickName());
                }


                //权限设置
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nowId = item.getSClientId();
                        showAuthSettingPop();
                    }
                });

            }
        };

        recyclerView.setAdapter(adapter);

    }


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


        Call<FansListBean> call = RetrofitService.createMyAPI().GetFansListByPage(keyWord, pageSize, pageNum);
        call.enqueue(new Callback<FansListBean>() {
            @Override
            public void onResponse(Call<FansListBean> call, Response<FansListBean> response) {
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
                                    emptyPage.setVisibility(View.GONE);
                                }else {
                                    if (!isLoadMore){
                                        adapter.clear();
                                        emptyPage.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

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
                    refresh.finishRefreshing();
                    refresh.finishLoadmore();


                }
            }

            @Override
            public void onFailure(Call<FansListBean> call, Throwable t) {
                refresh.finishRefreshing();
                refresh.finishLoadmore();
                showBottom(getContext(), "网络异常！");
            }
        });
    }


}
