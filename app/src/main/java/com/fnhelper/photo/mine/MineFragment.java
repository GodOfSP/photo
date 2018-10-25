package com.fnhelper.photo.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fnhelper.photo.R;
import com.fnhelper.photo.interfaces.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 我的粉丝
 */
public class MineFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.head_pic)
    SimpleDraweeView headPic;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.vip_type)
    ImageView vipType;
    @BindView(R.id.expiry_date_title)
    TextView expiryDateTitle;
    @BindView(R.id.expiry_date)
    TextView expiryDate;
    @BindView(R.id.more_detail)
    TextView moreDetail;
    @BindView(R.id.presentAndMaid)
    RelativeLayout presentAndMaid;
    @BindView(R.id.bindPhone)
    RelativeLayout bindPhone;
    @BindView(R.id.my2Code)
    RelativeLayout my2Code;
    @BindView(R.id.question)
    RelativeLayout question;
    @BindView(R.id.about_app)
    RelativeLayout aboutApp;
    @BindView(R.id.system_notice)
    RelativeLayout systemNotice;
    @BindView(R.id.system_setting)
    RelativeLayout systemSetting;


    Unbinder unbinder;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.goto_album_info)
    View gotoAlbumInfo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MineFragment() {
        // Required empty public constructor
    }


    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        // initTklRefreshLayout();
        initUi();
        initClick();
        return view;
    }

    private void initUi() {
        headPic.setImageURI(Constants.sHeadImg);
        userName.setText(Constants.sTsNickNameoken);
    }

    private void initClick() {

        presentAndMaid.setOnClickListener(this);
        bindPhone.setOnClickListener(this);
        my2Code.setOnClickListener(this);
        question.setOnClickListener(this);
        aboutApp.setOnClickListener(this);
        systemNotice.setOnClickListener(this);
        systemSetting.setOnClickListener(this);
        gotoAlbumInfo.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.presentAndMaid:
                //提现返佣
                startActivity( new Intent(getContext(), ToPresentPresentMaidAc.class));
                break;
            case R.id.bindPhone:
                //去绑定
                Intent i = new Intent(getContext(), BindInputTelActivity.class);
                i.putExtra("which", 1);
                startActivity(i);
                break;
            case R.id.goto_album_info:
                //相册信息
                startActivity(new Intent(getContext(), AlbumInfoActivity.class));
                break;
        }

    }





   /* private void initTklRefreshLayout() {

        new TwinklingRefreshLayoutUtil().getUpdateAndLoadMoreTwinkling(getActivity(), refresh);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {


        });
    }*/


}
