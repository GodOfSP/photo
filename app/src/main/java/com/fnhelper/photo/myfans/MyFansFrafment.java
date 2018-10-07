package com.fnhelper.photo.myfans;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fnhelper.photo.R;
import com.fnhelper.photo.base.recyclerviewadapter.BaseAdapterHelper;
import com.fnhelper.photo.base.recyclerviewadapter.QuickAdapter;
import com.fnhelper.photo.diyviews.ClearEditText;
import com.fnhelper.photo.utils.TwinklingRefreshLayoutUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.viewGroup)
    ConstraintLayout viewGroup;

    private QuickAdapter<String> adapter;
    private EasyPopup mCirclePop;


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
        initPop();
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

    private void initPop() {
        mCirclePop = EasyPopup.create()
                .setContentView(getContext(), R.layout.auth_pop)
                .setAnimationStyle(R.style.BottomPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(false)
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
    }


    /**
     * 权限设置
     */
    private void showAuthSettingPop() {


        mCirclePop.showAtAnchorView(getActivity().findViewById(android.R.id.content), YGravity.ALIGN_BOTTOM, XGravity.CENTER, 0, 0);

    }

    private void initRecyclerView() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        adapter = new QuickAdapter<String>(getContext(), R.layout.item_my_fans) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item, int position) {


                helper.setOnClickListener(R.id.auth_setting_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAuthSettingPop();
                    }
                });

                helper.setFrescoImageResource(R.id.head_pic,"http://img4.duitang.com/uploads/item/201411/09/20141109142633_ncKBY.thumb.700_0.jpeg");


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
                getList(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                getList(true);
            }
        });
    }

    private void getList(boolean isLoadMore) {

        adapter.add("asdas");
        adapter.add("asdas");
        adapter.add("asdas");
        adapter.add("asdas");

    }


}
