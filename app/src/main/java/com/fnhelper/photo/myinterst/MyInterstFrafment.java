package com.fnhelper.photo.myinterst;


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
 * 我的关注
 */
public class MyInterstFrafment extends Fragment {


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

    public MyInterstFrafment() {
        // Required empty public constructor
    }


    public static MyInterstFrafment newInstance(String param1, String param2) {
        MyInterstFrafment fragment = new MyInterstFrafment();
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
        View view = inflater.inflate(R.layout.fragment_my_interst, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTklRefreshLayout();
        initRecyclerView();
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

    private void initRecyclerView() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        adapter = new QuickAdapter<String>(getContext(), R.layout.item_my_interst) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item, int position) {


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
