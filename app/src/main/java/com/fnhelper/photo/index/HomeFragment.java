package com.fnhelper.photo.index;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fnhelper.photo.R;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.mine.MyCodeAc;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
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


    /*    *//**
     * 初始化刷新控件
     *//*
    private void initTklRefreshLayout() {
        MyRefreshView sinaRefreshView = new MyRefreshView(getContext());
        tklRefreshLayout.setHeaderView(sinaRefreshView);
        tklRefreshLayout.setEnableLoadmore(false);
        tklRefreshLayout.setEnableOverScroll(true);
        tklRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            */

    /**
     * 刷新
     *//*
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                //获取未读消失数
                loadMsgCount();
                //获取banner图片
                getBannerPic();
                //请求定位权限，获取主页内容
                new RxPermissions(getActivity()).request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    getHomeList();
                                } else {
                                    ToastUtil.showCenter(getActivity(), "请打开定位权限以获取数据!");
                                }
                            }
                        });
            }
        });
    }*/
    @Override
    public void onResume() {
        super.onResume();

        //请求权限后定位
    /*    new RxPermissions(getActivity()).request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                        } else {
                            Toast.makeText(getActivity(), "请打开定位权限以获取数据!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }


}
