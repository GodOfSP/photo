package com.fnhelper.photo.index;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fnhelper.photo.R;


/**
 * 相册动态
 */
public class HomeFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        return inflater.inflate(R.layout.fragment_home, container, false);
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
            *//**
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
