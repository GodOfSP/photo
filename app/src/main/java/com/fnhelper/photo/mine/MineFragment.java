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
import com.fnhelper.photo.WebViewAc;
import com.fnhelper.photo.beans.MyVipInfoBean;
import com.fnhelper.photo.interfaces.Constants;
import com.fnhelper.photo.interfaces.RetrofitService;
import com.fnhelper.photo.utils.STokenUtil;

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
 * 我的
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
        moreDetail.setOnClickListener(this);
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
        getMyVipInfo();
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
                startActivity(new Intent(getContext(), ToPresentPresentMaidAc.class));
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
            case R.id.more_detail:
                //会员套餐
                startActivity(new Intent(getContext(), VipMealAc.class));
                break;

            case R.id.my2Code:
                //我的相册二维码
                startActivity(new Intent(getContext(), MyCodeAc.class));
                break;

            case R.id.system_notice:
                //系统通知
                startActivity(new Intent(getContext(), NoticeAc.class));
                break;
            case R.id.system_setting:
                //系统设置
                startActivity(new Intent(getContext(), SystemSettingAc.class));
                break;
            case R.id.question:
                //问题与反馈
                Intent intent = new Intent(getContext(), WebViewAc.class);
                intent.putExtra("where", WebViewAc.linkHtml);
                startActivity(intent);
                break;
            case R.id.about_app:
                //关于app
                Intent intent1 = new Intent(getContext(), WebViewAc.class);
                intent1.putExtra("where", WebViewAc.aboutHtml);
                startActivity(intent1);
                break;
        }

    }





   /* private void initTklRefreshLayout() {

        new TwinklingRefreshLayoutUtil().getUpdateAndLoadMoreTwinkling(getActivity(), refresh);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {


        });
    }*/

    /**
     * 获取会员信息
     */
    private void getMyVipInfo() {

        retrofit2.Call<MyVipInfoBean> call = RetrofitService.createMyAPI().GetCommisionRecord();
        call.enqueue(new Callback<MyVipInfoBean>() {
            @Override
            public void onResponse(Call<MyVipInfoBean> call, Response<MyVipInfoBean> response) {
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getCode() == CODE_SUCCESS) {
                            //成功
                            if (response.body()!=null && response.body().getData()!=null){
                                Constants.isVIP = response.body().getData().isBIsVip();
                                if (response.body().getData().isBIsVip()) {
                                    Constants.vip_exi_time = response.body().getData().getDExpireTime();
                                    expiryDate.setText(response.body().getData().getDExpireTime());
                                    expiryDate.setVisibility(View.VISIBLE);
                                    expiryDateTitle.setVisibility(View.VISIBLE);
                                    moreDetail.setVisibility(View.VISIBLE);
                                    vipType.setVisibility(View.VISIBLE);
                                    expiryDateTitle.setText("会员有效期:");
                                    moreDetail.setText("详情");
                                } else {
                                    expiryDateTitle.setText("暂未开通会员");
                                    moreDetail.setText("开通会员");
                                    moreDetail.setVisibility(View.VISIBLE);
                                    expiryDate.setVisibility(View.GONE);
                                    expiryDateTitle.setVisibility(View.VISIBLE);
                                    vipType.setVisibility(View.GONE);
                                }
                            }


                        } else if (response.body().getCode() == CODE_ERROR) {
                            //失败
                        } else if (response.body().getCode() == CODE_SERIVCE_LOSE) {
                            //服务错误
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //登录过期
                            STokenUtil.check(getActivity());
                        } else if (response.body().getCode() == CODE_TOKEN) {
                            //账号冻结
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<MyVipInfoBean> call, Throwable t) {
                showBottom(getContext(), "网络异常！");
            }
        });
    }

}
